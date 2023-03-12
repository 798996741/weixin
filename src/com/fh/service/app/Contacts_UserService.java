package com.fh.service.app;

import com.fh.controller.app.test.UserTest;
import com.fh.pojo.User;
import com.fh.utils.WeiXinUtil;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Contacts_UserService {
    private static Logger log = LoggerFactory.getLogger(UserTest.class);

    private  static  String createUser_url="https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=ACCESS_TOKEN";
    private  static  String getUser_url="https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";
    private  static  String updateUser_url="https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN";
    private  static  String deleteUser_url="https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=ACCESS_TOKEN&userid=USERID";
    private  static  String batchdeleteUser_url="https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token=ACCESS_TOKEN";
    private  static  String getDepartmentUser_url="https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD";
    private  static  String getDepartmentUserDetails_url="https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD";


    //1.创建成员
    public JSONObject createUser(String accessToken,User user) {

        //1.获取json字符串：将user对象转换为json字符串
        Gson gson = new Gson();
        String jsonU1 =gson.toJson(user);      //使用gson.toJson(user)即可将user对象顺序转成json
//        System.out.println("jsonU1:"+jsonU1);


        //2.获取请求的url
        createUser_url=createUser_url.replace("ACCESS_TOKEN", accessToken);

        //3.调用接口，发送请求，创建成员
        JSONObject jsonObject = WeiXinUtil.httpRequest(createUser_url, "POST", jsonU1);
//        System.out.println("jsonObject:"+jsonObject.toString());

        //4.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                log.error("创建成员失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return jsonObject;
    }
    //2.获取成员
    public JSONObject getUser(String accessToken,String userId) {

        //1.获取请求的url
        String url=getUser_url.replace("ACCESS_TOKEN", accessToken)
                .replace("USERID", userId);

        //2.调用接口，发送请求，获取成员
        JSONObject jsonObject = WeiXinUtil.httpRequest(url, "GET", null);
        System.out.println("jsonObject:"+jsonObject.toString());

        //3.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                log.error("获取成员失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return jsonObject;
    }

    //3.更新成员
    public JSONObject updateUser(String accessToken,User user) {

        //1.获取json字符串：将user对象转换为json字符串
        Gson gson = new Gson();
        String jsonU1 =gson.toJson(user);      //使用gson.toJson(user)即可将user对象顺序转成json
        System.out.println("jsonU1:"+jsonU1);


        //2.获取请求的url
        updateUser_url=updateUser_url.replace("ACCESS_TOKEN", accessToken);

        //3.调用接口，发送请求，创建成员
        JSONObject jsonObject = WeiXinUtil.httpRequest(updateUser_url, "POST", jsonU1);
        System.out.println("jsonObject:"+jsonObject.toString());

        //4.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                log.error("更新成员失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return jsonObject;
    }

    //4.删除成员
    public JSONObject deleteUser(String accessToken,String userId) {

        //1.获取请求的url
        deleteUser_url=deleteUser_url.replace("ACCESS_TOKEN", accessToken)
                .replace("USERID", userId);

        //2.调用接口，发送请求，删除成员
        JSONObject jsonObject = WeiXinUtil.httpRequest(deleteUser_url, "GET", null);
        System.out.println("jsonObject:"+jsonObject.toString());

        //3.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                log.error("删除成员失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return jsonObject;
    }

    //5.批量删除成员
    public void batchdeleteUser(String accessToken,List<String> userIdList){
        //1.获取json字符串：将user对象转换为json字符串
        Map<String, Object> content = new HashMap<String, Object>();
        content.put("useridlist", userIdList);

        Gson gson=new Gson();
        String useridlist=gson.toJson(content);
        System.out.println(useridlist);

        //2.获取请求的url
        batchdeleteUser_url=batchdeleteUser_url.replace("ACCESS_TOKEN", accessToken);

        //3.调用接口，发送请求，创建成员
        JSONObject jsonObject = WeiXinUtil.httpRequest(batchdeleteUser_url, "POST", useridlist);
        System.out.println("jsonObject:"+jsonObject.toString());

        //4.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                log.error("批量删除成员失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
    }


    //6.获取部门成员
    public JSONObject getDepartmentUser(String accessToken,String departmentId,String fetchChild) {

        //1.获取请求的url
        getDepartmentUser_url=getDepartmentUser_url.replace("ACCESS_TOKEN", accessToken)
                .replace("DEPARTMENT_ID", departmentId)
                .replace("FETCH_CHILD", fetchChild);

        //2.调用接口，发送请求，获取部门成员
        JSONObject jsonObject = WeiXinUtil.httpRequest(getDepartmentUser_url, "GET", null);
        System.out.println("jsonObject:"+jsonObject.toString());

        //3.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                log.error("获取部门成员失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return jsonObject;
    }


    //7.获取部门成员详情
    public JSONObject getDepartmentUserDetails(String accessToken,String departmentId,String fetchChild) {
        //1.获取请求的url
        getDepartmentUserDetails_url=getDepartmentUserDetails_url.replace("ACCESS_TOKEN", accessToken)
                .replace("DEPARTMENT_ID", departmentId)
                .replace("FETCH_CHILD", fetchChild);

        //2.调用接口，发送请求，获取部门成员
        JSONObject jsonObject = WeiXinUtil.httpRequest(getDepartmentUserDetails_url, "POST", null);
        System.out.println("jsonObject:"+jsonObject.toString());

        //3.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                log.error("获取部门成员详情失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return jsonObject;
    }

}
