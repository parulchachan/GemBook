create schema IF NOT EXISTS gembook collate latin1_swedish_ci;

create table IF NOT EXISTS gembook.user
(
	first_name varchar(255) not null,
	last_name varchar(255),
    user_id varchar(255) primary key
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
	user_id varchar(255) not null,
	post_time bigint,
	constraint post_post_type_id_fk
		foreign key (post_type_id) references gembook.post_type (post_type_id)
			on update cascade on delete cascade,
	constraint post_user_id_user_user_email_fk
		foreign key (user_id) references gembook.user (user_id)
			on update cascade on delete cascade
);

create table IF NOT EXISTS gembook.likes (
	user_id varchar(255) not null,
	post_id int not null,
	like_time bigint,	
	like_flag varchar(1) not null,
	constraint likes_user_id_user_user_id_fk foreign key (user_id) references gembook.user (user_id)
		on update cascade on delete cascade,
        constraint likes_post_id_post_post_id_fk foreign key (post_id) references gembook.post (post_id)
		on update cascade on delete cascade
);

create table IF NOT EXISTS gembook.comments
(
	photo_id  int auto_increment primary key,
	post_id int not null,
	photo_content varchar(4000),
	photo_time bigint,
	constraint photo_post_id_post_post_id_fk foreign key (post_id) references gembook.post (post_id)
			on update cascade on delete cascade
);

create table IF NOT EXISTS gembook.photos
(
	comment_id  int auto_increment primary key,
	post_id int not null,
	user_id varchar(255) not null,
	comment_content varchar(4000),
	comment_time bigint,
	constraint comment_post_id_post_post_id_fk foreign key (post_id) references gembook.post (post_id)
			on update cascade on delete cascade,
	constraint comment_user_name_user_user_name_fk foreign key (user_id) references gembook.user (user_id)
			on update cascade on delete cascade
);

insert into gembook.post_type(post_type_id, post_type_name) values(1001,'Findings');
insert into gembook.post_type(post_type_id, post_type_name) values(1002,'Achievements');
insert into gembook.post_type(post_type_id, post_type_name) values(1003,'Challenges');
insert into gembook.post_type(post_type_id, post_type_name) values(1004,'Projects');
insert into gembook.post_type(post_type_id, post_type_name) values(1005,'Announcements');

insert into user(user_id, first_name,last_name) values('parul.chachan@geminisolutions.in','Parul','Chachan');
insert into user(user_id, first_name,last_name) values('parul.chachan@gmail.com','P','C');
--insert into posts(post_type_id,post_content,user_id,post_time) values(1002,'Hi Everyone! I got employee of the year.','user0001', SYSDATE());
