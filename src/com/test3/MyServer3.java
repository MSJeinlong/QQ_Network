package com.test3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author:long
 * @Date: 2018/12/3 21:18
 * @Version 1.0
 */
public class MyServer3 extends JFrame implements ActionListener{

    private JTextArea jta;
    private JTextField jtf;
    private JButton jb;
    private JScrollPane jsp;
    private JPanel jp;
    private PrintWriter pw;

    public static void main(String[] args) {
        new MyServer3();
    }

    public MyServer3() {
        jta = new JTextArea();
        jtf = new JTextField(10);
        jb = new JButton("发送");
        jp = new JPanel();
        jsp = new JScrollPane(jta);

        jb.addActionListener(this);
        jtf.addActionListener(this);

        jp.add(jtf);
        jp.add(jb);

        this.add(jsp, "Center");
        this.add(jp, "South");

        this.setBounds(600, 300, 300, 200);
        this.setVisible(true);
        this.setTitle("QQ简易聊天--Server");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            //服务器监听
            ServerSocket ss = new ServerSocket(9988);
            //等待客户端连接
            Socket s = ss.accept();

            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            pw = new PrintWriter(s.getOutputStream(), true);
            while (true){
                //读取从客户端发来的信息
                String info = br.readLine();
                jta.append("客户端："+info+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //用户按下发送信息的按钮,或者按下回车
        if(e.getSource() == jb || e.getSource() == jtf){
            //把jtf里的信息发送过去
            String info = jtf.getText();
            jta.append("服务器："+info+"\n");
            pw.println(info);
            //清空jtf里面的内容
            jtf.setText(null);
        }
    }
}
