package cane.brothers.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.util.Assert

/**
 * @author mniedre
 */
class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    /**
     * Constructor
     *
     * @param principal
     */
    public JwtAuthenticationToken(Object principal) {
        super((Collection) null);
        this.principal = principal;
        this.setAuthenticated(false);
    }

    /**
     * Constructor
     *
     * @param principal
     * @param authorities
     * @return
     */
    public JwtAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    Object getPrincipal() {
        return this.principal
    }

    @Override
    Object getCredentials() {
        return null
    }

    @Override
    public void eraseCredentials() {
    }
}
