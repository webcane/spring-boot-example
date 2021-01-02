package cane.brothers.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

/**
 * @author mniedre
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    /**
     * Constructor
     *
     * @param principal
     * @param authorities
     */
    public JwtAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(principal, null, authorities);
    }
}
