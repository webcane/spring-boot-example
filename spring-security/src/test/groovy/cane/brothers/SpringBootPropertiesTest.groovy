package cane.brothers


import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import static org.assertj.core.api.Assertions.assertThat


/**
 * Created by cane
 */
@SpringBootTest(properties = "security.user.password=pass")
class SpringBootPropertiesTest {

    @Value('${security.user.password}')
    String pwd

    @Test
    void test(){
        assertThat(pwd).isEqualTo("pass")
    }
}
