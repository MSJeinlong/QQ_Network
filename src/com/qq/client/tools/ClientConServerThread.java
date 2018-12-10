package com.qq.client.tools;

import com.qq.client.common.Message;
import com.qq.client.common.MessageType;
import com.qq.client.view.FriendList;
import com.qq.client.view.QQChat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @program: Network_QQ
 * @Date: 2018-12-07 19:51
 * @Author: long
 * @Description:这是客户端和服务器端保持通讯的线程
 */
public class ClientConServerThread extends Thread {
    private Socket s;

    public ClientConServerThread(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        while (true) {
            //不停的读取从服务器端发来的信息
            ObjectInputStream ois = null;
            try {
                if (!s.isClosed()) {
                    ois = new ObjectInputStream(s.getInputStream());
                    Message m = (Message) ois.readObject();
                    /*System.out.println("读取从服务器发来的消息" + m.getSender() + " 给 " + m.getGetter() + "发送：" + m.getCon());*/

                    //判断服务器发过来的消息包的类型
                    String messType = m.getMesType();
                    if (messType.equals(MessageType.message_comm_mes)) {
                        //把从服务器获得消息，显示到该显示的聊天界面
                        QQChat qqChat = ManageQQChat.getQQChat(m.getGetter() + " " + m.getSender());
                        //显示
                        qqChat.showMessage(m);
                    } else if (messType.equals(MessageType.message_ret_onLineFrien)) {
                        System.out.println("客户端接受到：" + m.getCon());
                        String con = m.getCon();
                        String friends[] = con.split(" ");
                        String getter = m.getGetter();
                        //修改相应的好友列表
                        FriendList qqFriendList = ManageQqFriendList.getQqFriendList(getter);

                        //更新在线好友
                        if (qqFriendList != null) {
                            qqFriendList.updateFriend(m);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            } finally {
                try {
                    if (ois != null) {
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
