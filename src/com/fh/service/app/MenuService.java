package com.fh.service.app;

import com.fh.pojo.menu.Button;
import com.fh.pojo.menu.ComplexButton;
import com.fh.pojo.menu.Menu;
import com.fh.pojo.menu.ViewButton;
import com.fh.utils.WeiXinUtil;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuService {
    private static Logger log = LoggerFactory.getLogger(MenuService.class);
    // 菜单创建（POST） 限100（次/天）
    public static String create_menu_url = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN&agentid=AGENTID";

    /**
     * 1.创建菜单
     *
     * @param menu 菜单实例
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败
     */
    public void createMenu(String accessToken, Menu menu, int agentId) {

        //1.获取json字符串：将Menu对象转换为json字符串
        Gson gson = new Gson();
        String jsonMenu =gson.toJson(menu);      //使用gson.toJson(user)即可将user对象顺序转成json
        System.out.println("jsonMenu:"+jsonMenu);


        //2.获取请求的url
        create_menu_url = create_menu_url.replace("ACCESS_TOKEN", accessToken)
                .replace("AGENTID", String.valueOf(agentId));

        //3.调用接口,发送请求，创建菜单
        JSONObject jsonObject = WeiXinUtil.httpRequest(create_menu_url, "POST", jsonMenu);
        System.out.println("jsonObject:"+jsonObject.toString());

        //4.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }

    }

    /**
     * 2.组装菜单数据
     *
     * @return
     */
    public  Menu getMenu() {
        ViewButton btn11 = new ViewButton();
        btn11.setName("我要请假");
        btn11.setType("view");
       btn11.setUrl("http://jagtzx.natappfree.cc/app_goleave.do");
//        btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wwe1de579339958660&redirect_uri=http://jagtzx.natappfree.cc/WEB-INF/jsp/weixin/MTAuthorization.jsp&response_type=code&scope=snsapi_privateinfo&agentid=1000005&state=hec#wechat_redirect");


        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("日常审核");
        mainBtn1.setSub_button(new Button[] { btn11 });



        /**
         * 这是企业号目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1 });

        return menu;
    }
}

