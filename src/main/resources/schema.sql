drop table if exists transactions;
drop table if exists bankaccounts;
drop table if exists credentials;
drop table if exists ibans;
drop table if exists cards;
drop table if exists users;

create table users(
    user_id int auto_increment primary key,
    firstname varchar(20),
    lastname varchar(25),
    email varchar(40),
    telephone varchar(10)
);

create table cards(
    number varchar(16) primary key,
    expire_date varchar(5),
    cvv varchar(3),
    user_id int,
    foreign key (user_id) references users(user_id) on delete cascade
);

create table ibans(
    iban varchar(40) primary key,
    user_id int,
    foreign key (user_id) references users(user_id) on delete cascade
);

create table credentials(
    username varchar(20) primary key,
    password varchar(20),
    user_id int,
    foreign key (user_id) references users(user_id) on delete cascade
);

create table bankaccounts(
    account_Id int auto_increment primary key,
    balance int,
    user_id int,
    foreign key(user_id) references users(user_id) on delete cascade
);

create table transactions(
    trans_id int auto_increment primary key,
    trans_date varchar(10),
    user_iban varchar(40),
    amount int,
    description_message varchar(60),
    foreign key(user_iban) references ibans(iban) on delete cascade
);