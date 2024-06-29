drop table if exists emp1;
drop table if exists dept1;


create table dept1 (
    Department_Num integer,
    Name varchar(15) not null unique,
    Total_Salary decimal(10, 2) default 0,
    primary key (Department_Num)
);

-- Note use of default on Total_Salary.
insert into dept1 (Department_Num, Name)
    values
        (5, 'Research'),
        (4, 'Administration'),
        (1, 'Headquarters');


create table emp1 (
    Employee_Num integer,
    First_Name varchar(15) not null,
    Salary decimal(8,2) not null,
    Department_Num integer not null,
    primary key (Employee_Num),
    foreign key (Department_Num) references dept1 (Department_Num)
);


insert into emp1 (Employee_Num, First_Name, Salary, Department_Num)
values
    (123456789, 'John', 30000, 5),
    (333445555, 'Franklin', 40000, 5),
    (999887777, 'Alicia', 25000, 4),
    (987654321, 'Jennifer', 43000, 4),
    (666884444, 'Ramesh', 38000, 5),
    (453453453, 'Joyce', 25000, 5),
    (987987987, 'Ahmad', 25000, 4),
    (888665555, 'James', 55000, 1);
drop table if exists emp1;
drop table if exists dept1;


create table dept1 (
    Department_Num integer,
    Name varchar(15) not null unique,
    Total_Salary decimal(10, 2) default 0,
    primary key (Department_Num)
);

-- Note use of default on Total_Salary.
insert into dept1 (Department_Num, Name)
    values
        (5, 'Research'),
        (4, 'Administration'),
        (1, 'Headquarters');


create table emp1 (
    Employee_Num integer,
    First_Name varchar(15) not null,
    Salary decimal(8,2) not null,
    Department_Num integer not null,
    primary key (Employee_Num),
    foreign key (Department_Num) references dept1 (Department_Num)
);

drop trigger if exists check_update_total_salary;
drop trigger if exists check_insert_total_salary;
drop trigger if exists check_delete_total_salary;
delimiter //
create trigger check_update_total_salary
    after update on emp1
    for each row
begin
    update dept1 set Total_Salary = Total_Salary - old.Salary
    where Department_Num = old.Department_Num;
    update dept1 set Total_Salary = Total_Salary + new.Salary
    where Department_Num = new.Department_Num;
end //

create trigger check_insert_total_salary
    after insert on emp1
    for each row
begin
    update dept1 set Total_Salary = Total_Salary + new.Salary
    where Department_Num = new.Department_Num;
end //

create trigger check_delete_total_salary
    after delete on emp1
    for each row
begin
    update dept1 set Total_Salary = Total_Salary - old.Salary
    where Department_Num = old.Department_Num;
end //
delimiter ;

insert into emp1 (Employee_Num, First_Name, Salary, Department_Num)
values
    (123456789, 'John', 30000, 5),
    (333445555, 'Franklin', 40000, 5),
    (999887777, 'Alicia', 25000, 4),
    (987654321, 'Jennifer', 43000, 4),
    (666884444, 'Ramesh', 38000, 5),
    (453453453, 'Joyce', 25000, 5),
    (987987987, 'Ahmad', 25000, 4),
    (888665555, 'James', 55000, 1);

commit;
