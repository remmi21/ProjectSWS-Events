#Read me for the restful API kongaroo Event
This project where implemented in the proseminar of Semantic Webservices on the University
of Innsbruck. The outcome is a restful API named kongaroo Event which can be used with the 
localhost and the commandline. The mongoDB is located at mLab and deals with events in the Australian city Perth. 

The API was designed by Ramona Huber and Julia Wanker and can be found on github via: https://github.com/remmi21/ProjectSWS-Events
##Project structure
The complete implementation can be found in the directory /src/main/java.
There the two mains - Client and Server - can be found. The resources of the 
restful API are located in the directory /src/main/java/event.

##Dependencies and Requirements
All the maven dependencies can be found in pom.xml and should be automatically
installed by the used IDE. Moreover, a MongoDB is used in this project which runs 
on an online server. However, MongoDB has to be installed locally. 

##Using the API kangaroo Event
First starting the class Server. After that you should start the Client. 
The Client should then automatically display all the actions which can be performed
on the API. Via numbers the actions can be choosen and the API requests all the 
necessary input parameter in ordert o make the request. 
The Client can be stopped by typing the number -1. 