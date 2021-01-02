package cane.brothers

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort

/**
 * @author mniedre
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerIntegrationTest {

    @Autowired
    private SecurityProperties securityProperties

    @Value('${test.jwt.token}')
    private String jwtToken;

    @LocalServerPort
    private int serverPort

    @BeforeEach
    void initRestAssured() {
        RestAssured.port = serverPort

        RestAssured.filters(new ResponseLoggingFilter())
        RestAssured.filters(new RequestLoggingFilter())
    }

    @Test
    void 'api call without jwt authentication must fail'() {
        Response response = RestAssured.given()
                .auth()
                .oauth2('')
                .when()
                .get("/time")
                .then()
                .extract()
                .response()

        Assertions.assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void 'api call with jwt authentication must succeed'() {
        Response response = RestAssured.given()
                .auth()
        .oauth2(jwtToken)
                .when()
                .get("/time")
                .then()
                .extract()
                .response()

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
    }

    @Test
    void 'api call with invalid jwt authentication must fail'() {
        String invalidToken = jwtToken.substring(0, jwtToken.length() - 1);

        Response response = RestAssured.given()
                .auth()
                .oauth2(invalidToken)
                .when()
                .get("/time")
                .then()
                .extract()
                .response()

        Assertions.assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void 'api call with jwt authentication but without required authority PRE must fail'() {
        Response response = RestAssured.given()
                .auth()
                .oauth2(jwtToken)
                .when()
                .get("/pretime")
                .then()
                .extract()
                .response()

        Assertions.assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode());
    }

    @Test
    void 'api call without basic authentication must fail'() {
        RestAssured.when()
                .get("/time")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
    }

    @Test
    void 'api call with basic authentication must succeed'() {
        Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(securityProperties.getUser().getName(),
                        extractEncodedPassword(securityProperties.getUser().getPassword()))
                .when()
                .get("/time")
                .then()
                .extract()
                .response()

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
    }

    private static String extractEncodedPassword(String prefixEncodedPassword) {
        int start = prefixEncodedPassword.indexOf("}");
        return prefixEncodedPassword.substring(start + 1);
    }

}
