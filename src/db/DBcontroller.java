package db;

import java.sql.*;
import java.util.Vector;

public class DBcontroller {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/transport?useSSL=false&serverTimezone=UTC";

    static final String USER = "root";
    static final String PASS = "369369";

    Connection connection;
    Statement statement;
    public DBcontroller()
    {
        try{
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            statement = connection.createStatement();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void insertDriver(String name,int id,String gender,String phone,int age,int route) throws SQLException
    {
        String sql = String.format("INSERT INTO staff values(%d,'%s','%s','%s',%d)",id,name,gender,phone,age);
        //System.out.println(sql);
        statement.execute(sql);
        if(route!=-1)
        {
            String routeSql = String.format("INSERT INTO works values(%d,%d)",id,route);
            statement.execute(routeSql);
            return ;
        }
        return ;
    }

    public void insertCar(String carID,int seat,int route) throws SQLException
    {
        String sql = String.format("INSERT INTO car values('%s',%d)",carID,seat);

        statement.execute(sql);
        if(route!=-1)
        {
            String routesql = String.format("INSERT INTO belongs values('%s',%d)",carID,route);
            statement.execute(routesql);
            return ;
        }

        return ;
    }

    public void insertBreak(int Bno,int Sno,String carId,String breakName,String stop,
                            int year,int month,int day,int hour,int minute,int second) throws SQLException
    {
        String sql = String.format("INSERT INTO break values(%d,%d,'%s','%s','%s','%d-%d-%d','%d:%d:%d')",
                Bno,Sno,breakName,carId,stop,year,month,day,hour,minute,second);
        /**
         * 下面代码是用于防止插入的司机、汽车和站点不属于一个线路的，现在已经改为触发器实现了
         */
//        String query = String.format("select count(*) from works,belongs,route_stop where works.sno=%d and belongs.carId='%s'" +
//                " and route_stop.StopName='%s' and works.rno=belongs.rno and works.rno=route_stop.rno",Sno,carId,stop);
//        ResultSet res = statement.executeQuery(query);
//        res.next();
//        int size = res.getInt(1);
//        if(size==0)
//            throw new SQLException("司机、汽车、站点不属于一个线路！！");
//        System.out.println(sql);
        statement.execute(sql);
        return ;
    }

    public ResultSet queryDriver(int cade) throws SQLException
    {
        String sql = String.format("select staff.Sno 工号,staff.Sname 姓名,staff.Gender 性别,staff.Sage 年龄,staff.Stel 电话号码\n" +
                "from route,works,staff where route.Mno=%d and works.Rno=route.Rno and staff.Sno=works.Sno",cade);
        ResultSet res = null;

        res = statement.executeQuery(sql);
        return res;
    }

    public ResultSet queryBreak(int Sno,int year1,int month1,int day1,int year2,int month2,int day2) throws SQLException
    {
        String sql = String.format("select Bno 违章编号,break.Sno 工号,staff.Sname 姓名,iname 违章类型,CarId 车牌号," +
                        "StopName 站点,Bdate 日期,Btime 时间\n" +
                        " from staff,break where Bdate between '%d-%d-%d' and '%d-%d-%d' \n" +
                        "and break.Sno=staff.Sno and staff.Sno=%d;",
                year1,month1,day1,year2,month2,day2,Sno);
        ResultSet res = null;
        res = statement.executeQuery(sql);
        return  res;
    }

    public String queryStatistics(int Mno,int year1,int month1,int day1,int year2,int month2,int day2) throws SQLException
    {
        String sql = String.format("select group_concat(total) 总计 from (\n" +
                        "Select concat(count(break.bno),'次',break.iname) total from route,works,staff,break where\n" +
                        "route.Mno=%d and works.Rno=route.Rno and staff.Sno=works.Sno and break.Sno=staff.Sno\n" +
                        "and bdate between '%d-%d-%d' and '%d-%d-%d' group by break.iname\n" +
                        ") as A;",
                    Mno,year1,month1,day1,year2,month2,day2);
        ResultSet res = statement.executeQuery(sql);
        res.next();
        return res.getString(1)==null? "无违章记录":res.getString(1);

    }

    public Vector<String> getIllegals() throws SQLException
    {
        String sql = "select * from illegal";
        ResultSet res = statement.executeQuery(sql);
        Vector<String> v = new Vector<>();
        while (res.next())
        {
            v.add(res.getString(1));
        }
        return v;
    }

}
