package rocks.inspectit.demo.common;

import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rocks.inspectit.demo.TradingApplication;

import java.util.UUID;

@RestController
public class InfoController {

    private Faker faker = new Faker();

    @GetMapping("/info")
    public String info() {
        String fact = faker.chuckNorris().fact();
        return "{\"instance-name\": \"" + TradingApplication.INSTANCE_NAME + "\", \"uuid\": \"" + UUID.randomUUID() + "\", \"fact\": \"" + fact + "\"}";
    }

}
