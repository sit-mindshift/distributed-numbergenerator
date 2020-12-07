package schwarz.numbers.sdng.repositories;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;
import schwarz.numbers.sdng.models.NumberCounter;
import schwarz.numbers.sdng.models.NumberType;

@Repository
public interface NumberCounterRepository extends DatastoreRepository<NumberCounter, Long> {

    //throws an exception if no results
    NumberCounter findOneByNumberType(NumberType numberType);
}
