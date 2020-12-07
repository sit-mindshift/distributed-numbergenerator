package schwarz.numbers.sdng.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import schwarz.numbers.sdng.models.InMemoryRange;
import schwarz.numbers.sdng.models.NumberCounter;
import schwarz.numbers.sdng.models.NumberType;
import schwarz.numbers.sdng.services.NumberCounterService;


@Configuration
@Profile("prod")
public class AppConfig {
    Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Autowired
    private NumberCounterService numberCounterService;

    @Bean
    @Scope("singleton")
    public InMemoryRange getInMemoryOrderNumberRange() {
        NumberCounter numberCounter = numberCounterService.initializeNumberSequence(NumberType.ORDER_NUMBER);
        InMemoryRange inMemoryRange = new InMemoryRange(numberCounter);
        numberCounterService.incrementOrderNumberSequence(inMemoryRange, 1);
        logger.info("PRODUCTION_MODE InMemoryRange initialized: start:" + inMemoryRange.getStart() + " current:" + inMemoryRange.getCurrent() + " max:" + inMemoryRange.getMax());
        return inMemoryRange;
    }
}
