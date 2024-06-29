drop procedure if exists check_hours_warning;
delimiter //
create procedure check_hours_warning(in proj_num integer, in min_hours decimal(4,1))
begin 
	declare output text default '';
    for emp in (
		select First_Name, Surname, Hours from employee inner join works_on on employee.Employee_Num=works_on.Employee_Num where hours < min_hours and project_num = proj_num) do
        set output = concat('Warning: ', emp.First_Name, ' ', emp.Surname, ' works for only ', emp.hours, ' hours!\n', output);
	end for;
    select output;
end //
delimiter ;

call check_hours_warning(2,15);