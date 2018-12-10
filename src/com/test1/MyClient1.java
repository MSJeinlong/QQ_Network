package com.test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Author:long
 * @Date: 2018/12/3 20:03
 * @Version 1.0
 */
public class MyClient1 {
    public static void main(String[] args) {
        MyClient1 myClient1 = new MyClient1();
    }

    public MyClient1() {
        try {
            //Socket()就是去连接某个服务器端，127.0.0.1表示服务器的地址, 9999是端口号
            Socket s = new Socket("127.0.0.1", 9999);
            //如果s连接成功，就可以发送数据给服务器
            //我们通过pw，向s写数据，autoFlush为true表示及时刷新
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            //通过println向服务器发送信息
            pw.println("Hello, I'm a Client");

            //要读取s中由服务器传递过来的数据
            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String response = br.readLine();
            System.out.println("Client: I have accepted => "+response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
