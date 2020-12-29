package cane.brothers.security

import org.springframework.security.core.AuthenticatedPrincipal
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * @author mniedre
 */
class UserPrincipal implements AuthenticatedPrincipal, UserDetails {

    private String name;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * UserPrincipal
     *
     * @param name
     * @param password
     * @param authorities
     */
    public UserPrincipal(String name, String password, Collection<? extends GrantedAuthority> authorities) {
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    String getName() {
        return this.name
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities
    }

    @Override
    String getPassword() {
        return this.password
    }

    @Override
    String getUsername() {
        return this.name
    }

    @Override
    boolean isAccountNonExpired() {
        return true
    }

    @Override
    boolean isAccountNonLocked() {
        return true
    }

    @Override
    boolean isCredentialsNonExpired() {
        return true
    }

    @Override
    boolean isEnabled() {
        return true
    }
}
