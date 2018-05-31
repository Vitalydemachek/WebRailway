Drop table if exists bookedTicket;
Drop table if exists ticket;
Drop table if exists carriage;
Drop table if exists accordanceTypeCarriageSeats;
Drop table if exists TypeCarriage;
Drop table if exists seats;
Drop table if exists stops;
Drop table if exists trips;
Drop table if exists cities;
Create Table cities (
name varchar Primary key
);

Create Table trips(
ID serial primary key,
number varchar, 
city_from varchar references cities(name) ON DELETE RESTRICT,
city_to varchar references cities(name) ON DELETE RESTRICT,
departure_date timestamp
--foreign key(city_to)references cities(name) ON DELETE RESTRICT
);

create Table stops(
ID serial primary key,
arrive_date timestamp,
tripID integer references trips(ID),
city varchar references cities(name)
); 

create table seats(
ID serial primary key,
typeSeat varchar,
numberSeat integer
); 

create table TypeCarriage(
ID serial not null primary key,
typeDiscription varchar
);

create table AccordanceTypeCarriageSeats(
ID int not null,
typeCrr_ID integer references TypeCarriage(ID) on Delete restrict,
Seat_ID integer references Seats(ID) on Delete restrict,
PRIMARY KEY (ID,TypeCRR_ID)

);


create table carriage(
ID serial primary key,
tripID integer references trips(ID),
typeCrr_ID int references TypeCarriage(ID) ON DELETE RESTRICT,
numberCarriage integer
);

create Table ticket(
ID serial primary key,
tripID integer references trips(ID),
city_from varchar references cities(name) ON DELETE RESTRICT,
city_to varchar references cities(name) ON DELETE RESTRICT,
departure_date timestamp,
seatID integer references seats(ID),
carrID integer references carriage(ID),
customerFirstName varchar
); 

create table bookedTicket(
ID serial primary key,
ticketID integer references ticket(ID),
stopFromID integer references stops(ID) ON DELETE RESTRICT,
stopToID integer references stops(ID) ON DELETE RESTRICT
);



insert into cities values('msk');
insert into cities values('chelybinsk');
insert into cities values('omsk');
insert into cities values('novosibirsk');
insert into cities values('krasnoyrsk');
insert into cities values('irkutsk');
insert into cities values('habarovsk');
insert into cities values('vladivostok');
--insert into cities values('barnaul');
--insert into cities values('kemerovo');

--insert into trips values(,'A-1','msk','omsk','2018-04-01 21:00:00');
insert into trips(id,number,city_from,city_to,departure_date) values
(100,'A-1','omsk','vladivostok','2018-05-10 00:00:00'),
(101,'A-2','msk','vladivostok','2018-05-11 00:00:00'),
(102,'A-3','irkutsk','vladivostok','2018-05-12 00:00:00'),
(103,'A-4','msk','omsk','2018-05-13 00:00:00'),
(104,'A-5','msk','irkutsk','2018-05-14 00:00:00'),
(105,'A-6','omsk','irkutsk','2018-05-15 00:00:00'),
(106,'A-7','novosibirsk','msk','2018-05-16 00:00:00');

insert into stops(id,arrive_date,tripID,city) values
--'omsk'-'vladivostok'
(1,'2018-05-10 00:00:00',100,'omsk'),
(2,'2018-05-10 11:00:00',100,'novosibirsk'),
(3,'2018-05-10 22:00:00',100,'krasnoyrsk'),
(4,'2018-05-11 09:00:00',100,'irkutsk'),
(5,'2018-05-11 20:00:00',100,'habarovsk'),
(6,'2018-05-12 07:00:00',100,'vladivostok'),
--'msk'-'vladivostok'
(7,'2018-05-11 00:00:00',101,'msk'),
(8,'2018-05-11 11:00:00',101,'chelybinsk'),
(9,'2018-05-11 22:00:00',101,'omsk'),
(10,'2018-05-12 09:00:00',101,'novosibirsk'),
(11,'2018-05-12 20:00:00',101,'krasnoyrsk'),
(12,'2018-05-13 07:00:00',101,'irkutsk'),
(13,'2018-05-13 18:00:00',101,'habarovsk'),
(14,'2018-05-14 05:00:00',101,'vladivostok'),
--'irkutsk'-'vladivostok'
(15,'2018-05-12 00:00:00',102,'irkutsk'),
(16,'2018-05-12 11:00:00',102,'habarovsk'),
(17,'2018-05-12 22:00:00',102,'vladivostok'),
--'msk'-'omsk'
(18,'2018-05-13 00:00:00',103,'msk'),
(19,'2018-05-13 11:00:00',103,'chelybinsk'),
(20,'2018-05-13 22:00:00',103,'omsk'),
--'msk'-'irkutsk'
(21,'2018-05-14 00:00:00',104,'msk'),
(22,'2018-05-14 11:00:00',104,'chelybinsk'),
(23,'2018-05-14 22:00:00',104,'omsk'),
(24,'2018-05-15 09:00:00',104,'novosibirsk'),
(25,'2018-05-15 20:00:00',104,'krasnoyrsk'),
(26,'2018-05-16 07:00:00',104,'irkutsk'),
--'omsk'-'irkutsk'
(27,'2018-05-15 00:00:00',105,'omsk'),
(28,'2018-05-15 11:00:00',105,'novosibirsk'),
(29,'2018-05-15 22:00:00',105,'krasnoyrsk'),
(30,'2018-05-16 09:00:00',105,'irkutsk'),
--'novosibirsk','msk'
(31,'2018-05-16 00:00:00',106,'novosibirsk'),
(32,'2018-05-16 11:00:00',106,'omsk'),
(33,'2018-05-16 22:00:00',106,'chelybinsk'),
(34,'2018-05-17 09:00:00',106,'msk');

insert into seats(typeSeat,numberSeat) values
('нижняя полка',1),
('верхняя полка',2),
('нижняя полка',3),
('верхняя полка',4),
('нижняя полка',5),
('верхняя полка',6),
('нижняя полка',7),
('верхняя полка',8);





--insert into accordanceTypeCarriageSeats values
--('купе'),
--('плацкарт');

