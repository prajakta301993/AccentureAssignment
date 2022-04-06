# Corelation Coefficient Accenture Assessment

##Description
Write an application that calculates the correlation coefficient between the percentage of people that died and got vaccinated of COVID-19 given a continent or all available countries using the API as described on: https://github.com/M-Media-Group/Covid-19-API.

You will be asked to provide a demonstration of the functionality of the application including a code walkthrough via a screen sharing session during the interview.

##Requirements
- Write in Java or any other language targeting the JVM
- Write production-ready code
- Free to use any libraries and/or frameworks
- Document how to run the application
- Publish the source code into GitHub (using your own personal account) and share it with us

##Pre-requisite
- Java 1.8
- Maven 3
- Eclipse or any IDE

##How to run the application
- Clone this repository

Then there are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.accenture.AccentureAssignment` class from your IDE.

Alternatively you can use the `Spring Boot Maven plugin` like so:
`mvn spring-boot:run`

You can also generate war file using command `mvn install` and depoy it on available servers like `tomcat, jboss , wildfly ` etc.

##Using the API

Correlation Coefficient
Base API `http://localhost:8080/`

- For Getting correlation coefficient for all available countries

		Request:
			GET http://localhost:8080/getCorelationCoefficen

- For Getting correlation coefficient for all available countries

	- Any Continent (case sensitive) e.g Europe
	
		Request:
			GET http://localhost:8080/getCorelationCoefficen?continent=Europe



