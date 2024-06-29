drop table if exists emp9;
drop table if exists dept9;


create table dept9 (
    Department_Num integer,
    Name varchar(15) not null unique,
    primary key (Department_Num)
);

insert into dept9
values
    (5, 'Research'),
    (4, 'Administration'),
    (1, 'Headquarters'),
    (0, 'No Department');


create table emp9 (
    Employee_Num integer,
    First_Name varchar(15) not null,
    Surname varchar(15) not null,
    Salary decimal(8,2) not null,
    Department_Num integer not null,
    primary key (Employee_Num),
    constraint FK_Emp9_Department foreign key (Department_Num) references dept9 (Department_Num)
);

insert into emp9
values
    (123456789, 'John', 'Smith', 30000, 5),
    (333445555, 'Franklin', 'Wong', 60000, 5),
    (999887777, 'Alicia', 'Zelaya', 25000, 4),
    (987654321, 'Jennifer', 'Wallace', 73000, 4),
    (666884444, 'Ramesh', 'Narayan', 38000, 5),
    (453453453, 'Joyce', 'English', 25000, 5),
    (987987987, 'Ahmad', 'Jabbar', 25000, 4),
    (888665555, 'James', 'Borg', 55000, 1);


commit;
