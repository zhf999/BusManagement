drop trigger tri_test;
delimiter //
create trigger tri_test
before insert on break
for each row
begin
	SET @x = (select rno from works,staff where staff.sno=new.sno and works.sno=new.sno);
	SET @y = (select rno from car,belongs where car.carID=new.CarId and belongs.CarId=new.carId);
    if @x=@y then
		SIGNAL SQLSTATE '45000';
	end if;
end
//
delimiter ;


delimiter //
create trigger tri_test
before insert on break
for each row
begin
	set @cnt = (select count(*) from works,belongs,route_stop where
    works.sno=new.sno and belongs.carId= new .carId and route_stop.StopName=new.StopName 
    and works.rno=belongs.rno and works.rno=route_stop.rno);
    if @cnt=0 then
		SIGNAL SQLSTATE '45000';
	end if;
end
//
delimiter ;