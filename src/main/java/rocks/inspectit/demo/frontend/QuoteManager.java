package rocks.inspectit.demo.frontend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocks.inspectit.demo.dto.Coin;
import rocks.inspectit.demo.dto.Quote;
import rocks.inspectit.demo.dto.QuoteStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Slf4j
@Component
public class QuoteManager {

    @Autowired
    private ExchangeHandler exchangeHandler;

    @Autowired
    private QuoteRepository quoteRepository;

    private double profit = 0.01;

    public Quote quoteTrade(Coin coin, double amount) {
        Double price = exchangeHandler.getPrice(coin);
        log.info("Current price: {}", price);

        double total = round(amount * price * (1 + profit), 2);
        Quote quote = Quote.builder().coin(coin).amount(amount).price(price).total(total).build();

        quote = quoteRepository.save(quote);

        return quote;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public Optional<Quote> getQuote(String id) {
        return quoteRepository.findById(id);
    }

    public Quote close(String quoteId) {
        Optional<Quote> quoteOptional = quoteRepository.findById(quoteId);
        if (quoteOptional.isPresent()) {
            Quote quote = quoteOptional.get();
            quote.setStatus(QuoteStatus.CLOSED);
            return quoteRepository.save(quote);
        }
        throw new IllegalStateException("Quote does not exist");
    }
}
