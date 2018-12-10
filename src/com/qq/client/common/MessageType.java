package com.qq.client.common;

/**
 * @program: Network_QQ
 * @Date: 2018-12-10 10:53
 * @Author: long
 * @Description:
 */
public interface MessageType {

    String message_succeed = "1";   //表明登录成功
    String message_login_fail = "2";    //表明登录失败
    String message_comm_mes = "3";      //普通的信息包
    String message_get_onLineFriend = "4";  //要求在线好友
    String message_ret_onLineFrien = "5";   //返回好友在线的包
}
