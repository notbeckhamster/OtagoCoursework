drop function if exists total_salary;
delimiter //
create function total_salary(proj_num integer)
	returns decimal(8,2)
begin
	declare total_salary decimal(8,2) default 0.0;
	select sum(employee.salary) into total_salary from employee inner join works_on on employee.Employee_Num=works_on.Employee_Num where works_on.Project_Num = proj_num;
    return total_salary;
end // 
delimiter ;

select total_salary(2)