select customer.name, amount from sale inner join customer on (sale.customer_num = customer.customer_num) where sale.amount in (select max(amount) from sale);
select * from sale where amount > (select avg(amount) from sale where sale_date = "1990-10-03") and sale_date = "1990-10-03";
select * from sale where sale.salesperson_num in (select salesperson_num from salesperson where city = "London");
select salesperson_num, name from salesperson where Salesperson_Num in (select Salesperson_Num from sale group by Salesperson_Num having count(distinct customer_num) >1);
select * from sale as sale_outer where amount > (select avg(amount) from sale where sale_outer.Customer_Num = sale.Customer_Num);
select first_name, surname from employee where not exists (select * from dependent where (employee.employee_num = dependent.employee_num));

insert into department (Department_Num, Name, Manager_Num, Manager_Start_Date)
values (6, 'TempDept', 123456789, '2002-07-18');
insert into project (Project_Num, Name, Location, Department_Num)
values (50, 'TempProject', 'Houston', 6);

#7 below
(select project_num from employee inner join works_on on employee.employee_num = works_on.Employee_Num where employee.first_name = "John")
union
(select project_num from employee inner join department on department.Manager_Num = employee.employee_num inner join project on department.department_num = project.Department_Num where First_Name = "John");

select * from employee;
#8
update employee
set salary = salary * 1.1
where employee_num <> 888665555;
select * from employee;

create table houston_employees (
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
    primary key (Employee_Num)
);
#9
insert into houston_employees (select * from employee where address like '%Houston%');
select * from houston_employees;

#10

create table employee_dependents (
    Employee_Num integer,
    Employee_First_Name varchar(15) not null,
    Employee_Surname varchar(15) not null,
    Dependent_Name varchar(15) ,
    Dependent_Gender char,
    Dependent_Birth_Date date,
    primary key (Employee_Num, Dependent_Name),
    constraint FK_Employee_Dependents_Employee foreign key (Employee_Num) references employee (Employee_Num)
);
select employee.employee_num, first_name, surname, dependent.name, dependent.Gender, dependent.Birth_Date from employee inner join dependent on (employee.Employee_Num = dependent.Employee_num);
#10
insert into employee_dependents (select employee.employee_num, first_name, surname, dependent.name, dependent.Gender, dependent.Birth_Date from employee inner join dependent on (employee.Employee_Num = dependent.Employee_num));
select * from employee_dependents;