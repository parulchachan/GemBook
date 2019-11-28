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