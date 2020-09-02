Simple REST API implemetation using the below technology stacks.
1. Java 8
2. Spring Boot
3. H2 In-Memory database
4. Maven
5. JUnit
6. Docker to build and deploy the application

In this project, it is clearly described how to design the industry standard REST APIs using Spring and Spring Boot framework.

In this project, the service layers are tested by writing JUnit test cases using JUnit 5.

Please follow the below steps on how to test the APIs

1. Eclipse IDE: Import the project into eclipse and run the project as maven build with the command "package".
                After the successful build, navigate to target directory and run the jar using "java -jar egym-exercise.jar"
				
2. Start_Simple.sh: Run this script which will do maven package and start the application.				

3. Start.sh: To run this script, Docker toolbox is required and please follow the below steps
   1. Install the docker toolbox and open the quick start terminal.
   2. After successful build, open "Oracle VM Virtualbox" and navigate to Settings --> Network --> Adapter 1 --> Advanced --> Port Forwarding
   3. Create a new rule and enter the port "8080" in guest port and host port. This to run the application in localhost:8080
   4. Now double click on start.sh and this would create a docker image (build) out of Dockerfile. This uses the open JDK 8.
   5. After successful build, the docker would start/run the application at port 8080.

