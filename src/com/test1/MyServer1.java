package com.test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 这是第一个服务器端程序，让它在9999端口监听，可以接受从客户端发来的信息
 */
public class MyServer1 {

    public static void main(String[] args){
        MyServer1 ms1 = new MyServer1();
    }

    public MyServer1() {
            try {
                //在端口9999监听
                ServerSocket ss = new ServerSocket(9999);
                System.out.println("我是服务器，在端口9999监听");
                //等待某个客户端来连接，该函数会返回一个Socket连接
                Socket s = ss.accept();
                //要读取s中传递的数据
                InputStreamReader isr = new InputStreamReader(s.getInputStream());
                BufferedReader br = new BufferedReader(isr);

                String info = br.readLine();
                System.out.println("Server: I have accepted => "+info);

                PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
                pw.println("Hi, I'm a Server.");

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
