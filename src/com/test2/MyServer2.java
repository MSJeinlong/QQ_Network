package com.test2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author:long
 * @Date: 2018/12/3 20:37
 * @Version 1.0
 * 功能：是一个Server,可以通过控制台发送信息
 */
public class MyServer2 {

    public static void main(String[] args) {
        new MyServer2();
    }

    public MyServer2() {
        //在9999端口上监听
        try {
            System.out.println("服务器在9999监听...");
            ServerSocket ss = new ServerSocket(9999);
            //等待连接
            Socket s = ss.accept();
            //先接受客户端发来的信息
            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);

            //接受从控制台输入的信息
            InputStreamReader isr2 = new InputStreamReader(System.in);
            BufferedReader br2 = new BufferedReader(isr2);
            System.out.println("连接建立，对话开始");
            while (true){
                String infoFormClient = br.readLine();
                System.out.println("客户端说： "+infoFormClient);
                if(infoFormClient.equals("bye")){
                    System.out.println("对话接收，退出！");
                    s.close();
                    break;
                }
                //接受从控制台输入的信息
                System.out.println("输入你希望对客户端说的话：");
                String response = br2.readLine();
                //把从控制台接受的信息，回送给客户端.
                pw.println(response);
                if(response.equals("bye")){
                    System.out.println("对话接收，退出！");
                    s.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
