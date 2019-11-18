drop schema if exists projectservlet;
create schema projectservlet character set utf8 collate utf8_general_ci;

use projectservlet;

create table routes (
id int auto_increment,
number varchar(255) not null,
start_point varchar(255) not null,
end_point varchar(255) not null,
primary key (id));

create table buses(
id int auto_increment,
model varchar(255) not null,
number varchar(255) not null,
route_id int,
driver_id int,
primary key (id),
constraint c_bus
foreign key (route_id) references routes(id)
on delete no action on update no action);

create table drivers (
id int auto_increment,
first_name varchar(255) not null,
last_name varchar(255) not null,
phone_number varchar(255),
email varchar(255),
primary key (id));

alter table buses
add foreign key (driver_id) references drivers(id)
on delete no action on update no action;

create table users(
id int auto_increment,
username varchar(255) not null,
password varchar(255) not null,
authority varchar(255) not null,
enabled boolean not null,
primary key (id)),
constraint c_users
foreign key (username) references drivers(email)
on delete cascade on update cascade);
