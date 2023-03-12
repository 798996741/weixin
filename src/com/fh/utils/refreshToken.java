package com.fh.utils;

import com.alibaba.fastjson.JSONObject;
import com.fh.pojo.AccessToken;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;

public class refreshToken {
    /// <summary>
    /// 根据当前日期 判断Access_Token 是否超期  如果超期返回新的Access_Token   否则返回之前的Access_Token
    /// </summary>
    /// <param name="datetime"></param>
    /// <returns></returns>
    public String IsExistAccess_Token()
    {
        String Token = "";
        try {
            AccessToken accessToken = parseXml();

            Token = accessToken.getToken();
            long YouXRQ = accessToken.getExpiresIn();

//            Date YouXRQ = new Date(accessToken.getExpiresIn());
//            String expires_in = element.element("expires_in").getText();

            Date date = new Date();
            System.out.println("Token:"+Token);
            if (date.getTime() > YouXRQ){
                System.out.println("更换token");
                AccessToken mode = GetAccess_token();
                System.out.println(mode.getExpiresIn() + date.getTime());
                String parsetime = parsetime(7000000 + date.getTime());
                inxml(mode.getToken(),parsetime);
                Token=mode.getToken();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Token;
    }

    //POST请求，获取新的Access_token
    public AccessToken GetAccess_token() throws IOException {
        String appid = "wwe1de579339958660";
        String secret = "7EGSTWQFPkn86mI4eMq5UO1Tp1dL0CSDZRo3KRqmIUc";
        String strUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + appid + "&corpsecret=" + secret;
        AccessToken mode = new AccessToken();
        URL url = new URL(strUrl);
        HttpsURLConnection req = (HttpsURLConnection) url.openConnection();
        req.setRequestMethod("GET");
        req.connect();
        int responseCode = req.getResponseCode();
        String data ="";
        if(responseCode == HttpURLConnection.HTTP_OK){
            //得到响应流
            InputStream inputStream = req.getInputStream();
            //将响应流转换成字符串
            data = IOUtils.toString(inputStream, "utf-8");
        }else {
            return mode;
        }
        JSONObject jsonObject = JSONObject.parseObject(data);
        mode.setToken(jsonObject.getString("access_token"));
        mode.setExpiresIn(jsonObject.getInteger("expires_in"));
        return mode;
    }


    @Test
    public void test2(){
        try {
            String appid = "wwe1de579339958660";
            String secret = "7EGSTWQFPkn86mI4eMq5UO1Tp1dL0CSDZRo3KRqmIUc";
            String strUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + appid + "&corpsecret=" + secret;
            AccessToken mode = new AccessToken();
            URL url = new URL(strUrl);
            HttpsURLConnection req = (HttpsURLConnection) url.openConnection();
            req.setRequestMethod("GET");
            req.connect();
            int responseCode = req.getResponseCode();
            String data ="";
            if(responseCode == HttpURLConnection.HTTP_OK){
                //得到响应流
                InputStream inputStream = req.getInputStream();
                //将响应流转换成字符串
                data = IOUtils.toString(inputStream, "utf-8");
            }else {

            }
            JSONObject jsonObject = JSONObject.parseObject(data);
            mode.setToken(jsonObject.getString("access_token"));
            mode.setExpiresIn(jsonObject.getInteger("expires_in"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void inxml2() throws Exception {
        try {
            Properties properties = new Properties();

            properties.setProperty("access_token", "123");
            properties.setProperty("expires_in", "456");
            FileOutputStream outputStream = new FileOutputStream(new File("XMLFile.properties"));
            properties.store(outputStream, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void inxml(String accesstoken,String expiresin) throws Exception {
        try {
            Properties properties = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();

            URL url = loader.getResource("com/fh/file/XMLFile.properties");

            properties.setProperty("access_token", accesstoken);
            properties.setProperty("expires_in", expiresin);
            FileOutputStream outputStream = new FileOutputStream(new File(url.toURI()));
//            FileOutputStream outputStream = new FileOutputStream(url);
            properties.store(outputStream, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
                e.printStackTrace();
        }
    }

    public AccessToken parseXml(){
        Properties properties = new Properties();
        AccessToken accessToken = new AccessToken();
//        InputStreamReader reader = null;
//        OutputStreamWriter writer = null;
        try {
            InputStream inputStream = refreshToken.class.getClassLoader().getResourceAsStream("com/fh/file/XMLFile.properties");
//            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("src/XMLFile.properties"));
//            FileInputStream iFile = new FileInputStream("/XMLFile.properties");
            properties.load(inputStream);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String expires_in1 = properties.get("expires_in").toString();
            String access_token1 = properties.get("access_token").toString();
            Date date = simpleDateFormat.parse(properties.get("expires_in").toString());
            System.out.println(date);
            long expires_in = date.getTime();
            String access_token = properties.get("access_token").toString();

            accessToken.setExpiresIn(expires_in);
            accessToken.setToken(access_token);
//            reader.close();
//            writer.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return accessToken;
    }

//    public void inxml(String accesstoken,String expiresin) throws Exception {
//        try {
//            String path = "XMLFile.properties";
//            File file = new File(path);
////            InputStream inputStream = this.getClass().getResourceAsStream(path);
//            SAXReader reader = new SAXReader();
//            Document doc = reader.read(file);
//            Element rootElement = doc.getRootElement();
//            Element access_token = doc.getRootElement().element("access_token");
//            access_token.setText(accesstoken);
//            Element expires_in = doc.getRootElement().element("expires_in");
//            expires_in.setText(expiresin);
//            OutputFormat format = OutputFormat.createPrettyPrint();
//            System.out.println("写入成功");
//            System.out.println(expiresin);
//            System.out.println(accesstoken);
//            format.setEncoding("UTF-8");
//            try {
//                XMLWriter writer = new XMLWriter(new FileWriter(file),format);
//                //写入数据
//                writer.write(doc);
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

//        public Element parseXml(){
//            Element rootElement=null;
//            try{
//                String path = "/XMLFile.properties";
//                //将src下面的xml转换为输入流
////                InputStream inputStream = new FileInputStream(new File(path));
//                InputStream inputStream = this.getClass().getResourceAsStream(path);//也可以根据类的编译文件相对路径去找xml
//                //创建SAXReader读取器，专门用于读取xml
//                SAXReader saxReader = new SAXReader();
//                //根据saxReader的read重写方法可知，既可以通过inputStream输入流来读取，也可以通过file对象来读取
//                Document document = saxReader.read(inputStream);
////                Document document = saxReader.read(new File(path));//必须指定文件的绝对路径
//                //另外还可以使用DocumentHelper提供的xml转换器也是可以的。
//                //Document document = DocumentHelper.parseText("<?xml version=\"1.0\" encoding=\"UTF-8\"?><modules id=\"123\"><module> 这个是module标签的文本信息</module></modules>");
//
//                //获取根节点对象
//                rootElement = document.getRootElement();
//                Element access_token = rootElement.element("access_token");
//                Element expires_in = rootElement.element("expires_in");
////                System.out.println(access_token.getText());
////                System.out.println(expires_in.getText());
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return rootElement;
//        }
    @Test
    public void parseXml2(){
        Element rootElement=null;
        try{
            String path = "/XMLFile.properties";
            //将src下面的xml转换为输入流
//            InputStream inputStream = new FileInputStream(new File(path));
            InputStream inputStream = this.getClass().getResourceAsStream(path);//也可以根据类的编译文件相对路径去找xml
            //创建SAXReader读取器，专门用于读取xml
            SAXReader saxReader = new SAXReader();
            //根据saxReader的read重写方法可知，既可以通过inputStream输入流来读取，也可以通过file对象来读取
            Document document = saxReader.read(inputStream);
//            Document document = saxReader.read(new File(path));//必须指定文件的绝对路径
            //另外还可以使用DocumentHelper提供的xml转换器也是可以的。
            //Document document = DocumentHelper.parseText("<?xml version=\"1.0\" encoding=\"UTF-8\"?><modules id=\"123\"><module> 这个是module标签的文本信息</module></modules>");

            //获取根节点对象
            rootElement = document.getRootElement();
            Element access_token = rootElement.element("access_token");
            Element expires_in = rootElement.element("expires_in");
            System.out.println(access_token.getText());
            System.out.println(expires_in.getText());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String parsetime(long milliSecond){
        Date date = new Date(milliSecond);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        return str;

    }


}
