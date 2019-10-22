package com.example.ldemo;

import com.alibaba.fastjson.JSONObject;
import com.example.ldemo.utils.HttpClient;
import com.example.ldemo.utils.HttpUtils;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.util.Cookie;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @package: com.example.ldemo
 * @className: ${TYPE_NAME}
 * @description: 类作用描述
 * @author: 李臣臣
 * @createDate: 2019/10/12 10:26
 * @updateUser: 李臣臣
 * @updateDate: 2019/10/12 10:26
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
public class TestLogin {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    final  String appId = "9e90f8465fc55bc2015fe24dfb4a0009";
    @Test
    public void  login(){

            String username = "GAMCTP-00002";
            String password = "jingan1906";
            String yzm ="4lt8";
            String loginUrl = "https://pacas-login.pingan.com.cn/cas/PA003/EPCIS_PTP/login";
            //webclient设置
            //创建一个webclient
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            // 启动JS
            webClient.getOptions().setJavaScriptEnabled(true);
            //忽略ssl认证
            webClient.getOptions().setUseInsecureSSL(true);
            //禁用Css，可避免自动二次请求CSS进行渲染
            webClient.getOptions().setCssEnabled(false);
            //运行错误时，不抛出异常
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            //出错不报异常
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            // 设置Ajax异步
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            //登录
            HashMap<Object, Object> map = new HashMap<>();
            Boolean b = false;
            try {
                HtmlPage page = (HtmlPage) webClient.getPage(loginUrl);

                if(page.getForms().size()>0){

                    HtmlForm form = page.getForms().get(0);
                    //用户名text框
                    HtmlTextInput txtUName = (HtmlTextInput) form.getInputByName("username");
                    txtUName.setValueAttribute(username);
                    //密码框
                    HtmlPasswordInput txtPwd = (HtmlPasswordInput) form.getInputByName("password");
                    txtPwd.setValueAttribute(password);
                    //验证码
                    HtmlTextInput code = (HtmlTextInput) form.getInputByName("code");
                    code.setValueAttribute(yzm);
                    //获得提交按钮
                    HtmlInput submit = form.getInputByValue("立即登录");
                    //登录进入
                    page = (HtmlPage) submit.click();

                    String loadPage = page.asText();

                    if (loadPage.contains("投保处理")) {
                        b = false;

                    } else {
                        b = true;
                        System.out.println("登陆成功");
                    //    logger.debug("登陆成功" + "____________________________{账号}" + username);
                    }
                }
            } catch (Exception e) {
                System.out.println("登陆失败");
              //  logger.error(e.getMessage(), e);
              //  logger.error("登陆失败" + "____________________________{账号}" + username);
            }
            if (b) {
                //根据传递的标识判断进行的功能


            }

        //退出

            webClient.close();
        }


    public String yzm(){
        String pinanUrl = "https://pacas-login.pingan.com.cn/cas/genRandCode?param=1569575040634&appId=9e90f8465fc55bc2015fe24dfb4a0009";

        Map p = new HashMap<>();
        p.put( "appId", appId);


        String pResult =  new HttpClient().doPost(pinanUrl,p);


        JSONObject jsonObject = JSONObject.parseObject(pResult);

       // return jsonObject.getJSONObject("content").getString("data");


        String host = "http://apigateway.jianjiaoshuju.com";
        String path = "/api/v_1/yzm.html";
        String method = "POST";
        String appcode = "B18AD914CFEB5A2114B0D98E4816C0A6";
        String appKey = "AKID87c6ff0489ae14d2cbfcbbb32f9dfbeb";
        String appSecret = "3186db78704bd32d321009caa21d8697";
        Map headers = new HashMap();
        headers.put("appcode",appcode);
        headers.put("appKey",appKey);
        headers.put("appSecret",appSecret);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map querys = new HashMap();
        Map bodys = new HashMap();
//        bodys.put("v_pic", "data:image/jpeg;base64,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");
        bodys.put("v_pic", jsonObject.getJSONObject("content").getString("data"));
        bodys.put("v_type", "ne4");
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println("q============================="+response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));

          //{"msg":"查询成功!","v_code":"nm5x","errCode":0,"v_type":"ne4"}


      //      logger.debug(JSONObject.parseObject(EntityUtils.toString(response.getEntity())).get("v_code").toString()+"<<<<<");

            return  JSONObject.parseObject(EntityUtils.toString(response.getEntity())).get("v_code").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }



    @Test
    public void  login111(){

        String loginUrl = "http://epcis-ptp.pingan.com.cn/CAS_SUCCESS_LOGIN?CAS_SSO_COOKIE=9e90f8465fc55bc2015fe24dfb4a0009-4c2aa443d7f94455af20161de24787bb&PASESSION=i4APC80rK2lggttfGWq7L-tgde35ac6kK5gVSpQjRf4-0BsBdm7R3S2tOS-IADL%21P189yTfTFpoX2YqQHHIsqlnX9MnR7LfLZEqPp0ofjkktqKveKOYptzdjb3jmwZsIlVr2ZL1r5tXjsREsDL-UeQ%3D%3D%7CA0yxOC0zMxMx%3DiMDNAozjMo%3DMxSg";

        //webclient设置
        //创建一个webclient
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        // 启动JS
        webClient.getOptions().setJavaScriptEnabled(true);
        //忽略ssl认证
        webClient.getOptions().setUseInsecureSSL(true);
        //禁用Css，可避免自动二次请求CSS进行渲染
        webClient.getOptions().setCssEnabled(false);
        //运行错误时，不抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //出错不报异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        // 设置Ajax异步
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        //登录
        HashMap<Object, Object> map = new HashMap<>();
        Boolean b = false;
        try {
            HtmlPage page = (HtmlPage) webClient.getPage(loginUrl);

            CookieManager CM = webClient.getCookieManager();
            Set<Cookie> cookies = CM.getCookies();//返回的Cookie在这里，下次请求的时候可能可以用上啦。

            String cks = "";
            for(Cookie c : cookies) {
                cks = cks+c.getName()+"="+c.getValue()+";";
            }



            System.out.println(" :============ " + cks);

            String pinanUrl = "http://epcis-ptp.pingan.com.cn/epcis.ptp.accidentPage.accidentquerypms.do";

            Map p = new HashMap<>();
            p.put( "accCode","GAMCTP-00002");
            p.put( "cookie",cks.substring(0,cks.length()-1));


            String pResult =  new HttpClient().doPost(pinanUrl,p);

            System.out.print("cc----"+pResult);


        } catch (Exception e) {
            System.out.println("登陆失败");
            //  logger.error(e.getMessage(), e);
            //  logger.error("登陆失败" + "____________________________{账号}" + username);
        }

        //退出

        webClient.close();
    }



    public synchronized String getCookie(String url){
        String cks = "";
        try {
            long time = System.currentTimeMillis();
            final WebClient webclient = new WebClient(BrowserVersion.CHROME);
            webclient.getOptions().setJavaScriptEnabled(true);
            webclient.getOptions().setThrowExceptionOnScriptError(true);
            webclient.getOptions().setCssEnabled(false);
            webclient.getCookieManager().clearCookies();
            webclient.getCache().clear();
            webclient.setRefreshHandler(new ImmediateRefreshHandler());
            webclient.getOptions().setTimeout(600*1000);
            webclient.setJavaScriptTimeout(600*1000);
            webclient.setAjaxController(new NicelyResynchronizingAjaxController());
            webclient.setJavaScriptTimeout(600*1000);
            webclient.getOptions().setRedirectEnabled(true);
            webclient.waitForBackgroundJavaScript(60*1000);
            webclient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webclient.getOptions().setUseInsecureSSL(true);

            HtmlPage page = (HtmlPage)webclient.getPage(url);

            CookieManager CM = webclient.getCookieManager();
            Set<Cookie> cookies = CM.getCookies();//返回的Cookie在这里，下次请求的时候可能可以用上啦。

            for(Cookie c : cookies) {
                cks = cks+c.getName()+"="+c.getValue()+";";
            }
            webclient.close();
            if (!StringUtils.isEmpty(cks)) {
                logger.info("获取cookie耗时："+(System.currentTimeMillis()-time));
            }else {
                logger.info("*******获取cookie失败，耗时："+(System.currentTimeMillis()-time)+"******");
            }
        } catch (Exception e) {
            logger.error("通过htmlunit获取cookie失败......" , e);
        }
        return cks;
    }
}
