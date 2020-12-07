package schwarz.numbers.sdng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;
import schwarz.numbers.sdng.services.NumberCounterService;


@SpringBootApplication
public class SdngApplication {

    Logger logger = LoggerFactory.getLogger(SdngApplication.class);


    @Autowired
    private NumberCounterService numberCounterService;

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "prod");
        SpringApplication.run(SdngApplication.class, args);

    }

    /*
    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        logger.info("nextOrderNumber:"+ numberCounterService.getNextOrderLong());
        logger.info("nextOrderNumber:"+ numberCounterService.getNextOrderLong());
        logger.info("nextOrderNumber:"+ numberCounterService.getNextOrderLong());
        logger.info("nextOrderNumber:"+ numberCounterService.getNextOrderLong());
        logger.info("nextOrderNumber:"+ numberCounterService.getNextOrderLong());
    }
     */

}
