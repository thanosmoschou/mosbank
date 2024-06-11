CREATE USER 'spring'@localhost identified by 'spring';
CREATE DATABASE mosbank;
GRANT SELECT, UPDATE, DELETE, REFERENCES, INSERT, DROP, CREATE on mosbank.* to 'spring'@localhost;