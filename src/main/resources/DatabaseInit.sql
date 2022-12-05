create database flowerManager;
use flowerManager;
create table user (
    username varchar(20) primary key not null unique,
    password varchar(100) not null,
    type tinyint default 0 not null
);
create table flower(
    name varchar(50) primary key not null unique,
    price decimal(20,2) default 0.00,
    storage int default 0 not null
);
create table orders(
    orders_id int primary key not null auto_increment unique,
    orders_date datetime not null default current_timestamp,
    flower_name varchar(20) not null,
    amount int not null,
    discount decimal(20, 2) default 0.00,
    operator varchar(20) not null,
    foreign key fk_operator_username(operator) references user(username),
    foreign key fk_flower_name(flower_name) references flower(name)
);

-- origin admin password is '123456'
insert into user value ("admin", "a23838945b3eb07157333a0451c62a354542e06f71027089", 1);

insert into flower value ("china rose", 19.30, 132);
insert into flower value ("lotus", 2.4, 12);
insert into flower value ("lily", 28.43, 293);
insert into flower value ("rose", 30.20, 100);
insert into flower value ("sunflower", 13.40, 482);