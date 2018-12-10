package com.qq.client.view;

import com.qq.client.common.Message;
import com.qq.client.common.MessageType;
import com.qq.client.common.User;
import com.qq.client.model.QQClientUser;
import com.qq.client.tools.ManageClientConServerThread;
import com.qq.client.tools.ManageQqFriendList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @Author:long
 * @Date: 2018/12/4 19:14
 * @Version 1.0
 */
public class Login extends JFrame implements ActionListener {

    //定义南部的组件
    private JPanel jp1;
    private JButton jp1_jb1, jp1_jb2, jp1_jb3;
    //中部有3个JPanel，有一个选项卡是窗口管理
    private JTabbedPane jtp;
    private JPanel jp2, jp3, jp4;
    private JLabel jp2_jbl1, jp2_jbl2, jp2_jbl3, jp2_jbl4;
    private JButton jp2_jb1;
    private JTextField jp2_jtf;
    private JPasswordField jp2_jpf;
    private JCheckBox jp2_jcb1, jp2_jcb2;

    public static void main(String[] args) {
        new Login();
    }

    public Login() {
        //中部
        jp2 = new JPanel(new GridLayout(3, 3));

        jp2_jbl1 = new JLabel("qq号码", JLabel.CENTER);
        jp2_jbl2 = new JLabel("QQ密码", JLabel.CENTER);
        jp2_jbl3 = new JLabel("忘记密码", JLabel.CENTER);
        jp2_jbl3.setForeground(Color.blue);
        jp2_jbl4 = new JLabel("申请密码保护");
        jp2_jb1 = new JButton("清除");
        jp2_jtf = new JTextField();
        jp2_jpf = new JPasswordField();
        jp2_jcb1 = new JCheckBox("隐身登录");
        jp2_jcb2 = new JCheckBox("记住密码");

        //把控件按照顺序加入到jp2
        jp2.add(jp2_jbl1);
        jp2.add(jp2_jtf);
        jp2.add(jp2_jb1);
        jp2.add(jp2_jbl2);
        jp2.add(jp2_jpf);
        jp2.add(jp2_jbl3);
        jp2.add(jp2_jcb1);
        jp2.add(jp2_jcb2);
        jp2.add(jp2_jbl4);
        //创建选项卡窗口
        jtp = new JTabbedPane();
        jp3 = new JPanel();
        jp4 = new JPanel();
        jtp.add("QQ号码", jp2);
        jtp.add("手机号码", jp3);
        jtp.add("电子邮件", jp4);

        //南部
        jp1 = new JPanel();
        jp1_jb1 = new JButton("登录");
        jp1_jb2 = new JButton("取消");
        jp1_jb3 = new JButton("注册");
        jp1_jb1.addActionListener(this);

        jp1.add(jp1_jb1);
        jp1.add(jp1_jb2);
        jp1.add(jp1_jb3);

        this.add(jtp);
        this.add(jp1, "South");
        this.setTitle("QQ登录");
        this.setBounds(800, 400, 300, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jp1_jb1){
            //如果用户点击登录了
            QQClientUser qqClientUser = new QQClientUser();
            //新建User对象
            User u = new User();
            //拿到用户输入的ID和password
            u.setUserId(jp2_jtf.getText().trim());
            u.setPassword(new String(jp2_jpf.getPassword()).trim());
            System.out.println("密码框内容："+u.getPassword());

            if(qqClientUser.checkUser(u)){

            /*    if(ManageClientConServerThread.getClientConServerThread(u.getUserId()).getS().isClosed()){
                    System.out.println("Socket已经关闭了");
                }*/
                //先创建好友列表
                FriendList friendList  = new FriendList(u.getUserId());
                if(ManageClientConServerThread.getClientConServerThread(u.getUserId()).getS().isClosed()){
                    System.out.println("Socket已经关闭了");
                }
                ManageQqFriendList.addQqFriendList(u.getUserId(), friendList);

                //发送一个要求返回在线好友的请求包
                try {
                    if(ManageClientConServerThread.getClientConServerThread(u.getUserId()).getS().isClosed()){
                        System.out.println("Socket已经关闭了");
                    }
                    ObjectOutputStream oos = new ObjectOutputStream
                            (ManageClientConServerThread.getClientConServerThread(u.getUserId()).getS().getOutputStream());

                    //做一个Message包
                    Message m = new Message();
                    m.setMesType(MessageType.message_get_onLineFriend);
                    //指明我要的是这个qq号的好友情况
                    m.setSender(u.getUserId());
                    oos.writeObject(m);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //登录成功
                this.dispose();

            } else {
                //登录失败
                JOptionPane.showMessageDialog(this, "用户名或者密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
