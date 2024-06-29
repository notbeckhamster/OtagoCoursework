drop table if exists sale;
drop table if exists customer;
drop table if exists salesperson;

create table salesperson (
    Salesperson_Num integer,
    Name varchar(10) not null,
    City varchar(15) not null,
    Commission decimal(4,2) not null,
    primary key (Salesperson_Num)
);

create table customer (
    Customer_Num integer,
    Name varchar(12) not null,
    City varchar(15) not null,
    Rating integer not null,
    Salesperson_Num integer not null,
    primary key (Customer_Num),
    constraint FK_Cust_Salesperson foreign key (Salesperson_Num) references salesperson (Salesperson_Num)
);

create table sale (
    Sale_Num integer,
    Amount decimal(8,2) not null,
    Sale_Date date not null,
    Customer_Num integer not null,
    Salesperson_Num integer not null,
    primary key (Sale_Num),
    constraint FK_Sale_Customer foreign key (Customer_Num) references customer (Customer_Num),
    constraint FK_Sale_Salesperson foreign key (Salesperson_Num) references customer (Salesperson_Num)
);

insert into salesperson
values
    (1001, 'Peel', 'London', 0.12),
    (1002, 'Serres', 'San Jose', 0.13),
    (1004, 'Motika', 'London', 0.11),
    (1007, 'Rifkin', 'Barcelona', 0.15),
    (1003, 'Axelrod', 'New York', 0.10);

insert into customer
values
    (2001, 'Hoffman', 'London', 100, 1001),
    (2002, 'Giovanni', 'Rome', 200, 1003),
    (2003, 'Liu', 'San Jose', 200, 1002),
    (2004, 'Grass', 'Berlin', 300, 1002),
    (2006, 'Clemens', 'London', 100, 1001),
    (2008, 'Cisneros', 'San Jose', 300, 1007),
    (2007, 'Pereira', 'Rome', 100, 1004);

insert into sale
values
    (3001, 18.69, '1990-10-03', 2008, 1007),
    (3003, 767.19, '1990-10-03', 2001, 1001),
    (3002, 1900.10, '1990-10-03', 2007, 1004),
    (3005, 5160.45, '1990-10-03', 2003, 1002),
    (3006, 1098.16, '1990-10-03', 2008, 1007),
    (3009, 1713.23, '1990-10-04', 2002, 1003),
    (3007, 75.75, '1990-10-04', 2004, 1002),
    (3008, 4723.00, '1990-10-05', 2006, 1001),
    (3010, 1309.95, '1990-10-06', 2004, 1002),
    (3011, 9891.88, '1990-10-06', 2006, 1001);

commit;
