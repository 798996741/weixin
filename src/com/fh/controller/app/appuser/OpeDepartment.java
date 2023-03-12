package com.fh.controller.app.appuser;

import com.fh.controller.base.BaseController;
import com.fh.pojo.Department;
import com.fh.service.app.Contacts_DepartmentService;
import com.fh.util.PageData;
import com.fh.utils.WeiXinParamesUtil;
import com.fh.utils.WeiXinUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RequestMapping(value="/app_department")
public class OpeDepartment extends BaseController {

    @RequestMapping(value = "/FindDepartment",method= RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
    @ResponseBody
    public String FindDepartment(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession httpSession = request.getSession();
        Object tokenid = httpSession.getAttribute("tokenid");
        String accessToken ="";
        if (tokenid==null){
            accessToken = WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.agentSecret).getToken();
            httpSession.setAttribute("tokenid",accessToken);
            httpSession.setMaxInactiveInterval(30 * 120);
        }else {
            accessToken=tokenid+"";
        }

        PageData pd = new PageData();
        pd = this.getPageData();
        String departmentId="1";
        accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
        Contacts_DepartmentService cds= new Contacts_DepartmentService();
        JSONObject departmentList = cds.getDepartmentList(accessToken, departmentId);
        return departmentList.toString();
    }


    @RequestMapping(value = "/save",method= RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
    @ResponseBody
    public String CreateDepartment(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession httpSession = request.getSession();
        Object tokenid = httpSession.getAttribute("tokenid");
        String accessToken ="";
        if (tokenid==null){
            accessToken = WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.agentSecret).getToken();
            httpSession.setAttribute("tokenid",accessToken);
            httpSession.setMaxInactiveInterval(30 * 120);
        }else {
            accessToken=tokenid+"";
        }

        PageData pd = new PageData();
        pd = this.getPageData();
        JSONObject json = new JSONObject();
        System.out.println(pd);
        int area_id = Integer.parseInt(pd.getString("AREA_ID").replaceAll("[a-zA-Z]","" ));
        String parent_id = pd.getString("PARENT_ID");
        if (parent_id.length()<2){
            parent_id="1";
        }else {
            parent_id=pd.getString("PARENT_ID").replaceAll("[a-zA-Z]","" );
        }

        String name = new String(pd.getString("NAME").getBytes("ISO8859_1"), "UTF-8");
        //1.创建Department对象，并将对象转换成json字符串
        Department department = new Department(area_id, name, Integer.parseInt(parent_id));

        //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
        accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
        System.out.println("accessToken:"+accessToken);

        //3.创建部门
        Contacts_DepartmentService cds= new Contacts_DepartmentService();
        JSONObject massage = cds.createDepartment(accessToken, department);

        String errcode = massage.getString("errcode");
        System.out.println(errcode);
        json.put("msg","success");
        if (!errcode.equals("0")) {
            json.put("msg",errcode);
        }
        return json.toString();
    }

    @RequestMapping(value = "/update",method=RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
    @ResponseBody
    public String UpdateDepartment(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession httpSession = request.getSession();
        Object tokenid = httpSession.getAttribute("tokenid");
        String accessToken ="";
        if (tokenid==null){
            accessToken = WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.agentSecret).getToken();
            httpSession.setAttribute("tokenid",accessToken);
            httpSession.setMaxInactiveInterval(30 * 120);
        }else {
            accessToken=tokenid+"";
        }
        PageData pd = new PageData();
        pd = this.getPageData();
        JSONObject json = new JSONObject();
        System.out.println(pd);
        int area_id = Integer.parseInt(pd.getString("AREA_ID").replaceAll("[a-zA-Z]","" ));
        String parent_id = pd.getString("PARENT_ID");
        if (parent_id.length()<2){
            parent_id="1";
        }else {
            parent_id=pd.getString("PARENT_ID").replaceAll("[a-zA-Z]","" );
        }

        String name = new String(pd.getString("NAME").getBytes("ISO8859_1"), "UTF-8");
        //1.创建Department对象，并将对象转换成json字符串
        Department department = new Department(area_id, name, Integer.parseInt(parent_id));
        //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
        accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
        System.out.println("accessToken:"+accessToken);

        //3.更新部门
        Contacts_DepartmentService cds= new Contacts_DepartmentService();
        JSONObject massage = cds.updateDepartment(accessToken, department);
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
    public String DeleteDepartment(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession httpSession = request.getSession();
        Object tokenid = httpSession.getAttribute("tokenid");
        String accessToken ="";
        if (tokenid==null){
            accessToken = WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.agentSecret).getToken();
            httpSession.setAttribute("tokenid",accessToken);
            httpSession.setMaxInactiveInterval(30 * 120);
        }else {
            accessToken=tokenid+"";
        }
        PageData pd = new PageData();
        pd = this.getPageData();
        JSONObject json = new JSONObject();
        String area_id = pd.getString("AREA_ID");
        String departmentId=area_id.replaceAll("[a-zA-Z]","" );

        //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
        accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
        System.out.println("accessToken:"+accessToken);

        //3.删除部门
        Contacts_DepartmentService cds= new Contacts_DepartmentService();
        JSONObject massage = cds.deleteDepartment(accessToken, departmentId);
        String errcode = massage.getString("errcode");
        json.put("msg","success");
        if (!errcode.equals("0")) {
            json.put("msg",errcode);
        }
        return json.toString();
    }
}
