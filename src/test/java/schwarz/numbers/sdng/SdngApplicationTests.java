package schwarz.numbers.sdng;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import schwarz.numbers.sdng.models.InMemoryRange;
import schwarz.numbers.sdng.models.NumberCounter;
import schwarz.numbers.sdng.models.NumberType;
import schwarz.numbers.sdng.services.NumberCounterService;


import java.util.*;


@SpringBootTest
class SdngApplicationTests {
    Logger logger = LoggerFactory.getLogger(SdngApplicationTests.class);

    private final static int NUMBERS_OF_THREADS = 20;
    private final static int NUMBERS_PER_THREAD = 2000;

    @Autowired
    private NumberCounterService numberCounterService;

    private List<Long> generatedLongs = Collections.synchronizedList(new ArrayList<Long>());

    @Test
    void testInMemoryRange() throws InterruptedException {
        generatedLongs = Collections.synchronizedList(new ArrayList<Long>());
        long initialMaxNumber=numberCounterService.getCurrentOrderLong();
        logger.info("initialMaxNumber is "+initialMaxNumber);
        long firstNewNumber=initialMaxNumber+1;

        ThreadGroup group = new ThreadGroup( "OrderNumberGenerators" );

        long startTime=System.currentTimeMillis();
        for (int i = 0; i < NUMBERS_OF_THREADS; i++) {
            Thread t = new Thread(group,runnable);
            t.start();
        }
        logger.info(NUMBERS_OF_THREADS + " total threads started");


        synchronized( group ) {
            while (group.activeCount() > 0) {
                group.wait(10);
            }
        }
        double totalTimeInMs=(double)(System.currentTimeMillis()-startTime);
        logger.info(NUMBERS_OF_THREADS + " threads done");

        int expectedTotalCount=NUMBERS_OF_THREADS*NUMBERS_PER_THREAD;
        double numbersPerSecond=(double)expectedTotalCount/totalTimeInMs*1000d;

        Set<Long> generatedLongsOrdered = new TreeSet<Long>(generatedLongs);
        Object[] orderedArray= generatedLongsOrdered.stream().toArray();
        long firstElementInList=(long)orderedArray[0];
        long lastElementInList=(long)orderedArray[orderedArray.length-1];

        logger.info("Generated Numbers Unordered Size:"+generatedLongs.size());
        //logger.info("Generated Numbers Unordered:"+generatedLongs);
        logger.info("Generated Numbers Ordered Size:"+orderedArray.length);
        //logger.info("Generated Numbers Ordered:"+generatedLongsOrdered);
        logger.info("firstElement in Ordered Size:"+firstElementInList);
        logger.info("lastElement in Ordered Size:"+lastElementInList);

        logger.info("Generated "+expectedTotalCount+" numbers in "+totalTimeInMs+" ms" + " = "+ numbersPerSecond+" numbers per second");

        Assert.assertEquals(generatedLongs.size(),expectedTotalCount);
        Assert.assertEquals(orderedArray.length,expectedTotalCount);

        //WORKS ONLY OF NO OTHER TEST IS RUNNING IN PARALLEL
        //Assert.assertEquals(firstElementInList,firstNewNumber);
        //Assert.assertEquals(lastElementInList,initialMaxNumber+expectedTotalCount);


    }


    Runnable runnable = new Runnable() {


        @Override
        public void run() {
            long threadId = Thread.currentThread().getId();
            for (int i = 0; i < NUMBERS_PER_THREAD; i++) {
                Long nextLong = numberCounterService.getNextOrderLong();
                generatedLongs.add(nextLong);
            }
        }
    };
}
