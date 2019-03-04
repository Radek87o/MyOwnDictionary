drop schema if exists `my-own-dictionary`;

create schema `my-own-dictionary`;

use `my-own-dictionary`;

drop table if exists `role`;

create table `role`
(
	`id` int(11) not null auto_increment,
    `name` varchar(40) not null,
    primary key(`id`),
    unique key `name_unique` (`name`)
)engine=InnoDB auto_increment=1 charset=utf8;

insert into `role` (name) values ('ROLE_ADMIN'), ('ROLE_USER');

drop table if exists `user`;

create table `user`
(
	`id` int(11) not null auto_increment,
    `username` varchar(40) not null,
    `password` char(80) not null,
    `first_name` nvarchar(40) not null,
    `last_name` nvarchar(40) not null,
    `email` varchar(40) default null,
    `role_id` int(11) not null,
    primary key(`id`),
    unique key `username_unique`(`username`),
    constraint `FK_role_id` foreign key (`role_id`) references `role` (`id`) on delete no action on update no action
) engine=InnoDB auto_increment=1 charset=utf8;


#password for default admin - admin123
insert into `user` (username, password, first_name, last_name, email, role_id) values
('jan.nowak', '$2a$04$8F04By99bjikJXPGmAK3a.1k73eHCQLhdP8lbrYybZCR.XVeeEo7C', 'Jan', 'Nowak', 'jan.nowak@gmail.com',1);

drop table if exists `dictionary`;

create table `dictionary`
(
	`id` int(11) not null auto_increment,
    `english_word` varchar(40) not null,
    `polish_word` nvarchar(40) not null,
    `expression` varchar(255) default null,
    `user_id` int(11) not null,
    primary key(`id`),
    constraint `FK_user_id` foreign key(`user_id`) references `user`(`id`) on delete no action on update no action
)engine=InnoDB auto_increment=1 charset=utf8;

drop table if exists `dictionary_stats`;

create table `dictionary_stats`
(
	`id` int(11) not null auto_increment,
    `result` tinyint(1) not null,
    `dictionary_id` int(11) not null,
    `entry_date` datetime not null,
    primary key(`id`),
    constraint `FK_dictionary_id` foreign key(`dictionary_id`) references `dictionary` (`id`) on delete no action on update no action
)engine=InnoDB auto_increment=1 charset=utf8;


