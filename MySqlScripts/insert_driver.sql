insert into captain values(2001,'马云','男','15312345678',40);
insert into captain values(2002,'王健林','男','13912345678',35);
insert into captain values(2003,'范冰冰','女','13812345678',33);


insert into motorcade values(1,2001,'一号车队');
insert into motorcade values(2,2002,'城南车队');
insert into motorcade values(3,2003,'货运车队');

insert into staff values(1001, '张三', '男', '13813687987', 21),
(1002, '李四', '男', '18953478956', 38),
(1003, '狗蛋', '男', '15468789654', 25),
(1004, '李白', '男', '13509858467', 34),
(1005, '小黑', '男', '19876879652', 19),
(1006, '李娜', '女', '15748798321', 30);

insert into route values(1,1,1001,"一号线");
insert into route values(2,1,1002,"二号线");
insert into route values(3,2,1003,"城乡线");
insert into route values(4,3,1004,"建设线");

insert into works values(1001,1),(1002,2),(1003,3),(1004,4),(1005,1);

insert into car values('陕ANZ907',5),('陕AH1001',7),('陕A90007',5);

insert into belongs values('陕ANZ907',1),('陕AH1001',2),('陕A90007',3);

insert into illegal values("闯红灯",6),("未礼让斑马线",3),("压线",3),("违章停车",2);

insert into sstop values('一号起点站'),('西电站'),('西交站'),('一号终点站'),('北大站'),('清华站'),('西农站'),('西外站');

insert into route_stop values(1,'一号起点站',1),(1,'西电站',2),(1,'西交站',3),(1,'一号终点站',4),
	(2,'一号起点站',1),(2,'北大站',2),(2,'一号终点站',3),(3,'一号起点站',1),(3,'清华站',2),(3,'一号终点站',3);
    
INSERT INTO break values(10002,1001,'压线','陕A10003','西交站','2022-11-16','16:16:55'),
(10001,1001,'闯红灯','陕ANZ907','西电站','2022-11-18','13:44:16');