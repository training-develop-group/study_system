package com.example.study_system.util;

import com.example.study_system.emun.StRoleEmun;
import com.example.study_system.model.UserInfo;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.alibaba.fastjson.JSON;

/**
 * author lindan
 * date 2019/10/15.
 */
public final class UserUtil {

    public static UserInfo getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute("user");
        UserInfo userInfo;
        if(StringUtils.isEmpty(user)) {
        	userInfo = new UserInfo();
        	userInfo.setUserId("76b9e812-0496-11ea-a3a7-00ffa210afd0");
        	userInfo.setUserName("张三");
        	userInfo.setStRoleId(StRoleEmun.USER.getStRoleId());
        } else {
        	userInfo = JSON.parseObject(user,UserInfo.class);
        }
        return userInfo;
    }
    /**
     * 利用MD5进行加密
     * @param str
     */
    public static String EncoderByMd5(String str) {
        //确定计算方法
        MessageDigest md5= null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            String newstr= null;
            newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
            return newstr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**判断用户密码是否正确
     *newpasswd 用户输入的密码
     *oldpasswd 正确密码*/
    public static boolean checkpassword(String newpasswd,String oldpasswd){
        if(EncoderByMd5(newpasswd).equals(oldpasswd))
            return true;
        else
            return false;
    }
    public static void main(String args[]){
        System.out.println("-----------main-----------");
//        getUser();
        System.out.println(EncoderByMd5("123456"));

    }
}
