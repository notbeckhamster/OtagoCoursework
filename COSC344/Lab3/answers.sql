select name from dependent 
where Relationship in ('Daughter', 'Spouse');

select name from project
where Location is null;

select name, first_name, surname from department inner join employee on department.Manager_Num = employee.Employee_Num;

select distinct first_name, surname from employee inner join works_on on (employee.Employee_Num = works_on.Employee_Num) where Hours < 18;

select first_name, surname from employee where surname like 'W%';

select * from employee;

select name from salesperson where city in ('San Jose', 'Barcelona');

select name,amount from sale inner join customer on (sale.Customer_Num = customer.Customer_Num) where amount between 1500 and 5000;

select count(*) from sale;

select avg(amount) from sale;

select sale.Salesperson_Num, max(amount) from salesperson inner join sale on (salesperson.Salesperson_Num = sale.Salesperson_Num) group by sale.salesperson_num;

select sale.Salesperson_Num, max(amount) as maxA from salesperson inner join sale on (salesperson.Salesperson_Num = sale.Salesperson_Num) group by sale.salesperson_num having maxA > 3000;

select first_name, surname, salary from employee order by salary, surname;

select sale.Sale_Num, customer.name, customer.Customer_Num, salesperson.Salesperson_Num from customer 
inner join sale on (customer.Customer_Num = sale.customer_num) 
inner join salesperson on (sale.Salesperson_Num = salesperson.Salesperson_Num) 
where customer.city <> salesperson.city;