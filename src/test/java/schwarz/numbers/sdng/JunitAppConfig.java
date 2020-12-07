package schwarz.numbers.sdng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import schwarz.numbers.sdng.models.InMemoryRange;
import schwarz.numbers.sdng.models.NumberCounter;
import schwarz.numbers.sdng.models.NumberType;
import schwarz.numbers.sdng.services.NumberCounterService;


@Configuration
public class JunitAppConfig {
    Logger logger = LoggerFactory.getLogger(JunitAppConfig.class);

    @Autowired
    private NumberCounterService numberCounterService;

    @Bean
    @Scope("singleton")
    public InMemoryRange getInMemoryOrderNumberRange(){
        NumberCounter numberCounter= numberCounterService.initializeNumberSequence(NumberType.JUNIT_ORDER_NUMBER);
        //numberCounterService.resetOrderNumberSequence(NumberType.JUNIT_ORDER_NUMBER);
        InMemoryRange inMemoryRange= new InMemoryRange(numberCounter);
        numberCounterService.incrementOrderNumberSequence(inMemoryRange,1);
        logger.info("JUNIT_MODE InMemoryRange initialized: start:"+inMemoryRange.getStart()+" current:"+inMemoryRange.getCurrent()+" max:"+inMemoryRange.getMax());
        return inMemoryRange;
    }
}
