package cane.brothers.security


import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationDetailsSource
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.AuthenticationConverter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils

import javax.servlet.http.HttpServletRequest
import java.util.stream.Collectors

/**
 * @author mniedre
 */
public class JwtAuthenticationConverter implements AuthenticationConverter {

    private static final Logger log =
            LoggerFactory.getLogger(JwtAuthenticationConverter.class)

    public static final String SECRET = "Secret";

    public static final String AUTHENTICATION_SCHEME = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;

    /**
     * Constructor
     */
    public JwtAuthenticationConverter() {
        this(new WebAuthenticationDetailsSource());
    }

    /**
     * Constructor
     *
     * @param authenticationDetailsSource
     */
    public JwtAuthenticationConverter(
            AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        this.authenticationDetailsSource = authenticationDetailsSource
    }


    @Override
    public UsernamePasswordAuthenticationToken convert(HttpServletRequest request) {
        String header = request.getHeader(HEADER_STRING);
        if (header == null) {
            return null
        }
        if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME)) {
            return null
        }
        if (header.equalsIgnoreCase(AUTHENTICATION_SCHEME)) {
            throw new BadCredentialsException("Empty bearer authentication token")
        }

        String token = header.replace(AUTHENTICATION_SCHEME, "").trim()
        String base64EncodedKeyBytes = Base64.getEncoder().encodeToString(SECRET.getBytes())

        try {
            JwtParser jwts = Jwts.parser()
            if (jwts.isSigned(token)) {
                Jws<Claims> claimsJws = jwts
                        .setSigningKey(base64EncodedKeyBytes)
                        .parseClaimsJws(token);
                //OK, we can trust this JWT

                Claims claims = claimsJws.getBody()

                String user = claims.getSubject()
                log.info(user)

                Object groups = claims.get("groups")
                List<GrantedAuthority> authorities = new ArrayList<>()

                if (groups != null) {
                    List<SimpleGrantedAuthority> asList = cast(groups).stream()
                            .map({ g -> new SimpleGrantedAuthority(g.toString()) })
                            .collect(Collectors.toList())
                    authorities.addAll(asList)
                }

                if (user != null) {
                    UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(user, null, authorities)
                    result.setDetails(this.authenticationDetailsSource.buildDetails(request))
                    return result
                } else {
                    throw new BadCredentialsException("Invalid bearer authentication token")
                }
            }
        } catch (JwtException e) {
            //don't trust the JWT!
            log.error(e.getMessage())
        }

        catch (IllegalArgumentException ex) {
            log.error(ex.getMessage())
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T extends List<?>> T cast(Object obj) {
        return (T) obj
    }
}
