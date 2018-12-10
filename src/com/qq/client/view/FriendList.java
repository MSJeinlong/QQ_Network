package com.qq.client.view;

import com.qq.client.common.Message;
import com.qq.client.tools.ManageQQChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @Author:long
 * @Date: 2018/12/4 19:55
 * @Version 1.0
 */
public class FriendList extends JFrame implements ActionListener, MouseListener {

    //处理第一张卡片(我的好友)
    private JPanel jphy1, jphy2, jphy3;
    private JButton jphy1_jb1, jphy3_jb1, jphy3_jb2;
    private JScrollPane jsp;
    private JLabel[] jbls;

    //处理第二张卡片(陌生人)
    private JPanel jpsmr1, jpsmr2, jpsmr3;
    private JButton jpsmr3_jb1, jpsmr3_jb2, jpsmr1_jb1;
    private JScrollPane jsp2;

    //用户ID
    private String ownerId;

    //把整个JFrame设置成CardLayout
    private CardLayout c1;


   /* public static void main(String[] args) {
        new FriendList();
    }*/

    public FriendList(String ownerId) {
        this.ownerId = ownerId;
        //处理第一张卡片，显示我的好友
        jphy1 = new JPanel(new BorderLayout());

        jphy1_jb1 = new JButton("我的好友");
        jphy3_jb1 = new JButton("陌生人");
        jphy3_jb1.addActionListener(this);
        jphy3_jb2 = new JButton("黑名单");

        //假定有50个好友
        jphy2 = new JPanel(new GridLayout(50, 1, 4, 4));

        //给jphy2, 初始化50好友
        jbls = new JLabel[50];

        for (int i = 0; i < jbls.length; i++) {
            jbls[i] = new JLabel(i + 1 + "", new ImageIcon("image/girl1.png"), JLabel.LEFT);
            jbls[i].setEnabled(false);
            if(jbls[i].getText().equals(ownerId)){
                jbls[i].setEnabled(true);
            }
            jphy2.add(jbls[i]);
            jbls[i].addMouseListener(this);

        }

        jphy3 = new JPanel(new GridLayout(2, 1));
        jphy3.add(jphy3_jb1);
        jphy3.add(jphy3_jb2);

        jsp = new JScrollPane(jphy2);

        //对jphy1,初始化
        jphy1.add(jphy1_jb1, "North");
        jphy1.add(jsp, "Center");
        jphy1.add(jphy3, "South");

        //处理第二张卡片, 显示陌生人
        jpsmr1 = new JPanel(new BorderLayout());

        jpsmr3_jb1 = new JButton("我的好友");
        jpsmr3_jb1.addActionListener(this);
        jpsmr3_jb2 = new JButton("陌生人");
        jpsmr1_jb1 = new JButton("黑名单");

        //假定有50个好友
        jpsmr2 = new JPanel(new GridLayout(20, 1, 4, 4));

        //给jphy2, 初始化50好友
        JLabel[] jbls2 = new JLabel[20];

        for (int i = 0; i < jbls2.length; i++) {
            jbls2[i] = new JLabel(i + 1 + "", new ImageIcon("image/girl2.png"), JLabel.LEFT);
            jpsmr2.add(jbls2[i]);
        }

        jpsmr3 = new JPanel(new GridLayout(2, 1));
        jpsmr3.add(jpsmr3_jb1);
        jpsmr3.add(jpsmr3_jb2);

        jsp2 = new JScrollPane(jpsmr2);

        //对jpsmr1,初始化
        jpsmr1.add(jpsmr3, "North");
        jpsmr1.add(jsp2, "Center");
        jpsmr1.add(jpsmr1_jb1, "South");

        //搞卡片布局
        c1 = new CardLayout();
        this.setLayout(c1);
        this.add(jphy1, "1");
        this.add(jpsmr1, "2");

        this.setTitle(ownerId + "的好友列表");
        this.setBounds(800, 200, 200, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    //更新在线好友的好友情况
    public void updateFriend(Message m){
        String onLineFriend[] = m.getCon().split(" ");

        for (int i = 0; i < onLineFriend.length; i++) {
            jbls[Integer.parseInt(onLineFriend[i])- 1].setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jphy3_jb1) {
            c1.show(this.getContentPane(), "2");
        } else if (e.getSource() == jpsmr3_jb1) {
            c1.show(this.getContentPane(), "1");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //响应用户双击的事件，并得到好友的编号
        if (e.getClickCount() == 2) {
            //得到该好友的编号
            String friendNum = ((JLabel) e.getSource()).getText();
            /*System.out.println("你希望和"+friendNum+"聊天");*/
            QQChat qqChat = new QQChat(ownerId, friendNum);

            //把聊天界面加入到管理类
            ManageQQChat.addQQChat(ownerId + " " + friendNum, qqChat);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel jlb1 = (JLabel) e.getSource();
        jlb1.setForeground(Color.red);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel jlb1 = (JLabel) e.getSource();
        jlb1.setForeground(Color.black);
    }
}
