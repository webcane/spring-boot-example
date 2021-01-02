package cane.brothers.security

import org.springframework.security.core.userdetails.User
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
    User loadUserByUsername(String username) throws UsernameNotFoundException {
        if(StringUtils.hasText(username)) {

            // TODO load user by name
            return User
                    .withUsername(username)
                    .password("")
                    .authorities()
                    .build();
        } else {
            new UsernameNotFoundException("User not found by name: " + username)
        }
    }
}
