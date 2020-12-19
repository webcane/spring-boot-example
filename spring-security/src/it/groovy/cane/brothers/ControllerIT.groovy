package cane.brothers

import io.restassured.RestAssured
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import org.apache.http.HttpStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.TestPropertySource

/**
 * @author mniedre
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = [
        "security.user.password=pass",
        "security.enable-csrf=true",
        "security.sessions=if_required"])
class ControllerIT {

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void initRestAssured() {
        RestAssured.port = serverPort;
        RestAssured.filters(new ResponseLoggingFilter())
        RestAssured.filters(new RequestLoggingFilter())
    }

    @Test
    void 'api call without authentication must fail'() {
        RestAssured.when()
                .get("/")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    void 'api call with authentication must succeed'() {
        RestAssured.given()
                .auth().preemptive().basic("user", "pass")
                .when()
                .get("/")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
