grant select on emp9 to alice;

grant select, update(Name) on dept9 to alice;

revoke all privileges on emp9 from alice;
revoke all privileges on dept9 from alice;

create role select_emp_dept;
grant select on emp9 to select_emp_dept;
grant select on dept9 to select_emp_dept;
grant select_emp_dept to alice;

create view emp_dept_name as 
select First_Name, Surname, Name from emp9 inner join dept9 using (Department_Num);
grant select on emp_dept_name to alice;

create or replace view emp_dept_name as 
select First_Name, Surname, Name from emp9 inner join dept9 using (Department_Num) where salary <= 50000;



