drop table if exists transactions;
drop table if exists bankaccounts;
drop table if exists credentials;
drop table if exists ibans;
drop table if exists cards;
drop table if exists users;

create table users(
    id int auto_increment primary key,
    firstname varchar(20),
    lastname varchar(25),
    email varchar(40),
    telephone varchar(10)
);

create table cards(
    number varchar(16) primary key,
    expireDate varchar(5),
    cvv varchar(3),
    userid int,
    foreign key (userid) references users(id) on delete cascade
);

create table ibans(
    iban varchar(40) primary key,
    userid int,
    foreign key (userid) references users(id) on delete cascade
);

create table credentials(
    username varchar(20) primary key,
    password varchar(20),
    userid int,
    foreign key (userid) references users(id) on delete cascade
);

create table bankaccounts(
    accountid int auto_increment primary key,
    balance int,
    userid int,
    foreign key(userid) references users(id) on delete cascade
);

create table transactions(
    transid int auto_increment primary key,
    transdate varchar(10),
    useriban varchar(40),
    amount int,
    foreign key(useriban) references ibans(iban) on delete cascade
);