package rocks.inspectit.demo.frontend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rocks.inspectit.demo.dto.Coin;
import rocks.inspectit.demo.dto.Quote;
import rocks.inspectit.demo.dto.QuoteStatus;

import java.util.Optional;

@Slf4j
@RestController
public class TradingController {

    @Autowired
    private ExchangeHandler exchangeHandler;

    @Autowired
    private QuoteManager quoteManager;

    @GetMapping("/quote")
    public ResponseEntity<Quote> quote(@RequestParam Coin coin, @RequestParam double amount) {
        log.info("Quote for {} {}", amount, coin);
        Quote quote = quoteManager.quoteTrade(coin, amount);
        log.info("Quote is: {}", quote);

        return ResponseEntity.ok(quote);
    }

    @GetMapping("/status")
    public ResponseEntity<Quote> status(@RequestParam(name = "quote") String quoteId) {
        log.info("Status quote {}", quoteId);

        Optional<Quote> quoteOptional = quoteManager.getQuote(quoteId);

        if (quoteOptional.isEmpty()) {
            throw new IllegalArgumentException("Quote with id '" + quoteId + "' does not exist.");
        }

        return ResponseEntity.ok(quoteOptional.get());
    }

    @GetMapping("/buy")
    public ResponseEntity<Quote> buy(@RequestParam(name = "quote") String quoteId) {
        log.info("Buying quote {}", quoteId);

        Optional<Quote> quoteOptional = quoteManager.getQuote(quoteId);

        if (quoteOptional.isEmpty()) {
            throw new IllegalArgumentException("Quote with id '" + quoteId + "' does not exist.");
        }

        Quote quote = quoteOptional.get();
        if (quote.getStatus() != QuoteStatus.OPEN) {
            throw new IllegalStateException("The quote has already been closed.");
        }

        exchangeHandler.buy(quote);

        Quote close = quoteManager.close(quoteId);

        return ResponseEntity.ok(close);
    }
}
