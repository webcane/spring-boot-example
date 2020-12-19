package cane.brothers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author mniedre
 */
@RestController
public class Controller {

    @GetMapping
    public String get() {
        return String.valueOf(System.currentTimeMillis())
    }
}
