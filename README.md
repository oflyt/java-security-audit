# Overview 
A copy of [vulnerability-java-samples](https://github.com/bbossola/vulnerability-java-samples) with some restructuring and added logging. 

## How to run
> `mvn clean install`  
> `java -jar target/jackson-exploit-1.0-SNAPSHOT.jar`  

## Requests
> `cd curls`  
> `./list.sh`  
> `./add.sh`  
> `./exploit.sh`  

## Logging
The logging has been split up into three logs, main log, vulnerability log and stack trace log. That means that they can be distributed differently and permissions can be handled separately. 

### MDC
Session ID is added to the MDC and then also added to all the logging in the app. 

### Main log
The main log will contain basic information about what is happening in the app. 

### Vulnerability audit log
The vulnerability audit log contain information from every request, such as IP of requester, headers, cookies, and body. It also contains information about the response but then only the status code and body. Since this log might contain sensitive information it is good to have it separate from the main log. 

### Stack trace log
The stack trace log contains the stack traces when something has gone wrong in the application, this is mainly to keep the main log clean. 

## credits
https://github.com/bbossola/vulnerability-java-samples
https://bbossola.wordpress.com/2018/04/14/remotely-execute-java-code-using-json/
