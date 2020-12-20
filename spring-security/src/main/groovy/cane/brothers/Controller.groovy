package cane.brothers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author mniedre
 */
@RestController
class Controller {

    @GetMapping
    String get() {
        return String.valueOf(System.currentTimeMillis())
    }

    @PostMapping("/test/post")
    String testPost() {
        return "Hello it is post request"
    }
}
