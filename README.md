## Rate Limiter


### PreRequisite for setup 

1. Java 8
2. Maven
3. Good to Have (PostMan, Docker, Redis)


### How to start

1. Run the below command
```shell
mvn clean install -DskipTests
```

2. Open the project in your ide, and start the RatelimiterApplication.java

3. You can configure the application.properties to use redis/in memory for storage

```shell
minute-rate-limiter-strategy=sliding/fixed
hour-rate-limiter-strategy=sliding/fixed
cache-provider=redis/in_memory
```

### Api Documentation

After starting the service locally visit 

```shell
http://localhost:8080/swagger-ui.html
```

#### Demo Video


### Algorithms

1. Fixed Window

We will count requests in the current window, if that exceeds limit, we will throttle the request or else allow it.

2. Sliding Window with Weightage 

We check whether we can allow the current request or not based on the current window request count and
previous window count and a dynamic weightage based on current timestamp.

    *  Rate = <current_rate> + (prev_window_count * (weightage)
    *  For minute => weightage = Math.max(0, 0.95 - (currentSecond / 60))
    *  For hour => weightage = Math.max(0, 0.95 - (currentMinute / 60))
    
### Things being used

1. We are using H2 in memory db to store data. You can use postgres or mysql as well
2. Swagger UI, Log4J, JUnit

### Things Pending
1. Integration Tests and more fool proof unit tests
2. Handling Race conditions in Redis . Using INCR etc