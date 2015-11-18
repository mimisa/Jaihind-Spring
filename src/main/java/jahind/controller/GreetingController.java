package jahind.controller;

import jahind.entity.Greeting;
import jahind.entity.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Gaurav on 18/11/15.
 */
@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/api/greeting")
    public Greeting greeting(@AuthenticationPrincipal User user) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, user.getName()));
    }
}
