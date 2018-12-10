package com.qq.client.view;

import com.qq.client.common.Message;
import com.qq.client.common.MessageType;
import com.qq.client.tools.ManageClientConServerThread;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:long
 * @Date: 2018/12/4 21:43
 * @Version 1.0
 * 这是与好友聊天的界面
 * 因为客户端要处于读取的状态，因此要报客户端变成一个线程
 */
public class QQChat extends JFrame implements ActionListener {
    private JTextArea jta;
    private JTextField jtf;
    private JPanel jp1;
    private JScrollPane jsp;
    private JButton jb;
    private String ownerId;
    private String friendId;

    /*public static void main(String[] args) {
        new QQChat("1");
    }*/

    public QQChat(String ownerId, String friend) {
        this.ownerId = ownerId;
        this.friendId = friend;
        jta = new JTextArea();
        jtf = new JTextField(10);
        jtf.addActionListener(this);
        jp1 = new JPanel();
        jsp = new JScrollPane(jta);
        jb = new JButton("发送");
        jb.addActionListener(this);

        jp1.add(jtf);
        jp1.add(jb);

        this.add(jsp, "Center");
        this.add(jp1, "South");

        this.setTitle(ownerId + "正在和 " + friend + " 聊天");
        this.setBounds(500, 200, 300, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

    }

    /*显示消息的方法*/
    public void showMessage(Message m){
        String info = m.getSender()+" 说："+ m.getCon()+" ("+m.getSendTime()+")\r\n";
        this.jta.append(info);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        //用户点击了"发送",或者按下回车键
        if (s == jb || s == jtf) {
            Message m = new Message();
            m.setMesType(MessageType.message_comm_mes);
            m.setSender(ownerId);
            m.setGetter(friendId);
            String mess = jtf.getText();
            m.setCon(mess);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(date);
            m.setSendTime(time);
            jta.append(ownerId + " 说: " + mess + " (" + time + ")\r\n");
            jtf.setText(null);
            //发送信息给服务器
            try {
                ObjectOutputStream oos = new ObjectOutputStream
                        (ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
                oos.writeObject(m);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
