# market-place-purchase
This project interfaces all purchase related actions.

### Technologies Used System Wide
- Java 12
- Docker (Containerization)
- Kubernetes (Container Orchestration) 
- RabbitMQ
- MongoDB
- Netflix Ribbon (Client side load balancing - This will be separated in a different branch, since I'm coding the Kubernetes part now)
- Netflix Eureka (Naming server - This will be separated in a different branch, since I'm coding the Kubernetes part now)
- Netflix Feign (Declarative rest calls - This will be separated in a different branch, since I'm coding the Kubernetes part now)
- Swagger (API documentation and code generation)
- Spring Boot
- Cloud Config Server (Centralized configuration files of the microservices)
- Cloud Sleuth (Adds trace ids on the rest calls. Helps a lot in finding bugs in microservices architecture)
- Cloud Zipkin (Uses the Sleuth ids to present us the overall health of our rest calls)
- Actuator (Used to display overall health and monitoring metadata of the system)
- Sonar (Helps on identifying code smells, test coverage and some more...)
- Jacoco (Allows to integrate our tests results in the sonar web app)
- Rest Assured (Api integration tests)
- Json Assert (Used to validate the returned Json with Regex)
- JUnit
- Mockito
- Gson
- Maven

### Deploy
...all the docker images have been created and pushed, I'm now creating the Kubernetes' configurations and as soon as I've finished it, you'll have it :] ...

### Info
The Market Place project is composed by five microservices that are:
market-place-api-gateway, market-place-products, market-place-purchase, market-place-orderprocessor 
and market-place-sellers.

It's also composed by four other containers that are:
market-place-config-server, market-place-docker-compose, market-place-exception-handler and 
market-place-eureka. 
 
In the near future it will have a react frontend spa. Further down are the links to their repositories.

Also, please find bellow the system's diagram. It will help further on how things 
happens "behind the curtains".

### How to
Everything starts on the sellers api, where a seller has to be registered through the 
`/market-place-sellers/marketplace/api/v1/sellers` endpoint. After a seller has been registered then we can 
register any number of products for that seller through the 
`/market-place-products/marketplace/api/v1/products` endpoint. After the products has been registered, then 
we can start selling them right away through the `/market-place-purchase/marketplace/api/v1/purchase/start`.

Again, for more info on what happens on the backend part when we're calling those endpoints, please refer 
to the diagram bellow and also all the documentations provided on the openapi.yaml files referenced bellow. 

<b>For more info</b> on the available endpoints, expected payloads, 
responses and so on, please refer to the swagger "openapi.yaml" file
located in the src/main/resources folder of each project. All you have to do is paste their content into 
`https://editor.swagger.io/`.

https://github.com/VandersonAssis/market-place-kubernetes

https://github.com/VandersonAssis/market-place-docker-compose

https://github.com/VandersonAssis/market-place-api-gateway

https://github.com/VandersonAssis/market-palce-integration-tests

https://github.com/VandersonAssis/market-place-products

https://github.com/VandersonAssis/market-place-sellers

https://github.com/VandersonAssis/market-place-purchase

https://github.com/VandersonAssis/market-place-orderprocessor

https://github.com/VandersonAssis/market-place-configserver

https://github.com/VandersonAssis/market-place-exception-handlers

#### Disclaimer
This and any other piece of code belonging to the Market Place project is 
being created with intention to show interviewers my abilities creating 
a system using microservices architecture.

Also, please have in mind that 
I work on this project only on my free time, therefore, improvements can take a little while to be made.

Last but not least, feel free to do whatever the h311 you want with this code. :0d

### Diagram
![alt text](https://raw.githubusercontent.com/VandersonAssis/market-place-support-files/master/diagrams/system-diagram.png)
