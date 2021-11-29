package rocks.inspectit.demo.frontend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rocks.inspectit.demo.dto.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, String> {

}
