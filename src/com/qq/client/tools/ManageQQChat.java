package com.qq.client.tools;

import com.qq.client.view.QQChat;

import java.util.HashMap;

/**
 * @program: Network_QQ
 * @Date: 2018-12-07 20:31
 * @Author: long
 * @Description:
 */
public class ManageQQChat {

    private static HashMap hm = new HashMap<String, QQChat>();

    //添加
    public static void addQQChat(String loginIdAndFriendId, QQChat qqChat){
        hm.put(loginIdAndFriendId, qqChat);
    }
    //取出
    public static QQChat getQQChat(String loginIdAndFriendId){
        return (QQChat)hm.get(loginIdAndFriendId);
    }
}
