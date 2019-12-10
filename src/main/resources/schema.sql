create schema IF NOT EXISTS gembook collate latin1_swedish_ci;


create table IF NOT EXISTS user
(
	first_name varchar(255) not null,
	last_name varchar(255),
    user_name varchar(255) primary key,
    password varchar(255) not null
);

create table IF NOT EXISTS post_type
(
	post_type_id int primary key,
	post_type_name varchar(255) not null
);

create table IF NOT EXISTS post
(
	post_id int primary key,
	post_type_id int not null,
	post_content varchar(255),
	user_name varchar(255) not null,
	post_time date,
	constraint post_post_type_post_type_post_type_id_fk
		foreign key (post_type_id) references post_type (post_type_id)
			on update cascade on delete cascade,
	constraint post_user_id_user_user_name_fk
		foreign key (user_name) references user (user_name)
			on update cascade on delete no action
);

insert into post_type(post_type_id, post_type_name) values(1001,'Findings');
insert into post_type(post_type_id, post_type_name) values(1002,'Achievements');
insert into post_type(post_type_id, post_type_name) values(1003,'Challenges');
insert into post_type(post_type_id, post_type_name) values(1004,'Projects');

-------------------------------------------------------------------------------------------------------
create database gembook;
 use gembook;
 
create table IF NOT EXISTS users
(
	user_id varchar(255) primary key,
    user_name varchar(255) not null,
    email_id  varchar(255)  
);
   

create table IF NOT EXISTS post_type
(
	post_type_id int primary key,
	post_type_name varchar(255) not null
);

create table IF NOT EXISTS posts
(
	post_id int auto_increment primary key,
	post_type_id int not null,
	post_content varchar(4000),
	user_id varchar(255) not null,
	post_time datetime
);

create table IF NOT EXISTS likes(
	user_id varchar(255) not null,
	post_id int not null,
	like_time datetime,
	constraint user_id_fk foreign key (user_id) references users (user_id)
		on update cascade on delete cascade,
        constraint post_id_fk foreign key (post_id) references posts (post_id)
		on update cascade on delete cascade
);

create table IF NOT EXISTS comments(
	comment_id int auto_increment primary key,
	post_id int not null,
	user_id varchar(255) not null,
	comment_content varchar(4000),
	comment_time datetime,
	constraint cuser_id_fk foreign key (user_id) references users (user_id)
		on update cascade on delete cascade,
		constraint cpost_id_fk foreign key (post_id) references posts (post_id)
		on update cascade on delete cascade
);


insert into post_type(post_type_id, post_type_name) values(1001,'Findings');
insert into post_type(post_type_id, post_type_name) values(1002,'Achievements');
insert into post_type(post_type_id, post_type_name) values(1003,'Challenges');
insert into post_type(post_type_id, post_type_name) values(1004,'Projects');
insert into post_type(post_type_id, post_type_name) values(1005,'Announcements');

insert into users(user_id, user_name,email_id) values('user0001','Parul Chachan','parul.chachan@geminisolutions.in');
insert into users(user_id, user_name,email_id) values('user0002','Parul','parul.chachan@gmail.com');
insert into posts(post_type_id,post_content,user_id,post_time) values(1002,'Hi Everyone! I got employee of the year.','user0001', SYSDATE());
