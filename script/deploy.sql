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
insert into cities values('omsk');
insert into cities values('vladivostok');
insert into cities values('habarovsk');
insert into cities values('irkutsk');
insert into cities values('barnaul');
insert into cities values('chelybinsk');
insert into cities values('novosibirsk');
insert into cities values('kemerovo');
insert into cities values('krasnoyrsk');

--insert into trips values(,'A-1','msk','omsk','2018-04-01 21:00:00');
insert into trips(number,city_from,city_to,departure_date)
values('A-1','vladivostok','msk','2018-04-01 21:00:00');
insert into trips(number,city_from,city_to,departure_date)
values('A-2','omsk','krasnoyrsk','2018-04-15 19:30:00');


