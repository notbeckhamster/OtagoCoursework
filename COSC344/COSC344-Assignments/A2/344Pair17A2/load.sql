drop table if exists payment;
drop table if exists member_goals;
drop table if exists member_exercise;
drop table if exists exercise;
drop table if exists member;
drop table if exists staff;
drop trigger if exists update_salary;
drop trigger if exists update_gst;

create table staff (
	staff_id varchar(10) not null,
    first_name varchar(255) not null,
    surname varchar(255) not null,
    address varchar(255) not null,
    email varchar(255) not null,
    phone varchar(30) not null,
    date_of_birth date,
    gender enum("Male", "Female", "Other"),
    ird_number varchar(9) not null unique,
    role varchar(100) not null,
    constraint staff_pk primary key (staff_id)
);

create table member (
	member_id varchar(10) not null,
    staff_id varchar(10) not null,
    first_name varchar(255) not null,
    surname varchar(255) not null,
    address varchar(255) not null,
    email varchar(255) not null,
    phone varchar(30) not null,
    date_of_birth date,
    gender enum("Male", "Female", "Other"),
    membership_type enum("Full", "Student", "Senior", "Special") not null,
    join_date date not null,
    card_issued boolean not null,
    notes varchar(255),
    constraint member_pk primary key (member_id),
    constraint member_fk_staff foreign key (staff_id) references staff(staff_id)
);

create table payment(
	payment_id varchar(10) not null,
    member_id varchar(10) not null,
    payment_date timestamp not null,
    method enum("Cash", "EFTPOS", "CreditCard", "Internet", "Other") not null,
    amount decimal(6,2) unsigned not null,
    total decimal(7,2) unsigned not null default 0,
    gst decimal(6,2) unsigned not null default 0,
    constraint payment_pk primary key (payment_id),
    constraint payment_fk foreign key (member_id) references member (member_id)
    
);

create table member_goals (
	member_id varchar(10) not null,
    goal varchar(200) not null,
    constraint member_goals_pk primary key(member_id, goal),
    constraint member_goals_fk foreign key (member_id) references member(member_id)
);

create table exercise (
	exercise_type varchar(100) not null,
    description varchar(500) not null,
    constraint exercise_pk primary key (exercise_type)
);


create table member_exercise (
	member_id varchar(10) not null,
    exercise_type varchar(100) not null,
    repetitions int unsigned not null,
    weight decimal(5,2),
    notes varchar(255),
    constraint member_exercise_pk primary key (exercise_type, member_id),
    constraint member_exercise_fk_member foreign key (member_id) references member(member_id),
    constraint member_exercise_fk_exercise foreign key (exercise_type) references exercise(exercise_type)
    
);



delimiter //
create trigger add_gst
	before insert on payment
    for each row
begin
	set new.gst = (new.amount * 0.15);
    set new.total = new.gst + new.amount;
end //

delimiter //
create trigger update_gst
	before update on payment
    for each row
begin
	set new.gst = (new.amount * 0.15);
    set new.total = new.gst + new.amount;
end //


insert into staff (staff_id, first_name, surname, address, email, phone, date_of_birth, gender, ird_number, role)
values ("11111112", "Glen", "Garcia", "Owheo Lab 6 st", "ggar@example.com", "02108777410", "2003-10-10", "Male", "12345678", "Trainer");

insert into staff (staff_id, first_name, surname, address, email, phone, date_of_birth, gender, ird_number, role)
values ("11111113", "Ronaldo", "Suarez", "34 Benson Rd", "rs109@example.com", "02108777412", "1994-07-05", "Male", "12345679", "Trainer");

insert into member (member_id, staff_id, first_name, surname, address, email, phone, date_of_birth, gender, membership_type, join_date, card_issued)
values ("21111111", "11111112", "Beckham", "Wilson", "126 Goofy Street", "bwil@example.com", "02108777409", "2003-10-10", "Female", "Student", "2003-10-10", true);

insert into member (member_id, staff_id, first_name, surname, address, email, phone, date_of_birth, gender, membership_type, join_date, card_issued)
values ("21111112", "11111113", "John", "Doe", "18 Forth Street", "jdoe123@example.com", "02108777411", "1997-02-21", "Female", "Senior", "2024-11-09", true);

insert into payment (payment_id, member_id, payment_date, method, amount)
values ("1", "21111111", "2003-10-10:13:45:00", "internet", 25.00);

insert into payment (payment_id, member_id, payment_date, method, amount)
values ("2", "21111112", "2024-11-09:8:00:00", "cash", 40.00);

insert into member_goals(member_id, goal) values ("21111111", "I want to be strong enough to withstand Rich Piana's 8 hour arm workout");
insert into member_goals(member_id, goal) values ("21111111", "I want to be able to squat a car");
insert into member_goals(member_id, goal) values ("21111112", "A goal is to get yolked for the beach");
insert into member_goals(member_id, goal) values ("21111112", "I want to be able to impress my mom with my muscles");

insert into exercise (exercise_type, description) values ("Pushups", "Start in a plank position, lower your body until your chest nearly touches the ground, then push back up.");
insert into exercise (exercise_type, description) values ("Squats", "stand with your feet shoulder-width apart, keep your back straight, lower your body by bending your knees and hips until your thighs are parallel to the ground, and then return to the standing position.");
insert into exercise (exercise_type, description) values ("Military Press", "Stand with feet shoulder-width apart, press the barbell overhead until arms are fully extended, then lower it back to shoulder level.");

insert into member_exercise (member_id, exercise_type, repetitions, notes) values ("21111111", "Pushups", "50", "Push past failure certified tom platz moment");
insert into member_exercise (member_id, exercise_type, repetitions, weight, notes) values ("21111111", "Squats", "50", 100, "5 sets of 10 reps with 2 minute rest in between each set");
insert into member_exercise (member_id, exercise_type, repetitions, notes) values ("21111112", "Pushups", "30", "Push past failure certified tom platz moment");
insert into member_exercise (member_id, exercise_type, repetitions, weight, notes) values ("21111112", "Squats", "30", 80, "3 sets of 10 reps with 2 minute rest in between each sets");
insert into member_exercise (member_id, exercise_type, repetitions, weight, notes) values ("21111112", "Military Press", 45, "30", "3 sets of 10 reps with 1 minute rest in between sets");

commit;