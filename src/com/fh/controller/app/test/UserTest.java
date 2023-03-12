package com.fh.controller.app.test;

import com.fh.pojo.User;
import com.fh.service.app.Contacts_UserService;
import com.fh.utils.WeiXinParamesUtil;
import com.fh.utils.WeiXinUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class UserTest {
    //1.创建成员
    @Test
    public void testCreateUser() {
        //1.创建user对象
        //User user = new User("3", "ray", 1,"13886086292","3057046319@qq.com","技术顾问","1");
        User user = new User("qingshu", "青树", 1,"13117916616","","前端开发","1");
        //User user = new User("5", "tom", 1,"14986086294","5057046319@qq.com","产品经理","1");

        //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
        String accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
        System.out.println("accessToken:"+accessToken);

        //3.创建成员
        Contacts_UserService cus=new Contacts_UserService();
        cus.createUser(accessToken,user);

    }
    //2.获取成员
    @Test
    public void testGetUser() {
        //1.获取userId
        //String userId="ShiRui";
        String userId="AAAKunLin";

        //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
        String accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
        System.out.println("accessToken:"+accessToken);

        //3.获取成员
        Contacts_UserService cus=new Contacts_UserService();
        cus.getUser(accessToken, userId);
    }

    //3.更新成员
    @Test
    public void testUpdateUser() {

        //1.更改user对象信息
        //User user = new User("3", "ray", 1,"13886086292","3057046319@qq.com","技术顾问","1");
        //User user = new User("4", "jack", 1,"13986086293","4057046319@qq.com","咨询顾问","1");
        User user = new User("3", "ray", 1,"13889086292","3057946319@qq.com","咨询顾问","1");

        //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
        String accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
        System.out.println("accessToken:"+accessToken);

        //3.创建成员
        Contacts_UserService cus=new Contacts_UserService();
        cus.updateUser( accessToken,user);

    }

    //4.删除成员
    @Test
    public void testDeleteUser() {
        //1.获取userId
        //String userId="ShiRui";
        String userId="qingshu";

        //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
        String accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
        System.out.println("accessToken:"+accessToken);

        //3.创建成员
        Contacts_UserService cus=new Contacts_UserService();
        cus.deleteUser(accessToken, userId);
    }


    //5.批量删除成员
    @Test
    public void testbatchdeleteUser() {
        //1.获取userIdList
        String userId1="3";
        String userId2="4";
        List<String> userIdList = Arrays.asList(userId1, userId2);  //此时将userIdList转json,则结果为：["3","4"],会报错：{"errcode":40063,"errmsg":"some parameters are empty"}

        //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
        String accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
        System.out.println("accessToken:"+accessToken);

        //3.批量删除成员
        Contacts_UserService cus=new Contacts_UserService();
        cus.batchdeleteUser(accessToken,userIdList);
    }


    //6.获取部门成员
    @Test
    public void testGetDepartmentUser() {
        //1.获取部门ID以及是否获取子部门成员
        String departmentId="1";
        String fetchChild="1";

        //2.获取accessToken:根据企业id和通讯录密钥获取access_token,并拼接请求url
        String accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
        System.out.println("accessToken:"+accessToken);

        //3.获取部门成员
        Contacts_UserService cus=new Contacts_UserService();
        cus.getDepartmentUser(accessToken, departmentId, fetchChild);
    }

    //7.获取部门成员详情
    @Test
    public void testGetDepartmentUserDetails() {
        //1.获取部门ID以及是否获取子部门成员
        String departmentId="1";
        String fetchChild="1";

        //2.获取accessToken:根据企业id和通讯录密钥获取access_token,并拼接请求url
        String accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
        System.out.println("accessToken:"+accessToken);

        //3.获取部门成员
        Contacts_UserService cus=new Contacts_UserService();
        cus.getDepartmentUserDetails(accessToken, departmentId, fetchChild);
    }
}
