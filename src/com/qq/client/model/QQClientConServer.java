package com.qq.client.model;

import com.qq.client.common.Message;
import com.qq.client.common.User;
import com.qq.client.tools.ClientConServerThread;
import com.qq.client.tools.ManageClientConServerThread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @Author:long
 * @Date: 2018/12/5 10:47
 * @Version 1.0
 * 这是客户端连接服务器的后台
 */
public class QQClientConServer {

    public  Socket s;
/*    private ObjectOutputStream oos;
    private ObjectInputStream ois;*/

    public boolean SendInfoToServer(Object o){
        boolean b = false;

        try {
            s = new Socket("127.0.0.1", 9988);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(o);
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            Message ms = (Message) ois.readObject();

            //验证登录是否合法
            if(ms.getMesType().equals("1")) {
                //创建一个该qq号和服务器端保持通讯连接的线程
                ClientConServerThread ccst = new ClientConServerThread(s);
                //启动该通讯线程
                ccst.start();
                ManageClientConServerThread.addClientConServerThread(((User)o).getUserId(), ccst);
                b = true;
            }
            else {
                System.out.println("登录不合法");
                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
}
