package com.fh.controller.app.appuser;

import com.fh.controller.base.BaseController;
import com.fh.pojo.massage.send.Text;
import com.fh.pojo.massage.send.TextMessage;
import com.fh.pojo.massage.send.Textcard;
import com.fh.pojo.massage.send.TextcardMessage;
import com.fh.service.app.SendMessageService;
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
import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RequestMapping(value="/app_message")
public class OpeMessage extends BaseController {
    @RequestMapping(value = "/sendtext",method= RequestMethod.GET)
    @CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
    @ResponseBody
    public String SendText(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession httpSession = request.getSession();
//        Object tokenid = httpSession.getAttribute("tokenid");
        String accessToken ="";
//        if (tokenid==null){
//            String url="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wwe1de579339958660&corpsecret=DN56liP41YlzMLc1qpzQuPTo1Y7s69ulkRWdbkZLrMs";
//            JSONObject jsonObject = httpRequest(url, "GET", null);
//            accessToken = jsonObject.getString("access_token");
//            httpSession.setMaxInactiveInterval(30 * 120);
//        }else {
//            accessToken=tokenid+"";
//        }
        refreshToken refreshToken = new refreshToken();
        accessToken = refreshToken.IsExistAccess_Token();
        PageData pd = new PageData();
        pd = this.getPageData();
        JSONObject json = new JSONObject();
        String s = pd.getString("content");
        String content = new String(s.getBytes("ISO8859_1"), "UTF-8");
        String user_id = pd.getString("USER_ID");
        //1.创建文本消息对象
        TextMessage message=new TextMessage();
        //1.1非必需
        message.setTouser(user_id);  //不区分大小写
        //textMessage.setToparty("1");
        //txtMsg.setTotag(totag);
        //txtMsg.setSafe(0);
        //1.2必需
        message.setMsgtype("text");
        message.setAgentid(WeiXinParamesUtil.agentId);

        Text text=new Text();
        text.setContent(content);
        message.setText(text);

        //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url

//        System.out.println("accessToken:"+accessToken);

        //3.发送消息：调用业务类，发送消息
        SendMessageService sms=new SendMessageService();
        JSONObject messagejson = sms.sendMessage(accessToken, message);
        String errcode = messagejson.getString("errcode");
        System.out.println(errcode);
        json.put("msg","success");
        json.put("accessToken",accessToken);
        if (!errcode.equals("0")) {

            accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.agentSecret).getToken();
            messagejson = sms.sendMessage(accessToken, message);
            json.put("msg",messagejson.getString("errcode"));
        }
        return json.toString();
    }

    @RequestMapping(value = "/sendtextcard",method=RequestMethod.GET)
    @CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
    @ResponseBody
    public String SendTextcard(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession httpSession = request.getSession();
        Object tokenid = httpSession.getAttribute("tokenid");
        String accessToken ="";
//        if (tokenid==null){
//            String url="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wwe1de579339958660&corpsecret=DN56liP41YlzMLc1qpzQuPTo1Y7s69ulkRWdbkZLrMs";
//            JSONObject jsonObject = httpRequest(url, "GET", null);
//            accessToken = jsonObject.getString("access_token");
//            httpSession.setMaxInactiveInterval(30 * 120);
//        }else {
//            accessToken=tokenid+"";
//        }
        refreshToken refreshToken = new refreshToken();
        accessToken = refreshToken.IsExistAccess_Token();
        PageData pd = new PageData();
        pd = this.getPageData();
        JSONObject json = new JSONObject();
        String s = pd.getString("title");
        String title = new String(s.getBytes("ISO8859_1"), "UTF-8");
        String i = pd.getString("description");
        String description = new String(i.getBytes("ISO8859_1"), "UTF-8");
        String url = pd.getString("url");
        String user_id = pd.getString("USER_ID");

        TextcardMessage message=new TextcardMessage();
        //1.1非必需
        message.setTouser(user_id);  //不区分大小写

        //1.2必需
        message.setMsgtype("textcard");
        message.setAgentid(WeiXinParamesUtil.agentId);

        Textcard textcard=new Textcard();
        textcard.setTitle(title);
        textcard.setDescription(description);
        textcard.setUrl(url);
        message.setTextcard(textcard);

        //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
//        accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.agentSecret).getToken();
//        System.out.println("accessToken:"+accessToken);

        //3.发送消息：调用业务类，发送消息
        SendMessageService sms=new SendMessageService();
        JSONObject messagejson = sms.sendMessage(accessToken, message);
        String errcode = messagejson.getString("errcode");
        System.out.println(errcode);
        json.put("msg","success");
        if (!errcode.equals("0")) {
            json.put("msg",errcode);
        }
        return json.toString();
    }
}
