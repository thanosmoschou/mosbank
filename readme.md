README UNDER CONSTRUCTION!!!

Useful notes:
First set up MySQL Server.
Download the MySQL Server from the original site ```https://dev.mysql.com/downloads/mysql/```. 
(You can find an online guide for that). Then set up the server. Create a user ```spring``` with password ```spring``` (weak I know but it is for demonstrating purposes only). Make him able to connect only from localhost.

After you set up the server, you can add the bin folder to environment variables. Find an online guide about that.

Start the MySQL server on windows
Open task manager -> Services -> Find MYSQL service -> right click -> start

Connect to the MySQL Server
mysql -u username -p -D databasename -e "source filenamePath"
mysql -u username -p -D databasename

Give to the user some privileges
show grants for 'spring'@'localhost';
grant references on mosbank.* to 'spring'@'localhost';

Useful commands:
show tables;
use mosbank;
show databases;

grant drop on 'mosbank' to 'spring'@'localhost';

Keep in mind:
Convert the cancel buttons to <a> tags in all html pages
Application endpoints:

/ : This is the root of the app. It redirects you to /login
/login : Login Page
/signup : Sign Up Page
/atm : mosbank's atm machine
