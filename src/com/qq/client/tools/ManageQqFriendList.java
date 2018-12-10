package com.qq.client.tools;

import com.qq.client.view.FriendList;

import java.util.HashMap;

/**
 * @program: Network_QQ
 * @Date: 2018-12-10 11:20
 * @Author: long
 * @Description:管理好友、黑名单、界面类
 */
public class ManageQqFriendList {
    private static HashMap hm = new HashMap<String, FriendList>();

    public static void addQqFriendList(String qqid, FriendList qqFriendList){
        hm.put(qqid, qqFriendList);
    }

    public static FriendList getQqFriendList(String qqId){
        return (FriendList)hm.get(qqId);
    }
}
