package com.qq.client.tools;

import java.util.HashMap;

/**
 * @program: Network_QQ
 * @Date: 2018-12-07 19:55
 * @Author: long
 * @Description:这是一个管理客户端和服务器保持通讯的线程类
 */
public class ManageClientConServerThread {
    private static HashMap hm = new HashMap<String, ClientConServerThread>();

    //把创建好的ClientConServerThread放入到hm
    public static void addClientConServerThread(String qqId, ClientConServerThread ccst){
        hm.put(qqId, ccst);
    }

    //可以通过qqId取得该线程
    public static ClientConServerThread getClientConServerThread(String qqID){
        return (ClientConServerThread)hm.get(qqID);
    }

    //移除qqId的通信线程
    public static void closeCCST(String qqId){
        ManageClientConServerThread.hm.remove(qqId);
    }
}
