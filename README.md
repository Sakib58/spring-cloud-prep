## Spring Cloud
Let me start the documentation by asking a simple question, 
**How do microservices communicate with each other?**

Assume I've two microservices named A and B. Service A needs to communicate
with Service B. 

Well, Service A can easily request Service B using `RestTemplate`.

For example,
```java
restTemplate.getForEntity(
        "http://localhost:8085/{api}", 
        ResponseClass.class, 
        api
        ).getBody();
```
In this example, I've hard-coded the URL and port number, and that will work fine
as expected. But when our project becomes popular and more people are using
our software, then to scale our software we may have to create a few
instances of our software and run it on different ports. In that case, how
can we configure restTemplate to send requests to any of the instances? **We
can't hardcode all the port numbers**, right? Even if we can, how to balance
the load among them? To solve this issue, spring introduces **Service Discovery**
concepts. Where we don't need to hardcode URLs and port numbers, instead we'll
use the service name and everything will be handled by Spring Cloud. For this,
we need a server which will discover all services or instances. Spring Cloud
provides an artifact named `spring-cloud-starter-netflix-eureka-server`,
by which I can create a **Eureka Server**. Spring Cloud also provides
a `spring-cloud-starter-netflix-eureka-client` artifact to register clients to
that server. Simply, we will create a Eureka Server, and register all our clients
to that server. In our example, we will register Service A and Service B to the Eureka
Server. By doing this, the Eureka server will know how many instances are
present and on which port they are running. RestTemplate of any client
application will use the only service name to send requests, the Eureka server will
handle everything for it. The new URL in the rest template will look like this,
```java
restTemplate.getForEntity(
        "http://SERVICE-NAME/{api}", 
        ResponseClass.class, 
        api).getBody()
```
Please note that it will not work until we specify RestTemplate to
be **LoadBalanced**.

```java
@Bean
@LoadBalanced
public RestTemplate restTemplate() {
    return new RestTemplate();
}
```

Well, now we can easily communicate with any microservices using
**RestTemplate**, no matter how many instances are running on different
ports. All we need is the name of that service.

There is another way, actually the most used way, to communicate with other
microservices, which is **OpenFeign** provided by Spring Cloud. For this to
work, we need to create a FeignClient interface,
```java
@FeignClient(name = "CURRENCY-CONVERSION", path = "/api/currency-conversion")
public interface CurrencyConversionClient {
    @GetMapping("/convert/{fromCurrency}/{toCurrency}/{amount}")
    CurrencyConvertDto convertCurrency(
            @PathVariable CurrencyName fromCurrency,
            @PathVariable CurrencyName toCurrency,
            @PathVariable double amount
    );
}
```

Here, I specified the service name and controller path within the feign client,
then wrote the abstract method. Implementation of this method will be
found on the `CURRENCY-CONVERSION` service on that specified path. Spring cloud
server will find that method and request to that API and send back the response
to the calling microservice.

we can do the exact same things with RestTemplate, then why should we
use **OpenFeign over RestTemplate**?

Well, there are lots of reasons for choosing OpenFeign over RestTemplate like,
OpenFeign allows us to define methods and parameters that are **similar to
Spring MVC controller**. OpenFeign provides **build-in support for client-side
load balancing**. OpenFeign can **integrate with circuit breakers like Hystrix**,
which avoids cascading failure by providing fallback mechanisms. OpenFeign
provides **build-in support for request and response logging**, etc.

Now, **what this project is all about?**

Our project is a currency exchange and payment system designed to 
facilitate seamless transactions between individuals or businesses in 
different countries. It simplifies the process of making international 
purchases or payments by handling currency conversion transparently. 
Users can easily pay in their local currency, and the system takes care 
of converting it into the desired foreign currency. Additionally, users can
deposit any remaining funds or receive refunds back into their local 
currency. Overall, our project aims to make cross-border transactions 
more accessible and convenient for everyone involved.

Here I've used two microservices so far, one is **Currency Conversion**,
and another one is **Payment Processing**. The Payment Processing 
microservice needs to call the Currency Conversion microservice for
calculating the payable amount in purchaser's own currency. There are a
few common classes and DTOs need to share among few microservices, those
classes or DTOs are kept in SharedPackage.


Well, Payment processing microservice is calling Currency Conversion microservice
to convert payable currency to user's own currency. If this api call will
fail for some unexpected reason, then **how can we trace the issue?**

Here **Logging** comes to rescue us, for logging and monitoring I've used **Micrometer,
Zipkin**. 

To get those, we need to add the following dependencies,
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>
<dependency>
    <groupId>io.zipkin.reporter2</groupId>
    <artifactId>zipkin-reporter-brave</artifactId>
    <version>2.16.3</version>
</dependency>
<dependency>
    <groupId>net.ttddyy.observation</groupId>
    <artifactId>datasource-micrometer-spring-boot</artifactId>
    <version>1.0.2</version>
</dependency>
<dependency>
    <groupId>io.github.openfeign</groupId>
    <artifactId>feign-micrometer</artifactId>
</dependency>
```
And add the following lines in application properties file,
```properties
management.endpoints.web.exposure.include=prometheus
management.tracing.sampling.probability=1.0
logging.pattern.level=%5p [${spring.application.name:},%X{traceId:-},%X{spanId:-}]
```

We need Zipkin to monitor traces, I've used docker image of it. Here is the
docker commands need to run Zipkin,

Pull the latest Zipkin,
```shell
docker run openzipkin/zipkin
```
Run Zipkin using the following command
```shell
docker run --rm -it --name zipkin -p 9411:9411 openzipkin/zipkin
```

Zipkin will run on [http://localhost:9411/zipkin/](http://localhost:9411/zipkin/)
and where we'll observe our APIs and trace each task independently.
I'll explore more with observing and monitoring in distributed system later.

Consider one more case, If my application has lots of microservices running
on different ports, then how do we manage authentication for each request and 
response on different services? Or how do we manage that which request
will be served by which services? So we need a centralized service that
will enforce security by managing the traffic of API request and responses.
And that service is called **API gateway**.

To implement spring cloud api gateway, we need the following dependencies,
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

Here, API gateway need to register itself to Eureka Server because it needs
to find all the instances of any services and need load balancer to distribute
load among them.

In this example, I've used the following configurations for routings,
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: currency-service
          uri: lb://CURRENCY-CONVERSION
          predicates:
            - Path=/api/currency-conversion/**
          filters:
            - StripPrefix=0
        - id: payment-service
          uri: lb://PAYMENT-PROCESSING
          predicates:
            - Path=/api/payment-process/**
          filters:
            - StripPrefix=0
```
If any request comes to this service with prefix `/api/currency-conversion`,
this will redirect to the `CURRENCY-CONVERSION` service. I've used
`StripPrefix=0` so that request to that service keeps same as predicates, if I use
`StripPrefix=1`, then new request will be stripped by 1 word and that'll
look like `/cuurency-conversion/**`.

In addition to the payment-processing microservice, I want another microservice
which involves sending notifications to users after each transaction is 
completed. Sounds straightforward, right? Well, not quite.


The challenge arises when we consider that sending notifications often 
involves interacting with third-party services, which can be slow and 
unpredictable. For example, let's say our notification service takes a 
minimum of 10 seconds to send each notification. If our payment-processing 
microservice were to wait for each notification to be sent before 
continuing its work, it would effectively be idle for those 10 seconds, 
leading to inefficient resource utilization and potentially slower 
transaction processing times.

Enter **RabbitMQ, a message-brokering system that allows for asynchronous 
communication between different parts of our system**. Instead of directly 
sending notifications from the payment-processing microservice to the 
notification service and waiting for a response, we can leverage RabbitMQ 
to decouple these processes.

**Here's how it works:** when a transaction is completed, the 
payment-processing microservice simply publishes a notification message 
to a designated queue in RabbitMQ and moves on with its work without 
waiting for a response. Meanwhile, the notification service continuously 
listens to this queue, picking up incoming notification messages as they 
arrive.

By using RabbitMQ, we've effectively offloaded the responsibility of 
sending notifications from the payment-processing microservice to the 
notification service. This asynchronous communication model allows both 
services to operate independently and efficiently, without one service 
being held up by the other's potentially time-consuming tasks.

In essence, RabbitMQ acts as a reliable intermediary, enabling seamless 
communication between different parts of our system while maximizing 
performance and scalability. With RabbitMQ handling the heavy lifting of 
message queuing and delivery, our payment-processing microservice can 
focus on what it does best: processing transactions quickly and efficiently.

I've recently completed an implementation of RabbitMQ in a new project, 
and I've thoroughly documented the process for anyone interested. You 
can find all the details and code samples on my [Github Repository](https://github.com/Sakib58/rabbitmq-spring-boot).