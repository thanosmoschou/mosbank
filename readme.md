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
Open the project's folder to a terminal. Now type the following command to import the ```initialDBScript.sql```:
```mysql -u root -p < initialDBScript.sql``` <br>
Enter your root password and the file will be imported. <br>

After you set up the server, you can add its bin folder to environment variables. You can find an online guide about that. <br>

### How to run the app

If you have set up the environment for the app, you need to make sure the MySQL Server is running every time you try to run the app. <br>
Open the project's folder in a terminal. Go to ```target``` folder:
Run the app with the following command: <br>
```java -jar mosbank-0.0.1-SNAPSHOT.jar``` <br>
You can access the app via browser: <br>
```http://localhost:8080``` <br>

<!-- ### Docker (Under Construction)
If you do not want to do all the previous steps to create the running environment, you can create some docker containers. <br>
Make sure you have Docker installed (on windows you need Docker Desktop). <br>
Open the project's folder to a terminal (make sure Docker Engine is running). <br>
Run the following commands: <br>
```cd filesForDocker``` <br>
Create MySQL container: <br>
```docker build -t mosbankdbimage -f DockerfileMySQL .``` <br>
```docker run -d --name mosbankdb mosbankdbimage``` <br>
Check if the container is running: <br>
```docker ps``` <br>
Create the Java Application Container: <br>
```docker build -t mosbankimage -f DockerfileJava ..``` <br>
```docker run -d --name mosbank mosbankimage``` <br>

Check container's ip: <br>
```docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mosbankdb``` <br> -->


