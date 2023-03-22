 CREATE DATABASE `test_list`;

 CREATE TABLE `guest_table` (
   `table_number` INT NOT NULL,
   `space` int,
   PRIMARY KEY (`table_number`)
 );

 CREATE TABLE `guest_info` (
   `id` int auto_increment,
   `table_number` int,
   `name` varchar(64),
   `accompanying_guests` int,
   `time_arrived` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`)
 );

  CREATE TABLE `user_info` (
    `id` int auto_increment,
    `name` varchar(64),
    `password` varchar(64),
    PRIMARY KEY (`id`)
  );

 insert into guest_table(table_number, space) values(1, 10);
 insert into user_info(name,password) values('test','test');