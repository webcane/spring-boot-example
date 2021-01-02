package cane.brothers.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mniedre
 */
class JwtUserDetailsServiceTest {

    private final String userName = "test";
    private JwtUserDetailsService userDetailsService = new JwtUserDetailsService();

    @Test
    void test_loadUserByUsername() {
        User user = userDetailsService.loadUserByUsername(userName);
        assertNotNull(user);
    }
}
