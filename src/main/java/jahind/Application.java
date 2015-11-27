package jahind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Gaurav on 16/11/15.
 */

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
