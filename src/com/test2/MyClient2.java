package com.test2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Author:long
 * @Date: 2018/12/3 20:46
 * @Version 1.0
 */
public class MyClient2 {
    public static void main(String[] args) {
        new MyClient2();
    }

    public MyClient2() {

        try {
            //连接服务器
            Socket s = new Socket("127.0.0.1", 9999);

            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            //读取控制台发过来的信息
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);

            //读取服务器发过来的信息
            InputStreamReader isr2 = new InputStreamReader(s.getInputStream());
            BufferedReader br2 = new BufferedReader(isr2);
            System.out.println("连接建立，对话开始");
            while (true){
                System.out.println("请输入你想对服务器想说的话");
                //客户端先从控制台接受
                String info = br.readLine();
                //然后发送给服务器
                pw.println(info);
                //如果客户端说了"bye",就关闭连接
                if(info.equals("bye")){
                    System.out.println("对话结束，退出！");
                    s.close();
                    break;
                }
                //接收从服务器发来的话
                String res = br2.readLine();
                System.out.println("服务器说："+res);
                if(res.equals("bye")){
                    System.out.println("对话结束，退出！");
                    s.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
