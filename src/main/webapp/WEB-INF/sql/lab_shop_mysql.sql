create database studydb character set utf8 collate utf8_general_ci;

grant select, insert, update, delete, create, drop on studydb.* to 'study'@'localhost' identified by 'study';
grant select, insert, update, delete, create, drop on studydb .* to 'study'@'%' identified by 'study';

create table shop_no_sequence (
	sequence_name varchar(10) not null,
	next_value int not null,
	primary key (sequence_name)
);

insert into shop_no_sequence values ('board', 0);
insert into shop_no_sequence values ('comment', 0);

create table shop_user (
	no int not null auto_increment,
	first_name varchar(20) not null,
	last_name varchar(20) not null,
	email varchar(50) not null,
	password varchar(50) not null,
	created_date datetime not null,
	last_date datetime not null,
	authority varchar(20) not null default 'ROLE_USER',
	image_path varchar(50) default 'default.jpg',
	primary key (no),
	unique index (email)
);

create table shop_board (
	no int not null auto_increment,
	group_no int not null,
	sequence_no char(12) not null,
	title varchar(100) not null,
	content text not null,
	posting_date datetime not null,
	read_count int not null,
	comment_count int not null,
	file_count int not null,
	goods_count int not null,
	user_no int not null,
	user_email varchar(50) not null,
	separator_name varchar(20) not null,
	primary key (no),
	index (sequence_no),
	foreign key(user_no) references shop_user(no),
	foreign key(user_email) references shop_user(email)
);


create table shop_comment(
	no int not null auto_increment,
	group_no int not null,
	sequence_no char(12) not null,
	content text not null,
	posting_date datetime not null,
	user_no int not null,
	user_email varchar(50) not null,
	board_no int not null,
	separator_name varchar(20) not null,
	primary key (no),
	index (sequence_no),
	foreign key(board_no) references shop_board(no) on delete cascade,
	foreign key(user_no) references shop_user(no),
	foreign key(user_email) references shop_user(email)
);

create table shop_goods (
	no int not null auto_increment,
	name char(100) not null,
	size char(20) not null,
	material char(50) not null,
	component char(20) not null,
	options text not null,
	manufacturer char(50) not null,
	madein char(50) not null,
	description text not null,
	price int not null,
	stock int not null,
	user_no int not null,
	user_email varchar(50) not null,
	created_date datetime not null,
	primary key (no),
	foreign key(user_no) references shop_user(no),
	foreign key(user_email) references shop_user(email)
);

create table shop_board_goods (
	board_no int not null,
	goods_no int not null,
	foreign key(board_no) references shop_board(no),
	foreign key(goods_no) references shop_goods(no)
);