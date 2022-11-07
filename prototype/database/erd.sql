drop database if exists book_store;

create database if not exists book_store;

create table province_city (
id bigint primary key auto_increment,
name varchar(50)
);

create table district (
id bigint primary key auto_increment,
name varchar(50),
province_city_id bigint,
foreign key (province_city_id) references province_city (id)
);

create table address (
id bigint primary key auto_increment,
street varchar(255),
district_id bigint,
foreign key (district_id) references district (id)
);

create table user (
id bigint primary key auto_increment,
name varchar(50) not null,
`email` varchar(50) not null,
phone_number varchar(20),
gender bit,
avatar varchar(255),
address_id bigint,
foreign key (address_id) references address (id),
unique index `email_UNIQUE` (`email` asc) visible
);

create table role (
id bigint primary key auto_increment,
role varchar(20)
);

create table account (
id bigint primary key auto_increment,
user_email varchar(20),
password varchar(255),
user_id bigint,
foreign key (user_id) references user (id)
);

create table account_role (
account_id bigint,
role_id bigint,
primary key (account_id, role_id),
foreign key (account_id) references account (id),
foreign key (role_id) references role (id)
);

create table category (
id bigint primary key auto_increment,
name varchar(255) not null
);

create table book (
id bigint primary key auto_increment,
title varchar(255) not null,
author varchar(50) not null,
translate varchar(50),
publishing_company varchar(255),
summary text,
release_date date,
price double not null,
quantity int 
);

create table book_detail (
book_id bigint,
category_id bigint,
primary key(book_id, category_id),
foreign key (book_id) references book (id),
foreign key (category_id) references category (id)
);

create table cart (
id bigint primary key auto_increment,
account_id bigint,
foreign key (account_id) references account (id)
);

create table cart_detail (
cart_id bigint,
book_id bigint,
primary key (cart_id, book_id),
foreign key (cart_id) references cart (id),
foreign key (book_id) references book (id)
);

create table invoice (
id bigint primary key auto_increment,
code varchar(20),
time date,
account_id bigint,
foreign key (account_id) references account (id)
);

create table invoice_detail (
id bigint primary key auto_increment,
quantity int,
book_id bigint,
invoice_id bigint,
foreign key (book_id) references book (id),
foreign key (invoice_id) references invoice (id)
);
