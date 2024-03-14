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
    pin varchar(4),
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

use mosbank;

-- Fake data for my db
insert into users values (1, "thanos", "moschou", "than@example.com", "6944444444"),
                         (2, "John", "Doe", "john@john.com", "6977777777"),
                         (3, "Makis", "Kotsampasis", "makis@makis.com", "2310123654");

insert into cards values ("5011054488597827", "4523", "08/24", "503", 1),
                         ("3058860736529886", "4789", "11/30", "621", 2),
                         ("7958110142072234", "6985", "12/29", "777", 3);

insert into credentials values ("thanos", "changeme", 1),
                               ("johnjohn", "aaSS11@@", 2),
                               ("makis", "zzXX11@@", 3);

insert into ibans values ("GR815495047604432456", 1),
                         ("GR815495047604432968", 2),
                         ("GR916728324431088128", 3);

insert into bankaccounts values (10, 1000, 1),
                                (20, 3000, 2),
                                (30, 30000, 3);

insert into transactions values (1, "30/01/2024", "GR916728324431088128", 1000, "Receive Payroll");