delete from staff where sno = 1010;

insert into break values(10001,1001,'闯红灯','闽JNz907','西电站','2022-11-25','16:06:55');
delete from break where bno=10001;

select staff.Sno 工号,staff.Sname 姓名,staff.Gender 性别,staff.Sage 年龄,staff.Stel 电话号码
from route,works,staff where route.Mno=1 and works.Rno=route.Rno and staff.Sno=works.Sno;

delete from break where bno=10001;
delete from illegal;
INSERT INTO break values(10002,1001,'压线','京A996996','西交站','2022-11-16','16:16:55');
select * from illegal where iname='压线';

select Bno 违章编号,break.Sno,staff.Sname,iname,CarId,StopName,Bdate,Btime
 from staff,break where Bdate between '2022-11-3' and '2022-11-20' 
and break.Sno=staff.Sno and staff.Sno=1001;

drop view statistics;


create view statistics(Mno,total,bdate) as(
select route.Mno,concat(count(break.bno),'次',break.iname),break.bdate from route,works,staff,break where
 works.Rno=route.Rno and staff.Sno=works.Sno and break.Sno=staff.Sno  group by break.iname);

select * from statistics;

select group_concat(total) 总计 from statistics where mno=1 and bdate between '2022-11-3' and '2022-11-20';

select group_concat(total) 总计 from (
Select concat(count(break.bno),'次',break.iname) total from route,works,staff,break where
route.Mno=1 and works.Rno=route.Rno and staff.Sno=works.Sno and break.Sno=staff.Sno
and bdate between '2022-11-3' and '2022-11-20' group by break.iname
) as A;



select works.rno from works,belongs,route_stop where works.sno=1006 and belongs.carId='陕A10002' 
                 and route_stop.StopName='一号起点站' and works.rno=belongs.rno and works.rno=route_stop.rno;
                 
delete from break where bno=10004;
delete from break where bno=10005;

insert into break values(10004, 1001, '闯红灯', '陕AH1001', '西电站', '2022-11-18', '13:44:16');

insert into break values(10005, 1001, '闯红灯', '陕ANZ907', '西电站', '2022-11-18', '13:44:16');     
                