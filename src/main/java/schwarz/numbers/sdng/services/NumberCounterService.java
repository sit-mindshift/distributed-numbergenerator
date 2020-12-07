package schwarz.numbers.sdng.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.data.datastore.core.DatastoreOperations;
import org.springframework.stereotype.Service;
import schwarz.numbers.sdng.models.InMemoryRange;
import schwarz.numbers.sdng.models.NumberCounter;
import schwarz.numbers.sdng.models.NumberType;
import schwarz.numbers.sdng.repositories.NumberCounterRepository;

import java.time.LocalDateTime;

@Service
public class NumberCounterService {

    private final static int COUNTER_RESERVATION_STEPS=800;

    Logger logger = LoggerFactory.getLogger(NumberCounterService.class);

    @Autowired
    private NumberCounterRepository numberCounterRepository;


    @Autowired
    DatastoreOperations datastoreOperations;

    @Autowired
    InMemoryRange inMemoryOrderNumberRange;


    public NumberCounter initializeNumberSequence(NumberType type) {
        NumberCounter numberCounter = numberCounterRepository.findOneByNumberType(type);
        if (numberCounter == null) {
            numberCounter = new NumberCounter();
            numberCounter.setCurrentNumber(0l);
            numberCounter.setLastModification(LocalDateTime.now());
            numberCounter.setNumberType(type);
            numberCounterRepository.save(numberCounter);
        }
        return numberCounter;
    }

    public void resetOrderNumberSequence(NumberType type) {

        NumberCounter numberCounter = numberCounterRepository.findOneByNumberType(type);
        if (numberCounter == null) {
            numberCounter = new NumberCounter();
        }
        numberCounter.setCurrentNumber(0l);
        numberCounter.setLastModification(LocalDateTime.now());
        numberCounter.setNumberType(type);
        numberCounterRepository.save(numberCounter);
    }


    public void incrementOrderNumberSequence(final InMemoryRange inMemoryRange, int count) {
        datastoreOperations.performTransaction(datastoreOperations -> {
            NumberCounter nb = datastoreOperations.findById(inMemoryRange.getNumberCounter().getId(), NumberCounter.class);
            long start = nb.getCurrentNumber();
            long end = start + count;
            inMemoryRange.setStart(start);
            inMemoryRange.setMax(end);
            inMemoryRange.setCurrent(start);
            nb.setCurrentNumber(end);
            nb.setLastModification(LocalDateTime.now());
            datastoreOperations.save(nb);
            logger.info("Incremented InMemoryRange  start:" + inMemoryRange.getStart() + " current:" + inMemoryRange.getCurrent() + " max:" + inMemoryRange.getMax());
            return inMemoryRange;
        });
    }

    public synchronized long getCurrentOrderLong() {
        return inMemoryOrderNumberRange.getCurrent();
    }

    public synchronized long getNextOrderLong() {
        if (inMemoryOrderNumberRange.getCurrent() == inMemoryOrderNumberRange.getMax()) {
            this.incrementOrderNumberSequence(inMemoryOrderNumberRange, COUNTER_RESERVATION_STEPS);
        }
        inMemoryOrderNumberRange.setCurrent(inMemoryOrderNumberRange.getCurrent() + 1);
        return inMemoryOrderNumberRange.getCurrent();
    }


}
