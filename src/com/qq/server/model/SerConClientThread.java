package com.qq.server.model;

import com.qq.client.common.Message;
import com.qq.client.common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @program: Network_QQ
 * @Date: 2018-12-05 22:05
 * @Author: long
 * @Description: 是服务器和某个客户端的通信线程
 */
public class SerConClientThread extends Thread{
    private Socket s;

    public SerConClientThread(Socket s) {
        //把服务器与该客户端的连接付给 s
        this.s = s;
    }

    //让其他线程去通知其他用户
    public void notifyOther(String iam){
        //得到所有在线的人的线程
        HashMap hm = ManageClientThread.getHm();
        Iterator it = hm.keySet().iterator();

        while (it.hasNext()){
            Message m = new Message();
            m.setCon(iam);
            m.setMesType(MessageType.message_ret_onLineFrien);
            //取出在线人的ID
            String onLineUserId = it.next().toString();
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(ManageClientThread.getClientThread(onLineUserId).getS().getOutputStream());
                m.setGetter(onLineUserId);
                oos.writeObject(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            //这里线程就可以接受到客户端的信息
            ObjectInputStream ois = null;
            try {
                    if(!s.isClosed()) {
                        ois = new ObjectInputStream(s.getInputStream());
                        Message m = (Message) ois.readObject();

                        //对从客户端取得消息进行类型判断，然后做相应的处理
                        String messType = m.getMesType();
                        if(messType.equals(MessageType.message_comm_mes)) {
                            System.out.println(m.getSender() + " 给 " + m.getGetter() + "说：" + m.getCon());
                            //转发信息
                            //取得接收人的通信线程
                            SerConClientThread scct = ManageClientThread.getClientThread(m.getGetter());
                            ObjectOutputStream oos = new ObjectOutputStream(scct.s.getOutputStream());
                            oos.writeObject(m);
                        } else if(m.getMesType().equals(MessageType.message_get_onLineFriend)){
                            System.out.println(m.getSender()+" 要他的好友");
                            //把在服务器的好友给该客户端返回
                            String res = ManageClientThread.getAllOnLineUserId();
                            Message m1 = new Message();
                            m1.setMesType(MessageType.message_ret_onLineFrien);
                            m1.setCon(res);
                            m1.setGetter(m.getSender());
                            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                            oos.writeObject(m1);
                        }
                    }
                } catch(Exception e){
                    e.printStackTrace();
                    break;
                } finally {
                try {
                    if(ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Socket getS() {
        return s;
    }
}
