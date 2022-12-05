/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2022-11-25 14:40:10                          */
/*==============================================================*/


drop table if exists Captain;

drop table if exists Car;

drop table if exists MotorCade;

drop table if exists Route_Stop;

drop table if exists Sstop;

drop table if exists Staff;

drop table if exists belongs;

drop table if exists break;

drop table if exists illegal;

drop table if exists route;

drop table if exists works;

/*==============================================================*/
/* Table: Captain                                               */
/*==============================================================*/
create table Captain
(
   Sno                  int not null,
   Sname                char(10),
   Gender               char(1),
   Stel                 char(12),
   Sage                 int,
   primary key (Sno)
);

alter table Captain comment '公交公司的所有队长';

/*==============================================================*/
/* Table: Car                                                   */
/*==============================================================*/
create table Car
(
   CarId                char(8) not null,
   Seat                 smallint,
   primary key (CarId)
);

alter table Car comment '公交公司的汽车';

/*==============================================================*/
/* Table: MotorCade                                             */
/*==============================================================*/
create table MotorCade
(
   Mno                  int not null,
   Sno                  int,
   Mname                char(10),
   primary key (Mno)
);

alter table MotorCade comment '公司有若干车队';

/*==============================================================*/
/* Table: Route_Stop                                            */
/*==============================================================*/
create table Route_Stop
(
   Rno                  int,
   StopName             char(10),
   StopNo               int
);

/*==============================================================*/
/* Table: Sstop                                                 */
/*==============================================================*/
create table Sstop
(
   StopName             char(10) not null,
   primary key (StopName)
);

/*==============================================================*/
/* Table: Staff                                                 */
/*==============================================================*/
create table Staff
(
   Sno                  int not null,
   Sname                char(10),
   Gender               char(1),
   Stel                 char(12),
   Sage                 int,
   primary key (Sno)
);

alter table Staff comment '公交公司的所有员工，包括路队长和普通司机';

/*==============================================================*/
/* Table: belongs                                               */
/*==============================================================*/
create table belongs
(
   CarId                char(8) not null,
   Rno                  int,
   primary key (CarId)
);

/*==============================================================*/
/* Table: break                                                 */
/*==============================================================*/
create table break
(
   Bno                  int not null,
   Sno                  int not null,
   iname                char(10) not null,
   CarId                char(8),
   StopName             char(10),
   Bdate                date,
   Btime                time,
   primary key (Bno)
);

/*==============================================================*/
/* Table: illegal                                               */
/*==============================================================*/
create table illegal
(
   iname                char(10) not null,
   punish               smallint,
   primary key (iname)
);

alter table illegal comment '所有违章类型';

/*==============================================================*/
/* Table: route                                                 */
/*==============================================================*/
create table route
(
   Rno                  int not null,
   Mno                  int,
   Sno                  int,
   Rname                char(10),
   primary key (Rno)
);

/*==============================================================*/
/* Table: works                                                 */
/*==============================================================*/
create table works
(
   Sno                  int not null,
   Rno                  int,
   primary key (Sno)
);


