package com.fh.controller.app.appuser;

import com.fh.controller.base.BaseController;
import com.fh.pojo.User;
import com.fh.service.app.Contacts_UserService;
import com.fh.util.PageData;
import com.fh.utils.WeiXinParamesUtil;
import com.fh.utils.WeiXinUtil;
import com.fh.utils.refreshToken;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RequestMapping(value="/app_user")
public class OpeUser extends BaseController {

    @RequestMapping(value = "/FindUser",method= RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
    @ResponseBody
    public String FindUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

//        String tokenid = (String)httpSession.getAttribute("tokenid");
        String accessToken ="";
//        if (tokenid!=null){
//            accessToken=tokenid;
//        }else {
            refreshToken refreshToken = new refreshToken();
            accessToken = refreshToken.IsExistAccess_Token();
            System.out.println("accessToken:"+accessToken);

//        }
        PageData pd = new PageData();
        pd = this.getPageData();
        String departmentId="1";
        String fetchChild="1";
//        accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
        Contacts_UserService cus=new Contacts_UserService();
        JSONObject departmentUserDetails = cus.getDepartmentUserDetails(accessToken, departmentId, fetchChild);
        String errcode = departmentUserDetails.getString("errcode");
        if (!"0".equals(errcode)){
//           accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
            departmentUserDetails = cus.getDepartmentUser(accessToken, departmentId, fetchChild);
            departmentUserDetails.put("msg",departmentUserDetails.getString("errcode")+"二次请求");
        }
        departmentUserDetails.put("accessToken",accessToken);
        return departmentUserDetails.toString();
    }

    @RequestMapping(value = "/save",method= RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
    @ResponseBody
    public String CreateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.addHeader("Access-Control-Allow-Origin", "*");
        PageData pd = new PageData();
        pd = this.getPageData();
        System.out.println(pd);
        JSONObject json = new JSONObject();
//        HttpSession httpSession = request.getSession();
//        Object tokenid = httpSession.getAttribute("tokenid");
        String accessToken ="";
//        if (tokenid==null){
//            accessToken = WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.agentSecret).getToken();
//            httpSession.setAttribute("tokenid",accessToken);
//            httpSession.setMaxInactiveInterval(30 * 120);
//        }else {
//            accessToken=tokenid+"";
//        }
        refreshToken refreshToken = new refreshToken();
        accessToken = refreshToken.IsExistAccess_Token();
        try {
            String userid = pd.getString("USER_ID");
            String name = new String(pd.getString("NAME").getBytes("ISO8859_1"), "UTF-8");
            String department = pd.getString("AREA_ID").replaceAll("[a-zA-Z]","" );
            String phone = pd.getString("PHONE");
            String email = pd.getString("EMAIL");
            String position = new String(pd.getString("POSITION").getBytes("ISO8859_1"), "UTF-8");//职位
            String gender = pd.getString("GENDER");//0男1女
            //1.创建user对象
            User user = new User(userid, name, Integer.parseInt(department), phone, email, position, gender);

            //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
            accessToken = WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
            System.out.println("accessToken:" + accessToken);

            //3.创建成员
            Contacts_UserService cus = new Contacts_UserService();
            JSONObject massage = cus.createUser(accessToken, user);
            String errcode = massage.getString("errcode");
            System.out.println(errcode);
            json.put("msg","success");
            if (!errcode.equals("0") ) {
                json.put("msg",errcode);
            }
        }catch (Exception e){
            json.put("msg","error");
        }

        return json.toString();
    }

    @RequestMapping(value = "/update",method=RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
    @ResponseBody
    public String UpdateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        JSONObject json = new JSONObject();
        System.out.println(pd);
//        HttpSession httpSession = request.getSession();
//        Object tokenid = httpSession.getAttribute("tokenid");
        String accessToken ="";
//        if (tokenid==null){
//            accessToken = WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.agentSecret).getToken();
//            httpSession.setAttribute("tokenid",accessToken);
//            httpSession.setMaxInactiveInterval(30 * 120);
//        }else {
//            accessToken=tokenid+"";
//        }
        refreshToken refreshToken = new refreshToken();
        accessToken = refreshToken.IsExistAccess_Token();
        String userid = pd.getString("USER_ID");
        String name = new String(pd.getString("NAME").getBytes("ISO8859_1"), "UTF-8");
        String department = pd.getString("AREA_ID").replaceAll("[a-zA-Z]","" );
        String phone = pd.getString("PHONE");
        String email = pd.getString("EMAIL");
        String position = new String(pd.getString("POSITION").getBytes("ISO8859_1"), "UTF-8");//职位
        String gender = pd.getString("GENDER");//0男1女
        //1.更改user对象信息
        User user = new User(userid, name, Integer.parseInt(department), phone, email, position, gender);

        //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
//        accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
        System.out.println("accessToken:"+accessToken);

        //3.创建成员
        Contacts_UserService cus=new Contacts_UserService();
        JSONObject massage =cus.updateUser( accessToken,user);
        String errcode = massage.getString("errcode");
        json.put("msg","success");
        if (!errcode.equals("0")) {
            json.put("msg",errcode);
        }
        return json.toString();
    }

    @RequestMapping(value = "/delete",method=RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
    @ResponseBody
    public String DeleteUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        JSONObject json = new JSONObject();
        System.out.println(pd);
        String userId = pd.getString("USER_ID");
        //1.获取userId
        //String userId="ShiRui";
//        HttpSession httpSession = request.getSession();
//        Object tokenid = httpSession.getAttribute("tokenid");
        String accessToken ="";
//        if (tokenid==null){
//            accessToken = WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.agentSecret).getToken();
//            httpSession.setAttribute("tokenid",accessToken);
//            httpSession.setMaxInactiveInterval(30 * 120);
//        }else {
//            accessToken=tokenid+"";
//        }

        //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
//        accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();

        refreshToken refreshToken = new refreshToken();
        accessToken = refreshToken.IsExistAccess_Token();
        System.out.println("accessToken:"+accessToken);
        //3.创建成员
        Contacts_UserService cus=new Contacts_UserService();
        JSONObject massage = cus.deleteUser(accessToken, userId);
        String errcode = massage.getString("errcode");
        json.put("msg","success");
        if (!errcode.equals("0")) {
            json.put("msg",errcode);
        }
        return json.toString();

    }

}
