create table `jpa_example`.`address` (
id int(11) not null auto_increment primary key,
street varchar(50) not null,
city varchar(50) not null,
country varchar(50) not null,
postcode varchar(50) not null
);