package com.qq.server.model;

import com.qq.client.common.Message;
import com.qq.client.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

/**
 * @Author:long
 * @Date: 2018/12/4 22:05
 * @Version 1.0
 * 这是qq服务器，它在监听，等待某个qq客户端来连接
 */
public class MyQQServer extends Thread{

    private Socket s;
    private boolean stop = false;

    public MyQQServer() {

    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isStop() {
        return stop;
    }

    @Override
    public void run() {
        try {
            System.out.println("服务器已启动，正在监听...");
            ServerSocket ss = new ServerSocket(9988);
            //阻塞，等待连接
            while (true) {
                s = ss.accept();
                //如果上面得到Socket是用于停止服务器的,则stop为true,退出while
                if(stop){
                    s.close();  //关闭客户端的Socket
                    //把所有的HashMap中所有的Socket关闭
                    closeAllSocket();
                    ss.close(); //关闭服务器端的ServerSocket
                    System.out.println("关闭服务器");
                    break;
                }
                //接受客户端发来的信息
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                User u = (User) ois.readObject();

                Message m = new Message();
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                System.out.println("服务器接受到的用户  id:"+u.getUserId()+" 密码："+u.getPassword());
                if (u.getPassword().equals("123456")) {
                    //返回一个成功登录的信息包
                    m.setMesType("1");
                    oos.writeObject(m);

                    //这里加单开一个线程，让该线程与客户端保持通讯
                    SerConClientThread scct = new SerConClientThread(s);
                    //保存客户端线程，用于qq通信
                    ManageClientThread.addClientThread(u.getUserId(), scct);
                    //启动线程
                    scct.start();

                    //并通知其他在线用户
                    /*scct.notifyOther(u.getUserId());*/
                } else {
                    m.setMesType("2");
                    oos.writeObject(m);
                    System.out.println("服务器关闭了Socket");
                    //关闭连接
                    s.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //关闭所有的与服务器通讯的所有客户端Socket
    public void closeAllSocket(){
        Set<Map.Entry<String, SerConClientThread>> entrySet  = ManageClientThread.getHm().entrySet();
        for(Map.Entry<String, SerConClientThread> entry:entrySet){
            try {
                entry.getValue().getS().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
