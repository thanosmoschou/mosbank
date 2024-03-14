# mosbank
## This is an online banking system built with Java and Spring Boot.
## Screenshots
### Login
![UserLogin](/screenshots/userLogin.png) <br>
### Sign Up
![UserSignUp](/screenshots/userSignUp.png) <br>
### Main Page
![Mainpage](/screenshots/mainPage.png) <br>
### MyAccount Page
![MyAccountPage](/screenshots/myAccountPage.png) <br>
### ATM Login
![AtmLogin](/screenshots/atmLogin.png) <br>
### ATM Use
![AtmUse](/screenshots/atmUse.png) <br>

## Available Endpoints

```/```: This is the root of the app. It redirects you to ```/login``` <br>
```/login``` : Login Page <br>
```/signup``` : Sign Up Page <br>
```/atm``` : mosbank's atm machine <br>

### How to set the environment (Before you start the app for the first time):

First set up MySQL Server. <br>
Download the MySQL Server from the original site ```https://dev.mysql.com/downloads/mysql/``` and configure the server.  <br>

### Start the MySQL server on windows

Open task manager -> Services -> Find MYSQL service -> right click -> start <br>
Now connect to the server via terminal with the following command: <br>
```mysql -u root -p``` <br>
Enter your root password and you are connected to the server. <br>
Create a user: <br>
```CREATE USER 'yourName'@localhost identified by 'yourPassword';``` <br>
Remember the ```yourName``` and ```yourPassword``` because these are the credentials that app will use to connect to the database while it is running. <br>
Then create a database called ```mosbank```: <br>
```CREATE DATABASE mosbank;``` <br>
Give to the user some privileges: <br>
```GRANT SELECT, UPDATE, DELETE, REFERENCES, INSERT, DROP, CREATE on mosbank.* to 'yourName'@localhost;``` <br>

After you set up the server, you can add its bin folder to environment variables. You can find an online guide about that. <br>

### How to run the app

If you have set up the environment for the app, you need to make sure the MySQL Server is running every time you try to run the app. <br>
Open the project's folder in a terminal. Go to ```target``` folder:
Run the app with the following command (You need to specify some arguments with -D option): <br>
```java -jar mosbank-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:mysql://localhost:3306/mosbank --spring.datasource.username=yourName --spring.datasource.password=yourPassword``` <br>
Or you can import the project's folder to your prefered IDE (I use Eclipse) and specify the arguments to the ```application.properties``` file, or you can
set the following VM Arguments to the IDE: <br>
```--spring.datasource.url=jdbc:mysql://localhost:3306/mosbank --spring.datasource.username=yourName --spring.datasource.password=yourPassword``` <br>
Last but not least, you can set some environment variables with those values, in case you do not want to add them to a file or do not want to set VM Arguments. <br>

You can access the app via browser: <br>
```http://localhost:8080``` <br>

### Docker 
If you do not want to do all the previous steps to create the running environment, you can create some docker containers. <br>
Make sure you have Docker installed (on windows you need Docker Desktop). <br>
Open the project's folder to a terminal (make sure Docker Engine is running). <br>
Run the following commands: <br>
```cd filesForDocker``` <br>
```docker build -t mosbankdbimage -f DockerfileMySQL .``` <br>
```docker run -d --name mosbankdb mosbankdbimage``` <br>
Check if the container is running: <br>
```docker ps``` <br>
```docker build -t mosbankimage -f DockerfileJava ..``` <br>
```docker run -d --name mosbank mosbankimage``` <br>

#### Keep in mind:
The data inside the database will be deleted when you restart the app. <br>

