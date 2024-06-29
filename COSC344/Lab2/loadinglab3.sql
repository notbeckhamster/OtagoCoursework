delete from staff;
delete from room;
delete from building;
delete from booking;
load data local infile '~/Desktop/COSC344/building.txt'
into table building
fields terminated by ','
lines terminated by '\n';

load data local infile '~/Desktop/COSC344/room.txt'
into table room
fields terminated by ','
lines terminated by '\n';

load data local infile '~/Desktop/COSC344/staff.txt'
into table staff
fields terminated by ','
lines terminated by '\n';

load data local infile '~/Desktop/COSC344/booking.txt'
into table booking
fields terminated by ','
lines terminated by '\n';


select * from booking;

select * from staff;
select * from building;
select * from room;