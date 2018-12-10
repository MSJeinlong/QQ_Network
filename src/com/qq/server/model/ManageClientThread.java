package com.qq.server.model;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @program: Network_QQ
 * @Date: 2018-12-07 16:30
 * @Author: long
 * @Description:
 */
public class ManageClientThread {

    private static HashMap hm = new HashMap<String, SerConClientThread>();

    //向hm中添加一个客户端通讯线程
    public static void addClientThread(String uid, SerConClientThread sct){
        hm.put(uid, sct);
    }

    //根据uid从HashMap中取出相应的客户端线程
    public static SerConClientThread getClientThread(String uid){
        return (SerConClientThread)hm.get(uid);
    }

    public static HashMap getHm() {
        return hm;
    }

    public static void closeSCCT(String qqId){
        //移除该线程
        ManageClientThread.hm.remove(qqId);
    }

    //返回当前在线的人的情况
    public static String getAllOnLineUserId(){
        //使用迭代器完成
        Iterator it = hm.keySet().iterator();
        String res = "";
        while (it.hasNext()){
            res += it.next().toString()+" ";
        }
        return res;
    }
}
