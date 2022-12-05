package db;

public class App {
    public static void main(String[] args)
    {
        /**
         * DBcontroller和UserWindow都是编写的类，前者用于连接数据库和发送SQL命令
         * 后者是自定义的GUI，并绑定了DBcontroller的函数。
         */
        DBcontroller db = new DBcontroller();
        UserWindow win = new UserWindow("公交安全管理系统",db);

    }
}
