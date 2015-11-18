package jahind.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Gaurav on 18/11/15.
 */
@RestController
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "home";
    }
}
