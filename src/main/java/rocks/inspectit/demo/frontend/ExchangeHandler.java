package rocks.inspectit.demo.frontend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import rocks.inspectit.demo.dto.Coin;
import rocks.inspectit.demo.dto.Quote;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class ExchangeHandler {

    private static final String ENV_BACKEND_URL_KEY = "BACKEND_URL";

    private static final String DEFAULT_BACKEND_URL = "http://localhost:8080";

    @Value("${" + ENV_BACKEND_URL_KEY + ":" + DEFAULT_BACKEND_URL + "}")
    private String backendUrl;

    @PostConstruct
    public void printBackendUrl() {
        log.info("Using backend URL: {}", backendUrl);
        if (DEFAULT_BACKEND_URL.equals(backendUrl)) {
            log.info("# # # # # # #");
            log.info("You are using the default backend URL. You can specify a custom URL using the environment variable '{}' or using system properties.", ENV_BACKEND_URL_KEY);
            log.info("\tExample: java -D{}=http://target:8080 -jar ...", ENV_BACKEND_URL_KEY);
            log.info("# # # # # # #");
        }
    }

    public Double getPrice(Coin coin) {
        log.info("Get price for {}", coin);

        String target = UriComponentsBuilder.fromHttpUrl(backendUrl + "/price")
                .queryParam("coin", coin)
                .encode()
                .toUriString();

        ResponseEntity<Double> responseEntity = new RestTemplate().getForEntity(target, Double.class);
        return responseEntity.getBody();
    }

    public void buy(Quote quote) {
        log.info("Buy {}", quote);

        String target = UriComponentsBuilder.fromHttpUrl(backendUrl + "/trade")
                .queryParam("coin", quote.getCoin())
                .queryParam("price", quote.getPrice())
                .encode()
                .toUriString();

        ResponseEntity<Void> responseEntity = new RestTemplate().getForEntity(target, Void.class);
        // do nothing - just calling the backend
    }
}
