create schema IF NOT EXISTS gembook collate latin1_swedish_ci;


create table IF NOT EXISTS gembook.user
(
	first_name varchar(255) not null,
	last_name varchar(255),
    user_name varchar(255) primary key
);

create table IF NOT EXISTS gembook.post_type
(
	post_type_id int primary key,
	post_type_name varchar(255) not null
);

create table IF NOT EXISTS gembook.post
(
	post_id int auto_increment primary key,
	post_type_id int not null,
	post_content varchar(255),
	user_name varchar(255) not null,
	post_time timestamp,
	constraint post_post_type_id_fk
		foreign key (post_type_id) references gembook.post_type (post_type_id)
			on update cascade on delete cascade,
	constraint post_user_id_user_user_email_fk
		foreign key (user_name) references gembook.user (user_name)
			on update cascade on delete cascade
);

insert into gembook.post_type(post_type_id, post_type_name) values(1001,'Findings');
insert into gembook.post_type(post_type_id, post_type_name) values(1002,'Achievements');
insert into gembook.post_type(post_type_id, post_type_name) values(1003,'Challenges');
insert into gembook.post_type(post_type_id, post_type_name) values(1004,'Projects');

create table IF NOT EXISTS gembook.comment
(
	comment_id  int auto_increment primary key,
	post_id int not null,
	user_name varchar(255) not null,
	comment_content varchar(255),
	comment_time timestamp,
	constraint comment_post_id_post_post_id_fk foreign key (post_id) references gembook.post (post_id)
			on update cascade on delete cascade,
	constraint comment_user_name_user_user_name_fk foreign key (user_name) references gembook.user (user_name)
			on update cascade on delete cascade
);