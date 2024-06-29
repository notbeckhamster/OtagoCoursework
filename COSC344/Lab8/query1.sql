create view employee_basic_info as select First_Name, Surname, Gender, Address
from employee;
show full tables in company where table_type='BASE TABLE';

create view all_sales (Sale_Num, Customer_Num, Salesperson_Name, Sale_Date, Sale_Amount) as 
select sale_num, customer.name, salesperson.name, sale_date, sale.amount 
from sale inner join salesperson using (salesperson_num) 
inner join customer using (customer_num);

create view customer_sales_above_2k as 
select customer_num, name, rating from customer where customer_num in (
	select customer_num
    from sale where amount > 2000
);

create view employee_project_stats as
select employee_num, employee.first_name as employee_name, count(*) as Number_of_projects, min(hours) as Min_hours_worked, max(hours) as Max_hours_worked 
from employee
inner join works_on using (employee_num)
group by employee_num;

select * from all_sales; 
update all_sales set sale_amount = 75.00 where sale_num = 3007;
select * from all_sales;

select * from customer_sales_above_2k;
update customer_sales_above_2k set rating = 150 where customer_num = 2003;
