drop table if exists booking;
drop table if exists room;
drop table if exists building;
drop table if exists staff; 

create table building(
	building_code varchar(10),
    building_name varchar(50) not null,
    building_address varchar(50),
    constraint building_pk primary key (building_code));

create table room(
	building_code varchar(10),
    room_number varchar(10),
    capacity smallint not null,
    constraint room_building_code_fk foreign key (building_code) references building (building_code),
    constraint room_pk primary key (building_code, room_number));

create table staff(
	staff_id varchar(10) ,
    firstname varchar(100) not null,
    surname varchar(100),
    email varchar(100),
    constraint staff_pk primary key (staff_id));
    
create table booking(
	staff_id varchar(10),
    building_code varchar(10),
    room_number varchar(10),
    start_time datetime,
    end_time datetime,
    constraint booking_room_fk foreign key (building_code, room_number) references room (building_code, room_number),
    constraint booking_pk primary key (staff_id, building_code, room_number, start_time));
