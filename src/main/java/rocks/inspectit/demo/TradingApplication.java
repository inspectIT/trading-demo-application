package rocks.inspectit.demo;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
public class TradingApplication {

    public static final String INSTANCE_NAME;

    static {
        Faker faker = new Faker();
        INSTANCE_NAME = faker.leagueOfLegends().champion();
    }

    public static void main(String[] args) {
        SpringApplication.run(TradingApplication.class, args);
    }

    @PostConstruct
    public void init() {
        log.info("Instance name is: {}", INSTANCE_NAME);
    }
}
