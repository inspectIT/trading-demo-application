package rocks.inspectit.demo.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rocks.inspectit.demo.dto.Coin;

@RestController
public class ExchangeController {

    @GetMapping("/price")
    public double getPrice(@RequestParam Coin coin) throws InterruptedException {
        Thread.sleep((long) (Math.random() * 500 + 500));

        switch (coin) {
            case BTC:
                return 61759.90D;
            case ETH:
                return 4507.50D;
            default:
                throw new IllegalArgumentException("Coin '" + coin + "' is not supported.");
        }
    }

    @GetMapping("/trade")
    public void trade(@RequestParam Coin coin, double price) throws InterruptedException {
        Thread.sleep((long) (Math.random() * 1500 + 2000));
    }
}
