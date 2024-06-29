select * from emp9;

select * from dept9;
update dept9
set Name = 'Comp Sci'
where Department_Num=0;
select * from dept9;

select * from emp9;
select * from dept9;

set role select_emp_dept;
select * from emp9;
select * from dept9;

set role none;
select * from emp9;
select * from dept9;
select * from emp_dept_name;

select * from emp_dept_name;
