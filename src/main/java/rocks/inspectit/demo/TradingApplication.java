package rocks.inspectit.demo;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
