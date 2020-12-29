package cane.brothers.security

import cane.brothers.security.UserPrincipal
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

/**
 * @author mniedre
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        if(StringUtils.hasText(username)) {
            // TODO load user by name
            return new UserPrincipal(username, null, null);
        } else {
            new UsernameNotFoundException("User not found by name: " + username)
        }
    }
}
