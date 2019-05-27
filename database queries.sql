create table book_info (
	ISBN varchar(255), 
	name varchar(255) NOT NULL, 
	author varchar(255), 
	description varchar(255), 
	publishing_date date, 
	primary key(ISBN)

);

insert into book_info values(' ', ' ', ' ', data);

# to run sql queries in mysql

source ~/myWebProject/mycommands.sql

insert into book_info values (1001, 'Java for dummies', 'Tan Ah Teck', NULL, '2008-07-09');
insert into book_info values (1002, 'More Java for dummies', 'Tan Ah Teck', NULL, '2001-08-14');
insert into book_info values (1003, 'More Java for more dummies', 'Mohammad Ali', NULL, '1999-11-07');
insert into book_info values (1004, 'A Cup of Java', 'Kumar', NULL, '2004-03-18');
insert into book_info values (1005, 'A Teaspoon of Java', 'Kevin Jones', NULL, '2019-04-03');

insert into book_stock values (1001, 13.56, 'Denver', 1, 100, , 0);
insert into book_stock values (1002, 10.50, 'Beijing', 1, 432, , 20);
insert into book_stock values (1003, 9.87, 'Paris', 0, 850, , 11);
insert into book_stock values (1004, 14.90, 'Sydney', 1, 206, , 0);
insert into book_stock values (1005, 7.60, 'Shanghai', 0, 1000, , 50);

create table book_stock (
	ISBN varchar(255),
	price float,
	location varchar(255),
	isAval bool,
	qty_in int,
	qty_sold int,
	foreign key (ISBN)
		references book_info(ISBN)
		on update cascade
		on delete set null
);

DROP TRIGGER IF EXISTS SOLD_OUT;
DELIMITER //
create trigger SOLD_OUT before update on book_stock
	for each row begin
		if new.qty_in = 0 then set new.isAval = 1;
		end if;
end //


create trigger AUTO_ADD_TO_STOCK after insert on book_info
	for each row
	begin
		insert into book_stock(ISBN) values (new.ISBN);
	end //