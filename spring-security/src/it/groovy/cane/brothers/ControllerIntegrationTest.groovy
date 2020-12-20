package cane.brothers

import io.restassured.RestAssured
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort

/**
 * @author mniedre
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ["spring.security.user.name=user",
                "spring.security.user.password=pass"])
class ControllerIntegrationTest {

    @Value('${spring.security.user.name}')
    String usr

    @Value('${spring.security.user.password}')
    String pwd

    @LocalServerPort
    private int serverPort

    @BeforeEach
    void initRestAssured() {
        RestAssured.port = serverPort

        RestAssured.filters(new ResponseLoggingFilter())
        RestAssured.filters(new RequestLoggingFilter())
    }

    @Test
    void 'api call without authentication must fail'() {
        RestAssured.when()
                .get("/")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
    }

    @Test
    void 'api call with authentication must succeed'() {
        Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(usr, pwd)
                .when()
                .get("/")
                .then()
                .extract()
                .response()

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
    }

    @Test
    void 'JSESSIONID must be changed after login'() {
        def sessionCookie = RestAssured.when()
                .get("/")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().cookie("JSESSIONID")

        def newCookie = RestAssured.given()
                .sessionId(sessionCookie) // JSESSIONID
                .auth()
                .basic(usr, pwd)
                .when()
                .get("/")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().cookie("JSESSIONID")

        Assertions.assertNotEquals(sessionCookie, newCookie)
    }

    @Test
    void 'POST without session and CSRF token must return 401'() {
        Response response = RestAssured
                .when()
                .post("/test/post")
                .then()
                .extract()
                .response()

        Assertions.assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void 'POST with session but without CSRF token must return 403'() {
        Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(usr, pwd)
                .when()
                .get("/")
                .then()
                .extract()
                .response()

        response = RestAssured.given()
                .sessionId(response.getSessionId()) // JSESSIONID
                .when()
                .post("/test/post")
                .then()
                .extract()
                .response()

        Assertions.assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode());
    }

    @Test
    void 'POST with session and CSRF token must succeed'() {
        Response response = RestAssured.given()
                .auth()
                .basic(usr, pwd)
                .when()
                .get("/")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response()

        RestAssured.given()
                .sessionId(response.getSessionId()) // JSESSIONID
                .cookie("XSRF-TOKEN", response.getCookie("XSRF-TOKEN"))
                .header("X-XSRF-TOKEN", response.getCookie("XSRF-TOKEN"))
                .when()
                .post("/test/post")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
    }
}
