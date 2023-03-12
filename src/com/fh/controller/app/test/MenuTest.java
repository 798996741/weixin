package com.fh.controller.app.test;

import com.fh.pojo.menu.Menu;
import com.fh.service.app.MenuService;
import com.fh.utils.WeiXinParamesUtil;
import com.fh.utils.WeiXinUtil;
import org.junit.Test;

public class MenuTest {
    @Test
    public void testCreateMenu(){
        //1.组装菜单
        MenuService ms=new MenuService();
        Menu menu=ms.getMenu();

        //2.获取access_token:根据企业id和应用密钥获取access_token
        String accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.agentSecret).getToken();
        System.out.println("accessToken:"+accessToken);

        //3.创建菜单
        ms.createMenu( accessToken, menu, WeiXinParamesUtil.agentId);

    }

}
