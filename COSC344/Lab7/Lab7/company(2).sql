set foreign_key_checks = false;

drop table if exists dependent;
drop table if exists works_on;
drop table if exists project;
drop table if exists department_locations;
drop table if exists department;
drop table if exists employee;


create table department (
    Department_Num integer,
    Name varchar(15) not null,
    Manager_Num integer,
    Manager_Start_Date date,
    primary key (Department_Num)
);

insert into department (Department_Num, Name, Manager_Num, Manager_Start_Date)
values
    (5, 'Research', 333445555, '1988-05-22'),
    (4, 'Administration', 987654321, '1995-01-01'),
    (1, 'Headquarters', 888665555, '1981-06-19'),
    (0, 'No Department', 111100000, '2004-12-31');
    -- Fascinating loophole: the last row can be inserted despite there
    -- being no corresponding employee because FKs are disabled, BUT it
    -- doesn't complain when you re-enable FKs!


create table employee (
    Employee_Num integer,
    First_Name varchar(15) not null,
    Middle_Initial char,
    Surname varchar(15) not null,
    Birth_Date date not null,
    Address varchar(30) not null,
    Salary decimal(8,2) not null,
    Gender char not null,
    IRD_Num char(9) unique not null,
    Department_Num integer not null,
    Supervisor_Num integer,
    primary key (Employee_Num),
    constraint FK_Emp_Department foreign key (Department_Num) references department (Department_Num),
    constraint FK_Emp_Supervisor foreign key (Supervisor_Num) references employee (Employee_Num)
);

insert into employee (Employee_Num, First_Name, Middle_Initial, Surname, Birth_Date, Address, Salary, Gender, IRD_Num, Department_Num, Supervisor_Num)
values
    (123456789, 'John', 'B', 'Smith', '1965-01-09', '731 Fondren, Houston, TX', 30000, 'M', 56219310, 5, 333445555),
    (333445555, 'Franklin', 'T', 'Wong', '1955-12-08', '638 Voss, Houston, TX', 40000, 'M', 35938208, 5, 888665555),
    (999887777, 'Alicia', 'J', 'Zelaya', '1968-07-19', '3321 Castle, Spring, TX', 25000, 'F', 48531435, 4, 987654321),
    (987654321, 'Jennifer', 'S', 'Wallace', '1941-06-20', '291 Berry, Bellaire, TX', 43000, 'F', 65669358, 4, 888665555),
    (666884444, 'Ramesh', 'K', 'Narayan', '1962-09-15', '975 Fire Oak, Humble, TX', 38000, 'M', 74513894, 5, 333445555),
    (453453453, 'Joyce', 'A', 'English', '1972-07-31', '5631 Rice, Houston, TX', 25000, 'F', 50441571, 5, 333445555),
    (987987987, 'Ahmad', 'V', 'Jabbar', '1969-03-29', '980 Dallas, Houston, TX', 25000, 'M', 39733641, 4, 987654321),
    (888665555, 'James', 'E', 'Borg', '1937-11-10', '450 Stone, Houston, TX', 55000, 'M', 27449875, 1, null);


create table department_locations (
    Department_Num integer,
    Location varchar(15),
    primary key (Department_Num, Location),
    constraint FK_DepLoc_Department foreign key (Department_Num) references department (Department_Num)
);

insert into department_locations (Department_Num, Location)
values
    (1, 'Houston'),
    (4, 'Stafford'),
    (5, 'Bellaire'),
    (5, 'Sugarland'),
    (5, 'Houston');


create table project (
    Project_Num integer,
    Name varchar(15) not null,
    -- This is a slight variation from the original ERD to allow for
    -- projects that don't have a specific location.
    Location varchar(15),
    Department_Num integer not null,
    primary key (Project_Num),
    constraint FK_Pro_Department foreign key (Department_Num) references department (Department_Num)
);

insert into project (Project_Num, Name, Location, Department_Num)
values
    (1, 'ProductX', 'Bellaire', 5),
    (2, 'ProductY', 'Sugarland', 5),
    (3, 'ProductZ', 'Houston', 5),
    (10, 'Computerisation', 'Stafford', 4),
    (20, 'Reorganisation', 'Houston', 1),
    (30, 'Newbenefits', 'Stafford', 4),
    (40, 'NonProject', null, 4);


create table works_on (
    Employee_Num integer,
    Project_Num integer,
    Hours decimal(4,1) not null,
    primary key (Employee_Num, Project_Num),
    constraint FK_Work_Employee foreign key (Employee_Num) references employee (Employee_Num),
    constraint FK_Work_Project foreign key (Project_Num) references project (Project_Num)
);

insert into works_on (Employee_Num, Project_Num, Hours)
values
    (123456789, 1, 32.5),
    (123456789, 2, 7.5),
    (666884444, 3, 40.0),
    (453453453, 1, 20.0),
    (453453453, 2, 20.0),
    (333445555, 2, 10.0),
    (333445555, 3, 10.0),
    (333445555, 10, 10.0),
    (333445555, 20, 10.0),
    (999887777, 30, 30.0),
    (999887777, 10, 10.0),
    (987987987, 10, 35.0),
    (987987987, 30, 30.0),
    (987654321, 30, 20.0),
    (987654321, 20, 15.0),
    (888665555, 20, 0.0);


create table dependent (
    Employee_Num integer,
    Name varchar(15) ,
    Gender char,
    Birth_Date date,
    Relationship varchar(8),
    primary key (Employee_Num, Name),
    constraint FK_Dep_Employee foreign key (Employee_Num) references employee (Employee_Num)
);

insert into dependent (Employee_Num, Name, Gender, Birth_Date, Relationship)
values
    (333445555,'Alice', 'F', '1986-04-05', 'Daughter'),
    (333445555,'Theodore', 'M', '1983-10-25', 'Son'),
    (333445555,'Joy', 'F', '1958-05-03', 'Spouse'),
    (987654321,'Abner', 'M', '1942-02-28', 'Spouse'),
    (123456789,'Michael', 'M', '1988-01-04', 'Son'),
    (123456789,'Alice', 'F', '1988-12-30', 'Daughter'),
    (123456789,'Elizabeth', 'F', '1967-05-05', 'Spouse');


alter table department add constraint FK_Dep_Manager foreign key (Manager_Num) references employee (Employee_Num);

set foreign_key_checks = true;

commit;

-- The following select statement lists all foreign key constaints defined on tables in the company database.
-- You can check whether all foreign keys have been successfully created. There should be eight.

select constraint_name, table_name, referenced_table_name
from information_schema.referential_constraints
where constraint_schema = 'company';

