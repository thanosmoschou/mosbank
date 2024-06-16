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
```java -jar mosbank_app.jar``` <br>
You can access the app via browser: <br>
```http://localhost:8080``` <br>

### Docker
You can create some docker containers if you want. <br>
Make sure you have Docker installed (on windows you need Docker Desktop). <br>
Open the project's folder to a terminal (make sure Docker Engine is running). <br>
Run the following commands: <br>

Create a docker network: <br>
```docker network create mosbank_network```

Create the db image: <br>
```
docker build -t mosbank_db_image -f Dockerfile_for_MySQL .
```

Create container that hosts the db: <br>
```
docker run -d -p 3306:3306 --network=mosbank_network --name mosbank_db mosbank_db_image
```

Check if the container is running: <br>
```docker ps``` <br>

Check if database is created inside the container: <br>
```
docker exec -it mosbank_db bash
mysql -u root -p
```

Enter the root password that is described in the Dockerfile and then type: <br>
```SHOW DATABASES;``` <br>

You should see the ```mosbank``` database created successfully. <br>

<!--
You can connect to the container's mysql server from your local machine without executing ```docker exec``` command. <br>

Type the following commands: <br>
```docker inspect -f "{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}" mosbank_db```

Take the output ip and then type: <br>
```mysql -h YOUR_IP -P 3306 -u root -p```
and simply enter the root password. <br>
Make sure you replace the ```YOUR_IP``` with the output of the previous command. <br>
-->

Create the image of the main app: <br>
```
docker build -t mosbank_app_image -f Dockerfile_for_Java .
```

Create the container that hosts the main app: <br>
```
docker run -d -p 8080:8080 --network=mosbank_network --name mosbank_app -e DB_HOST=mosbank_db mosbank_app_image
```

The app is still accessible via ```http://localhost:8080```.

<!--
Check container's ip: <br>
```docker inspect -f "{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}" mosbank``` <br>
-->

