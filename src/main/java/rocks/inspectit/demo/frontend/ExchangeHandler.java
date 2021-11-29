package rocks.inspectit.demo.frontend;

import lombok.extern.slf4j.Slf4j;
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

    private static final String DEFAULT_BACKEND_URL = "http://localhost:8080";

    private static final String BACKEND_PROPERTY_KEY = "backend_url";

    @PostConstruct
    public void printBackendUrl() {
        String backendUrl = getBackendUrl();
        log.info("Using backend URL: {}", backendUrl);
        if (DEFAULT_BACKEND_URL.equals(backendUrl)) {
            log.info("# # # # # # #");
            log.info("You are using the default backend URL. You can specify a custom URL using the system property '{}'.", backendUrl);
            log.info("\tExample: java -D{}=http://target:8080 -jar ...", BACKEND_PROPERTY_KEY);
            log.info("# # # # # # #");
        }
    }

    private String getBackendUrl() {
        String backendUrl = System.getProperty(BACKEND_PROPERTY_KEY);
        return backendUrl == null ? DEFAULT_BACKEND_URL : backendUrl;
    }

    public Double getPrice(Coin coin) {
        log.info("Get price for {}", coin);

        String target = UriComponentsBuilder.fromHttpUrl(getBackendUrl() + "/price")
                .queryParam("coin", coin)
                .encode()
                .toUriString();

        ResponseEntity<Double> responseEntity = new RestTemplate().getForEntity(target, Double.class);
        return responseEntity.getBody();
    }

    public void buy(Quote quote) {
        log.info("Buy {}", quote);

        String target = UriComponentsBuilder.fromHttpUrl(getBackendUrl() + "/trade")
                .queryParam("coin", quote.getCoin())
                .queryParam("price", quote.getPrice())
                .encode()
                .toUriString();

        ResponseEntity<Void> responseEntity = new RestTemplate().getForEntity(target, Void.class);
        // do nothing - just calling the backend
    }
}
