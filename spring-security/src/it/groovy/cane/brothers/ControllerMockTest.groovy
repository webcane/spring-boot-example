package cane.brothers


import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author mniedre
 */
@WebMvcTest(Controller.class)
class ControllerMockTest {

    @MockBean
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username = 'pretest', password = 'prepass', authorities = 'PRE')
    void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/pretime").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
