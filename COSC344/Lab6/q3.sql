drop procedure if exists check_hours_error;
delimiter //
create procedure check_hours_error(in proj_num integer, in min_hours decimal(4,1))
begin 
	declare output text default '';
    declare too_little condition for sqlstate '45000';
    declare current_emp cursor for 
		select First_Name, Surname, Hours from employee inner join works_on on employee.Employee_Num=works_on.Employee_Num where hours < min_hours and project_num = proj_num;
    for emp in current_emp do 
        set output = concat('Warning: ', emp.First_Name, ' ', emp.Surname, ' works for only ', emp.hours, ' hours!\n Checking has been halted and no other employees were checked.');
        signal too_little set message_text = output;
	end for;
end //
delimiter ;

call check_hours_error(2,15);