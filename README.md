# market-place-purchase
This project interfaces all purchase related actions.

### Technologies Used System Wide
- Java 12
- MongoDB
- RabbitMQ
- Docker (Containerization)
- Netflix Feign (Declarative rest calls)
- Netflix Ribbon (Client side load balancing)
- Netflix Eureka
- Swagger (API documentation and code generation)
- Lombok
- Spring Boot
- Spring Cloud Config Server (Centralized configuration files of the microservices)
- Sonar (Helps on identifying code smells, test coverage and some more...)
- Jacoco (Allows to integrate our tests results in the sonar web app)
- JUnit
- Mockito
- Gson
- Maven

### Deploy
To deploy this project please follow bellow steps.
- 1 Have Docker configured in your computer.
- 2 Clone this repository https://github.com/VandersonAssis/market-place-docker-compose
- 3 Navigate into this project using your preferred command line tool (which can run Docker commands)
- 4 Run the `docker-compose up` command

### Info
The Market Place project is composed by three microservices, a 
 docker-compose project and in the 
future a react frontend. The microservices are market-place-products, 
market-place-purchase and market-place-orderprocessor. Bellow are the 
links to their repositories.

### How to
Everything starts in the product microservice, where in order to be 
sold, they have to be registered in our mongodb database. After 
registered, the purchase microservices comes in. 

When a call is made to marketplace/api/v1/purchase/start endpoint with 
a correct payload, the order process will start, which will make use 
of rest microservices integration and multiple rabbitmq queues. 
In there's a flux diagram illustrating this in the  
market-place-docker-compose project.

<b>For more info</b> on the available endpoints, expected payloads, 
responses and so on, please refer to the swagger "openapi.yaml" file 
located in the src/main/resources folder of each project.

https://github.com/VandersonAssis/market-place-docker-compose

https://github.com/VandersonAssis/market-place-products

https://github.com/VandersonAssis/market-place-purchase

https://github.com/VandersonAssis/market-place-orderprocessor

#### Disclaimer
This and any other piece of code belonging to the Market Place project is 
being created with intention to show interviewers my abilities creating 
a system using microservices architecture. 

This system still under development, instructions on how to deploy it will be available as soon as I have made the steps. 

Also, please have in mind that 
I work in this project only on my free time, therefore improvements can take a little while to be made.