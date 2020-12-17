# Schwarz Distributed Number Generator
This is a distributed number generator based on GCP Firestore in Datastore mode
<br> It generates a increasing number sequence using a in memory counter with reserved number segments. 

## Run it locally
Check the build.gradle file and ensure that the `GOOGLE_APPLICATION_CREDENTIALS` variable points to a service account which has read/write permissions to a GCP Firestore in Datastore mode 
<br> example:
```
environment "GOOGLE_APPLICATION_CREDENTIALS", "D:\\programming\\sdng\\playground-9d6aac7e886e.json" 
```

### Execute the benchmark:
```
$ ./gradlew test -i
```


### Benchmark Parameters:
Variable | Short |Meaning
---------------- | ------------- | -------------
`SdngApplicationTests.NUMBERS_OF_THREADS`  | NOT | is the number of threads running in parallel trying to generate new numbers
`SdngApplicationTests.NUMBERS_PER_THREAD` | NPT | is the number numbers each thread wants to generate
`NumberCounterService.COUNTER_RESERVATION_STEPS`  | CRS | is the amount of numbers which gets reserved for each memory segment 
 
### Benchmark Results:
 NOT | NPT | CRS | Numbers per second
 ---------------- | ------------- | -------------| -------------
 10 | 2000 | 100 | **578**
 10 | 2000 | 200 | **1168**
 10 | 2000 | 400 | **2311**
 10 | 2000 | 800 | **4279**
 |  | | 
 20 | 2000 | 100 | **700**
 20 | 2000 | 200 | **1359**
 20 | 2000 | 400 | **2732**
 20 | 2000 | 800 | **5789**
 
 <br> Author: Sebastian Kroll
