package db;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class UserWindow extends Frame {
    UPDownPanel inputDriver, inputCar, inputBreak;
    UPDownPanel queryDriver, queryBreak, queryStatistics;

    DBcontroller db;

    public UserWindow(String name, DBcontroller db) {
        super(name);
        this.db = db;
        this.setBounds(500, 300, 600, 300);

        initInputDriver();
        initInputCar();
        initInputBreak();
        initQueryDriver();
        initQueryBreak();
        initQueryStatistics();
        JTabbedPane jtp = createJtp();
        this.add(jtp);
        this.addWindowListener(new MyEventListener());
        this.setVisible(true);
    }

    private JTabbedPane createJtp() {
        JTabbedPane jtp = new JTabbedPane();

        jtp.add("录入司机信息", inputDriver);
        jtp.add("录入汽车信息", inputCar);
        jtp.add("录入违章信息", inputBreak);
        jtp.add("查询司机信息", queryDriver);
        jtp.add("查询违章信息", queryBreak);
        jtp.add("统计违章信息", queryStatistics);
        return jtp;
    }

    private void initInputDriver() {
        inputDriver = new UPDownPanel();
        inputDriver.up.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JLabel lname = new JLabel("姓名"), lSno = new JLabel("工号"), lgender = new JLabel("性别"),
                ltele = new JLabel("电话"), lage = new JLabel("年龄"), lroute = new JLabel("工作路线");
        JTextField name = new JTextField(5), Sno = new JTextField(10), tele = new JTextField(10),
                age = new JTextField(5), route = new JTextField(5);

        JRadioButton hasRoute = new JRadioButton(""), noRoute = new JRadioButton("暂无");
        noRoute.setSelected(true);
        hasRoute.addActionListener(e -> route.setEnabled(true));
        noRoute.addActionListener(e -> route.setEnabled(false));
        ButtonGroup seleRoute = new ButtonGroup();
        seleRoute.add(noRoute);
        seleRoute.add(hasRoute);

        JScrollPane scrollPane = new JScrollPane();
        JTextArea jTextArea = new JTextArea(20,20);
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        scrollPane.setViewportView(jTextArea);
        scrollPane.setPreferredSize(new Dimension(500,90));

        JRadioButton man = new JRadioButton("男"), woman = new JRadioButton("女");
        ButtonGroup seleGender = new ButtonGroup();
        seleGender.add(man);
        man.setSelected(true);
        seleGender.add(woman);
        inputDriver.up.add(lname);
        inputDriver.up.add(name);
        inputDriver.up.add(lgender);
        inputDriver.up.add(man);
        inputDriver.up.add(woman);
        inputDriver.up.add(ltele);
        inputDriver.up.add(tele);
        inputDriver.up.add(lage);
        inputDriver.up.add(age);
        inputDriver.up.add(lSno);
        inputDriver.up.add(Sno);
        inputDriver.up.add(lroute);
        inputDriver.up.add(noRoute);
        inputDriver.up.add(hasRoute);
        inputDriver.up.add(route);
        inputDriver.up.add(scrollPane);
        route.setEnabled(false);

        inputDriver.submit.addActionListener(e -> {
            String string = jTextArea.getText();
            try
            {
                String tname = name.getText(), ttele = tele.getText();
                int isno = Integer.parseInt(Sno.getText()), iage = Integer.parseInt(age.getText());
                String sgender = man.isSelected() ? "男" : "女";
                int routeNo = hasRoute.isSelected() ? Integer.parseInt(route.getText()) : -1;
                db.insertDriver(tname, isno, sgender, ttele, iage, routeNo);
                string += "插入成功\n";
            } catch (SQLException exception)
            {
                string += "插入失败\n";
                string += exception.toString() + '\n';
            } catch (NumberFormatException exception)
            {
               // new MessageBox("请输入正确格式的数字",this);
                JOptionPane.showMessageDialog(this,"请输入正确格式的数字","错误",JOptionPane.ERROR_MESSAGE);
            }
            jTextArea.setText(string);
        });
    }

    private void initInputCar() {
        inputCar = new UPDownPanel();
        JLabel lcarID = new JLabel("车牌号"), lSeat = new JLabel("座位数"), lRoute = new JLabel("所属路线");
        JTextField tCarID = new JTextField(8), tSeat = new JTextField(5), tRoute = new JTextField(5);

        JScrollPane scrollPane = new JScrollPane();
        JTextArea jTextArea = new JTextArea(20,20);
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        scrollPane.setViewportView(jTextArea);
        scrollPane.setPreferredSize(new Dimension(500,90));

        JRadioButton hasRoute = new JRadioButton(""), noRoute = new JRadioButton("暂无");
        noRoute.setSelected(true);
        hasRoute.addActionListener(e -> tRoute.setEnabled(true));
        noRoute.addActionListener(e -> tRoute.setEnabled(false));
        ButtonGroup seleRoute = new ButtonGroup();
        seleRoute.add(noRoute);
        seleRoute.add(hasRoute);

        inputCar.up.add(lcarID);
        inputCar.up.add(tCarID);
        inputCar.up.add(lSeat);
        inputCar.up.add(tSeat);
        inputCar.up.add(lRoute);
        inputCar.up.add(noRoute);
        inputCar.up.add(hasRoute);
        inputCar.up.add(tRoute);
        inputCar.up.add(scrollPane);

        inputCar.submit.addActionListener(e -> {
            String string = jTextArea.getText();
            try{
                String carID = tCarID.getText();
                int seat = Integer.parseInt(tSeat.getText()),route = hasRoute.isSelected() ? Integer.parseInt(tRoute.getText()):-1;
                db.insertCar(carID,seat,route);string += "插入成功！！\n";
            }
            catch (SQLException exception)
            {
                string += "插入失败!!\n";
                string += exception.toString() + '\n';
            }
            catch (NumberFormatException exception)
            {
                //new MessageBox("请输入正确格式的数字",this);
                JOptionPane.showMessageDialog(this,"请输入正确格式的数字","错误",JOptionPane.ERROR_MESSAGE);
            }
            jTextArea.setText(string);
        });
    }

    private void initInputBreak()
    {
        inputBreak = new UPDownPanel();
        JLabel jBno = new JLabel("违章编号"),jSno = new JLabel("司机工号"),jCarID = new JLabel("车牌号"),
                jBreak = new JLabel("违章类型"),jStop = new JLabel("站点名称"),
                jDate = new JLabel("日期(年月日)"), jTime = new JLabel("时间(时分秒)");
        JTextField tBno = new JTextField(8),tSno = new JTextField(5),tCarID = new JTextField(8),
                tBreak = new JTextField(8),tStop = new JTextField(8);
        JPanel pDate = new JPanel(),pTime = new JPanel();
        JTextField tYear = new JTextField(4),tMonth = new JTextField(3),tDay = new JTextField(3),
                tHour = new JTextField(3),tMinute = new JTextField(3),tSecond = new JTextField(3);

        Vector<String> v = null;
        try{
            v = db.getIllegals();
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(this,"读取违章类型错误","错误",JOptionPane.ERROR_MESSAGE);
        }
        JComboBox jComboBox = new JComboBox(v);
        pDate.add(tYear);
        pDate.add(tMonth);
        pDate.add(tDay);

        pTime.add(tHour);
        pTime.add(tMinute);
        pTime.add(tSecond);

        JScrollPane scrollPane = new JScrollPane();
        JTextArea jTextArea = new JTextArea(20,20);
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        scrollPane.setViewportView(jTextArea);
        scrollPane.setPreferredSize(new Dimension(500,90));

        inputBreak.up.add(jBno);inputBreak.up.add(tBno);
        inputBreak.up.add(jSno);inputBreak.up.add(tSno);
        inputBreak.up.add(jCarID);inputBreak.up.add(tCarID);
        inputBreak.up.add(jBreak);inputBreak.up.add(jComboBox);
        inputBreak.up.add(jStop);inputBreak.up.add(tStop);
        inputBreak.up.add(jDate);inputBreak.up.add(pDate);
        inputBreak.up.add(jTime);inputBreak.up.add(pTime);
        inputBreak.up.add(scrollPane);

        inputBreak.submit.addActionListener(e -> {
            String string = jTextArea.getText();
            try
            {
                String carID = tCarID.getText(),breakName = (String) jComboBox.getSelectedItem(),stop = tStop.getText();
                int Bno = Integer.parseInt(tBno.getText()),Sno = Integer.parseInt(tSno.getText()),
                        year = Integer.parseInt(tYear.getText()),month = Integer.parseInt(tMonth.getText()),
                        day = Integer.parseInt(tDay.getText()),hour = Integer.parseInt(tHour.getText()),
                        minute = Integer.parseInt(tHour.getText()),second = Integer.parseInt(tSecond.getText());
                db.insertBreak(Bno,Sno,carID,breakName,stop,year,month,day,hour,minute,second);
                string += "插入成功！！\n";
            }catch (SQLException exception)
            {
                string += "插入失败!!\n";
                string += exception.toString() + '\n';
            } catch (NumberFormatException exception)
            {
                //new MessageBox("请输入正确格式的数字",this);
                JOptionPane.showMessageDialog(this,"请输入正确格式的数字","错误",JOptionPane.ERROR_MESSAGE);
            }
            jTextArea.setText(string);
        });
    }

    private void initQueryDriver()
    {
        queryDriver = new UPDownPanel();
        JLabel lCade = new JLabel("车队");
        JTextField tCade = new JTextField(8);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(500,150));

        queryDriver.up.add(lCade);queryDriver.up.add(tCade);
        queryDriver.up.add(scrollPane);
        queryDriver.submit.addActionListener(e -> {
            try
            {
                int cade = Integer.parseInt(tCade.getText());
                ResultSet res = db.queryDriver(cade);
                ResultSetMetaData resultSetMetaData = res.getMetaData();
                Vector<String> columnLabel = new Vector<>();
                Vector<Vector<String>> data = new Vector<>();
                int columnCount = resultSetMetaData.getColumnCount();
                for(int i=1;i<=columnCount;i++)columnLabel.add(resultSetMetaData.getColumnLabel(i));
                while(res.next())
                {
                    Vector<String> row = new Vector<>();
                    for(int i=1;i<=columnCount;i++)row.add(res.getString(i));
                    data.add(row);
                }
                JTable jTable = new JTable(data,columnLabel);
                scrollPane.setViewportView(jTable);

            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(this,ex.toString(),"错误",JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(this,"请输入正确格式的数字","错误",JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initQueryBreak()
    {
        queryBreak = new UPDownPanel();
        JPanel driverSingle = new JPanel(),startSingle = new JPanel();
        driverSingle.setPreferredSize(new Dimension(500,30));
        startSingle.setPreferredSize(new Dimension(500,30));

        //driverSingle.setBackground(new Color(100,0,0));
        JLabel jSno = new JLabel("司机工号"),jStart = new JLabel(" 起始年月日"),jEnd = new JLabel("终止年月日");
        JTextField tSno = new JTextField(8),tYear1 = new JTextField(6),tMonth1 = new JTextField(3),
                tDay1 = new JTextField(3),tYear2 = new JTextField(6),tMonth2 = new JTextField(3),
                tDay2 = new JTextField(3);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(500,150));
        //scrollPane.setViewportView(table);

        driverSingle.add(jSno);driverSingle.add(tSno);
        startSingle.add(jStart);
        startSingle.add(tYear1);startSingle.add(tMonth1);startSingle.add(tDay1);
        startSingle.add(jEnd);
        startSingle.add(tYear2);
        startSingle.add(tMonth2);
        startSingle.add(tDay2);

        queryBreak.up.add(driverSingle);
        queryBreak.up.add(startSingle);
        queryBreak.up.add(scrollPane);

        queryBreak.submit.addActionListener(e -> {
            try{
                int Sno = Integer.parseInt(tSno.getText()),year1 = Integer.parseInt(tYear1.getText()),
                    month1= Integer.parseInt(tMonth1.getText()),day1 = Integer.parseInt(tDay1.getText()),
                    year2 = Integer.parseInt(tYear2.getText()),month2 = Integer.parseInt(tMonth2.getText()),
                    day2 = Integer.parseInt(tDay2.getText());
                ResultSet res = db.queryBreak(Sno,year1,month1,day1,year2,month2,day2);
                ResultSetMetaData resultSetMetaData = res.getMetaData();
                Vector<String> columnLable = new Vector<>();
                int columnCount = resultSetMetaData.getColumnCount();
                for(int i=1;i<=columnCount;i++)columnLable.add(resultSetMetaData.getColumnLabel(i));
                Vector<Vector<String>> data = new Vector<>();
                while (res.next())
                {
                    Vector<String> row = new Vector<>();
                    for(int i=1;i<=columnCount;i++)row.add(res.getString(i));
                    data.add(row);
                }
                JTable table = new JTable(data,columnLable);
                scrollPane.setViewportView(table);
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(this,ex.toString(),"错误",JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException exception)
            {
                JOptionPane.showMessageDialog(this,"请输入正确格式的数字","错误",JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initQueryStatistics()
    {
        queryStatistics = new UPDownPanel();
        JPanel driverSingle = new JPanel(),startSingle = new JPanel();
        driverSingle.setPreferredSize(new Dimension(500,30));
        startSingle.setPreferredSize(new Dimension(500,30));

        //driverSingle.setBackground(new Color(100,0,0));
        JLabel jMno = new JLabel("车队编号"),jStart = new JLabel(" 起始年月日"),jEnd = new JLabel("终止年月日");
        JTextField tMno = new JTextField(8),tYear1 = new JTextField(6),tMonth1 = new JTextField(3),
                tDay1 = new JTextField(3),tYear2 = new JTextField(6),tMonth2 = new JTextField(3),
                tDay2 = new JTextField(3);

        JTextArea output = new JTextArea(20,20);
        output.setEditable(false);
        output.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(500,150));
        scrollPane.setViewportView(output);

        driverSingle.add(jMno);driverSingle.add(tMno);
        startSingle.add(jStart);
        startSingle.add(tYear1);startSingle.add(tMonth1);startSingle.add(tDay1);
        startSingle.add(jEnd);
        startSingle.add(tYear2);
        startSingle.add(tMonth2);
        startSingle.add(tDay2);

        queryStatistics.up.add(driverSingle);
        queryStatistics.up.add(startSingle);
        queryStatistics.up.add(scrollPane);

        // 为按钮注册监听器，使用lambda表达式简化代码
        queryStatistics.submit.addActionListener(e -> {
            try{
                // 从GUI的文本框中获取数据
                int Mno = Integer.parseInt(tMno.getText()),year1 = Integer.parseInt(tYear1.getText()),
                        month1= Integer.parseInt(tMonth1.getText()),day1 = Integer.parseInt(tDay1.getText()),
                        year2 = Integer.parseInt(tYear2.getText()),month2 = Integer.parseInt(tMonth2.getText()),
                        day2 = Integer.parseInt(tDay2.getText());
                String string = output.getText();
                // 调用DBcontroller的函数来发送SQL命令
                String res = db.queryStatistics(Mno,year1,month1,day1,year2,month2,day2);

                // 显示查询结果
                string += String.format("%d号车队在%d-%d-%d到%d-%d-%d期间%s\n",Mno,year1,month1,day1,
                        year2,month2,day2,res);
                output.setText(string);
            } catch (SQLException exception)
            {
                // 将SQL异常信息提升给用户
                JOptionPane.showMessageDialog(this,exception.toString(),"错误",JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException exception)
            {
                // 此异常由上面的的parseInt函数抛出，提示用户没有按要求输入数字
                JOptionPane.showMessageDialog(this,"请输入正确格式的数字","错误",JOptionPane.ERROR_MESSAGE);
            }

        });
    }

    class UPDownPanel extends JPanel
    {
        public JPanel up,down;
        public Button submit,quit;
        public UPDownPanel()
        {
            super(new BorderLayout());
            up = new JPanel();
            down = new JPanel();
            submit = new Button("提交");
            quit = new Button("退出");
            quit.addActionListener(e -> System.exit(1));
            down.add(submit);
            down.add(quit);

            this.add(up,BorderLayout.CENTER);
            this.add(down,BorderLayout.SOUTH);
        }
    }

    class MessageBox extends Frame
    {
        public MessageBox(String message,Component relativeTo)
        {
            Frame frame = new Frame("警告");

            frame.setSize(300,100);
            frame.setLocationRelativeTo(relativeTo);
            UPDownPanel panel = new UPDownPanel();
            JLabel jLabel = new JLabel(message);
            panel.up.add(jLabel);
            panel.submit.setLabel("确定");
            panel.submit.addActionListener(e -> frame.setVisible(false));
            panel.quit.setVisible(false);
            frame.add(panel);
            frame.setVisible(true);
        }
    }
}
