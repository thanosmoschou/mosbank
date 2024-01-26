First set up MySQL Server.
Download the MySQL Server from the original site ```https://dev.mysql.com/downloads/mysql/```. 
(You can find an online guide for that). Then set up the server. Create a user ```spring``` with password ```spring``` (weak I know but it is for demonstrating purposes only). Make him able to connect only from localhost.

Then you can download mysql workbench to interact with the database or you can use a command line. I use command line.
After you set up the server, you can add the bin folder to environment variables. Find an online guide about that.

Open task manager -> Services -> Find MYSQL service -> right click -> start

mysqld start
mysqld stop

mysql -u username -p -D databasename -e "source filenamePath"
mysql -u username -p -D databasename

show grants for 'spring'@'localhost';
grant references on mosbank.* to 'spring'@'localhost';

show tables;
use mosbank;
show databases;

grant drop on 'mosbank' to 'spring'@'localhost';