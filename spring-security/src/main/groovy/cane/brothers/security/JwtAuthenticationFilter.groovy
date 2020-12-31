package cane.brothers.security

import groovy.util.logging.Slf4j
import org.springframework.core.log.LogMessage
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author mniedre
 */
@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {


    private JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();

    /**
     * Constructor
     *
     * @param authenticationManager
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            JwtAuthenticationToken authRequest = this.authenticationConverter.convert(request);
            if (authRequest == null) {
                this.logger.trace("Did not process authentication request since failed to find "
                        + "username in Bearer Authorization header");
                chain.doFilter(request, response);
                return;
            }

            String username = authRequest.getName();
            this.logger.trace(LogMessage.format("Found username '%s' in Bearer Authorization header", username));
            if (authenticationIsRequired(username)) {

                Authentication authResult = getAuthenticationManager().authenticate(authRequest);
                SecurityContextHolder.getContext().setAuthentication(authResult);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
                }

                onSuccessfulAuthentication(request, response, authResult);
            }
        }
        catch (AuthenticationException ex) {
            SecurityContextHolder.clearContext();
            this.logger.debug("Failed to process authentication request", ex);

            onUnsuccessfulAuthentication(request, response, ex);
            if (isIgnoreFailure()) {
                chain.doFilter(request, response);
            }
            else {
                getAuthenticationEntryPoint().commence(request, response, ex);
            }
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean authenticationIsRequired(String username) {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth == null || !existingAuth.isAuthenticated()) {
            return true;
        }

        if (existingAuth instanceof UsernamePasswordAuthenticationToken && !existingAuth.getName().equals(username)) {
            return true;
        }

        return (existingAuth instanceof AnonymousAuthenticationToken);
    }
}
