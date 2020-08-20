package ldemo;

import com.alibaba.fastjson.JSONObject;
import com.example.ldemo.utils.HttpClient;
import com.example.ldemo.utils.HttpUtils;
import com.example.ldemo.utils.SM2KeyPair;
import com.example.ldemo.utils.SM2Util;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.util.Cookie;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisCluster;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @package: com.example.ldemo
 * @className: ${TYPE_NAME}
 * @description: 类作用描述
 * @author: 李臣臣
 * @createDate: 2019/10/12 18:15
 * @updateUser: 李臣臣
 * @updateDate: 2019/10/12 18:15
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PinanTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    final  String appId = "9e90f8465fc55bc2015fe24dfb4a0009";
    private String  yzmUrl = "https://pacas-login.pingan.com.cn/cas/genRandCode";
    private  String loginUrl = "https://pacas-login.pingan.com.cn/cas/PA003/EPCIS_PTP/auth.do";
    private  String accidentquerypmsUrl = "http://epcis-ptp.pingan.com.cn/epcis.ptp.accidentPage.accidentquerypms.do";
    private  String districtUrl = "http://epcis-ptp.pingan.com.cn/accidentPage/static/district.json";


    @Autowired
    private JedisCluster jedisCluster;


    @Test
    public void getApi(){

        if (jedisCluster.exists("cookie")){

            Map p = new HashMap<>();
            p.put( "accCode","GAMCTP-00002");
            p.put( "cookie",jedisCluster.get("cookie"));


            String pResult =  HttpClient.doPost(accidentquerypmsUrl,p);
            String district =  HttpClient.doPost(districtUrl,p);

            System.out.println("cc----"+district);

        }else {
            login();
          //  saveCookie("http://epcis-ptp.pingan.com.cn/CAS_SUCCESS_LOGIN?CAS_SSO_COOKIE=9e90f8465fc55bc2015fe24dfb4a0009-b7e8e1a9a8024cfa9694b81709af8f10&PASESSION=i4APC80rK2lggttfGWq7LwvRnIF8yMLuAHkNKnMugZGjbMgoKJuaB-TB7AoIjsndP189yTfTFpoX2YqQHHIsqlnX9MnR7LfLZEqPp0ofjknWsyTFUIevHt5S8f5Wc0lNWngPlngf3UuS6QzXRwH31Q%3D%3D%7CA00xOC0zMxMx%3DCMTNAowjNo%3DNxSA");

        }



    }

    public void  saveCookie(String cookieUrl){

       // String loginUrl = "http://epcis-ptp.pingan.com.cn/CAS_SUCCESS_LOGIN?CAS_SSO_COOKIE=9e90f8465fc55bc2015fe24dfb4a0009-4c2aa443d7f94455af20161de24787bb&PASESSION=i4APC80rK2lggttfGWq7L-tgde35ac6kK5gVSpQjRf4-0BsBdm7R3S2tOS-IADL%21P189yTfTFpoX2YqQHHIsqlnX9MnR7LfLZEqPp0ofjkktqKveKOYptzdjb3jmwZsIlVr2ZL1r5tXjsREsDL-UeQ%3D%3D%7CA0yxOC0zMxMx%3DiMDNAozjMo%3DMxSg";

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
            HtmlPage page = (HtmlPage) webClient.getPage(cookieUrl);

            CookieManager CM = webClient.getCookieManager();
            Set<Cookie> cookies = CM.getCookies();//返回的Cookie在这里，下次请求的时候可能可以用上啦。

            String cks = "";
            for(Cookie c : cookies) {
                cks = cks+c.getName()+"="+c.getValue()+";";
            }

            jedisCluster.set("cookie",cks.substring(0,cks.length()-1).toString());


        } catch (Exception e) {
            System.out.println("登陆失败");
            //  logger.error(e.getMessage(), e);
            //  logger.error("登陆失败" + "____________________________{账号}" + username);
        }

        //退出

        webClient.close();
    }

    @Test
    public void pw(){
        logger.debug(""+System.currentTimeMillis());

        String timestamp = System.currentTimeMillis()+"" ;
        String password = "jingan1906";
        String spk="d9a4xc4p";

        String oriPwd= password+spk+timestamp;
        String sm2PubX = "CE334A29C0FDF7810D4BBB1C9917E54719E53394F947AC8B525CCDEFDDA44810";
        String sm2PubY = "649E2A9651401CC3251BCEDAD42CE1506841A7A31D3EE3DEADDE65D769BA4458";


            SM2Util sm2 = new SM2Util();
            SM2KeyPair keyPair = sm2.generateKeyPair();
            byte[] data = sm2.encrypt(oriPwd,keyPair.getPublicKey());
            System.out.println("data is:"+ Arrays.toString(data));
            sm2.decrypt(data, keyPair.getPrivateKey());//71017045908707391874054405929626258767106914144911649587813342322113806533034

     /*   var msgData = CryptoJS.enc.Utf8.parse(msg);
        var cipherMode = SM2CipherMode.C1C3C2;
        var cipher = new SM2Cipher(cipherMode);
        var userKey = cipher.CreatePoint(keyX, keyY);
        msgData = cipher.GetWords(msgData.toString());
        var encryptData = cipher.Encrypt(userKey, msgData);
        return encryptData.toUpperCase();*/


       // doEncryptSM2(sm2PubX,sm2PubY,oriPwd);

    }

    public String doEncryptSM2(String keyX, String keyY, String msg) {

        return "";
    }


    @Test
    public void login(){

        String randCodeId = "";
        String code = "";
        String username = "GAMCTP-00002";
        String password = "0A238C83A78BF1538F6F5F049DF06A1DFF4864EAACC9F4EB7633ABB86EF14C996AA2338B14F5C038D960E6A00DCD5020C06A410D28640EDFD6382BA7991BEA2204820DA1C6070083C09FA7CF203A07598FAA317253041C2FAF7F56C9BE960B1510A98AA0F07789EEEB1D4B5676300ED7FF7D1E4250F8C1624BFFC19594C9D4";
        String ori = "";
        String timestamp = "1571043150581";
        String clientCallBack = "";
        String challenge = "";
        String seccode = "";
        String validate = "";


/*        {
            "appId": "9e90f8465fc55bc2015fe24dfb4a0009",
                "username": "GAMCTP-00002",
                "password": "CFE84230A8897FEB31F686A2C1F0DDA25E48B0957C4D19246C9888E7548D35FF192A2F5111F8C3F6719DEA173D3B4859F71E629744C4AF6F5F2A7724F7E94CC75790645454FB8FC5D2983C6040E496F022261A34939BB72F2D33C2CB1D113B11E3265FAB6DBBECE8ECDE53284494B9C55C072D02361D35E86A9A9474F2CD1D",
                "randCodeId": "02e52206a73e833ccea474a9dae3bebd1322b6a43-IC-9e90f8465fc55bc2015fe24dfb4a0009-116.230.135.220",
                "code": "1111",
                "ori": "",
                "timestamp": "1570858337421",
                "clientCallBack":"" ,
                "challenge":"" ,
                "seccode":"" ,
                "validate": ""
        }
        */


   JSONObject jsonObject = yzm();

//{"code":"SUCCESS","content":{"data":"data:image/jpg;base64,R0lGODlhRgAoAPcAAB8JUxNOMwVsE1gCR05vJWZVZxco1Q9b71AbiHBjgxzjSmunOWfuFn3gDm3t\r\nJ2//bQWJnSPumleOjV+jzWf7+JVHMKF8ePEnMIgHuKYvv6UzvKQ5uqY6vaQ9uKsfyK0by64Yzawd\r\nyrAbzrEd0LgU9qYuwKkmw6klxK0kyaorw68ozKswxK80yKw7w7EhzrElzrEqzbEh0LIl0LQn0LQp\r\n0bUu0rQu1Lkk17wu2bAzy7U8zLYw0rgx0rk107g21Lo51KNHtaJJtKJPs6lLvKNTs6ZVuKJdsaRa\r\ns6ZftapVvKhet6pYu6NhsKRpsKZysKZ2sad5sapztq1+tq54ubJ7vZJ7+KtGwLRHyrVNy7hNzrdE\r\n0LhC0bpF0r1E179G2L1M1b1J2rBSw7VRyrdeyrhQzrtezr1c0r1T4bpryr1o07l0yMBL2MRU2sFa\r\n1sVb281W5cJh18Rj18Zg28Vr1sZo2cxt38B9zsBy0cV81cpz3Ml12sxz3st36c564tF64oe+E73h\r\nM82RFeSTGufYAc3eTsbBdM/za6mAsquFs6yHtK2JtK6Mta+JuLGLurCRtrKWuLSUu7OYubmdv6u2\r\nqrehvLmlvbuovoOX97aIwLyExr2DybyNxr2Sxb2Uxr6nxr6twb+ww4XP17TG3vqhgcCAzMGJzceB\r\n1siD2siO1cCQycadzsiR08yS2NGO39Cb3MeL5NGC4dKU4tWZ4diT5sWkzMCoxcStyc2t1MGzxMW0\r\nycW5yMm0zcm6y82z0tGg29Kg3dOn3tai39Wl39Ks2tKz2dK519es4Nmj5Niv4Nqm8tyx5d+w6eCt\r\n6+G6+drPp8f+scnDysjIyM7G0M/I0dLA1dTD2NXE2dLJ1NXK19TO1dbK2NfM2dnN3NbR19fS2NnV\r\n2t3Z3t7G493M4t7J6N7S4N/a4ODN5ODL8OLT5OXR6eLc5Obd6OjV7erZ7urX9u/e9uTj5Ofh6ejj\r\n6evg7ejm6evm7O3t7e/s8PDr8fDt8PTt9vPy8/j3+Pr5+vz6/P7+/iwAAAAARgAoAEcI/wD/CRxI\r\nsKDBgwgTKlyoMN+BA4UKGfkXLxYXESJ4rLrHSwOIDyWSpPGCkYMugfGA3RGjYweNGj7CQICgsEIF\r\nhgVxgQARgsm/fbR+iDBj7x80EyA2gOpHEJyBhwdEpLBCxo2bNnT0vBmxk4Uqgv3m9bKFs6xCAwgH\r\nDIgWDR7baEUPwnOEJEwWM1APmMWJtmCCBAcZMDAYKBBOZ84E7TXKREiTR9729rNHjx4hQmV6yMAo\r\nYsSPPe4Wl7XncSeiXcbsaMEICyrPa9bQvHCxyoIFA0wF9nN3RgQIHmy87HCBUQaNFn+SJ790aa8h\r\nQyBc5Ko0sNeVBw9uQYKGZRHBvjgDQP/FuGbZvty5B4YKhZMAAb4Jnz0rOGjQqFE4BQhYuGDBXvB7\r\nNdDAPwggINqBCCK0Dz3qwGNPegkeFNE/Ekiw04U7idACJ+QMVM400FiCiBEpzFADDXnQo0Y4+wi0\r\nTzMy7ESDLBAKdMEFCdrDxE4KKABVRCCI4Ig9TezkBDz/jAMGCF9w400QINiwBhg++OCFKea0qA45\r\n+9xDT43/iCLKYuBgeIIHO8lwTD0/GYEhVLkZsI8//1RRRRk/XDiCbxl2EYcedWCxAnEZFKScQv04\r\nshMKhwikDhoj0FBKPpGAgAEGBRnASyrXMJVbXv/0sw8zQkUQwToRDtQPNJ/ggks3cO3/E40tb6lB\r\nBCNw/WNAW7nKGleoA9nDa1HqcBINVKrU0YYusUaTakIAPrsQVM9GK+21BAEAwHfYdmvQBBMoBAgg\r\nZVFAAbYOOODtugjak4039LSI4GXy0EKFEkQcEUUt6rArEC4pYKjBE9Dsc15C++iCCR7C1FMEDGa8\r\n4kkwctCAURftHHQjghEVcuEJG4RAHAg+9JHOP/Q80UEKIWAIAgpR2BMXPuQQwwefKMxU00172QMF\r\nCD8Wko8recYwBT2OtKxBItiI00d0U9iDywYXxgCGH62g4ks69Cwk5mL2QAlCj/30o05e0IADxE6f\r\n8KNOGiPAMEw/lRhwzjvh7KLJFiPk/0AKMcrYMQcbYygHzoM5toyhiSLE4Ic+/ygC9ENfiOCDMP3Y\r\nBl4+4iQT404j7An6npzx4MYsyiVXljQXanBIE0O4QMMvAoHTAU8dHCHFNv1ANQwzp3BgJ0ag43DD\r\nhSrgwU4961DTyRxcLHEgJTuF4IRA1WABgg7F/GOJpRj8KpABPPwwxCJQUcONOOKsjwxJIJgK5oHP\r\n7ZSCdz9tAoMIcKDTCAwgaNRA0HIP8RkEKvbQxP50oAp+PKsfTthJGKARl3JAAzusgAYaoEGd8eVK\r\nNwmxRwACMIlJQGUGmYgGOdChDXikZz2iEVau7CGNb+TjGw5iiwGKIsNh/aRXM3xLNGgkMSgYQIVX\r\n0ZDXuqw1ELXsZR/dyEUv0KFEanWLiQipz7UeghD3wAch8jnIffKzH4X050DR+ktgBlOQwhzGGRHS\r\nVqb8dRBBKEZaWKRjgsDFrQMJiAIF0mNBSECChIwLIQUoAB3TJZCAAAA7\r\n","key":"11d37122206e2f105ba9c410d83774e1696754805-IC-9e90f8465fc55bc2015fe24dfb4a0009-116.230.135.220"}}

    if ("SUCCESS".equals(jsonObject.get("code"))){

        randCodeId =   jsonObject.getJSONObject("content").get("key").toString();
        code = discernYzm1(jsonObject.getJSONObject("content").get("data").toString());

        //{"VERIFY_CODE_STATUS": "艾科瑞特，让企业业绩长青", "VERIFY_CODE_ENTITY": {"VERIFY_CODE": "NM5X"}}

    }else {
        System.out.println("获取验证码失败=========="+jsonObject);
    }

        Map p = new HashMap<>();
        p.put( "appId", appId);
        p.put( "randCodeId", randCodeId);
        p.put( "code", code);
        p.put( "username", username);
        p.put( "password", password);
        p.put( "ori", ori);
        p.put( "timestamp", timestamp);
        p.put( "clientCallBack", clientCallBack);
        p.put( "challenge", challenge);
        p.put( "seccode", seccode);
        p.put( "validate", validate);

       /* String url = loginUrl+"?appId="+appId+"&randCodeId="
                +randCodeId+"&code="+code+"&username="
                +username+"&password="+password+"&ori="+ori+"&timestamp="
                +timestamp+"&clientCallBack="+clientCallBack+"&challenge="
                +challenge+"&seccode="+seccode+"&validate="+validate;*/

        String pResult =  HttpClient.doPostForData(loginUrl,p);
        JSONObject loginJson = JSONObject.parseObject(pResult);
        System.out.println("loginJson=========="+loginJson);


        if (loginJson.get("content") != null){

            saveCookie(loginJson.get("content").toString());
         }else {
            System.out.println("loginJson=========="+loginJson.get("message"));
        }



    }

    //获取验证码
    public JSONObject yzm(){

        Map p = new HashMap<>();
        p.put( "appId", appId);
        String pResult =  HttpClient.doPostForData(yzmUrl,p);

        JSONObject jsonObject = JSONObject.parseObject(pResult);

        System.out.println("json=========="+jsonObject);

        return jsonObject;


    }


    //识别验证码
    public JSONObject discernYzm(String imageData){

          //  String host = "http://apigateway.jianjiaoshuju.com";
            String host = "http://apigateway.jianjiaoshuju.com/api/v_1/serviceQ.html";
          //  String path = "/api/v_1/yzm.html";
            String path = "/api/v_1/serviceQ.html";
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
            bodys.put("v_pic", imageData);
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
                HttpResponse response = HttpClient.doPost(host, path, method, headers, querys, bodys);
                //获取response的body
            //    System.out.println(EntityUtils.toString(response.getEntity()));

                String s = EntityUtils.toString(response.getEntity());

                //{"msg":"查询成功!","v_code":"nm5x","errCode":0,"v_type":"ne4"}

                //      logger.debug(JSONObject.parseObject(EntityUtils.toString(response.getEntity())).get("v_code").toString()+"<<<<<");

                return  JSONObject.parseObject(s);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    //识别验证码
    public String discernYzm1(String imageData){

        String host = "http://codevirify.market.alicloudapi.com";
        String path = "/icredit_ai_image/verify_code/v1";
        String method = "POST";
        String appcode = "cf19b80fd52b456da31d13045cb1ff85";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("IMAGE", imageData.replaceAll("data:image/jpg;base64,","") );
        bodys.put("IMAGE_TYPE", "0");


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
            System.out.println(response.toString());

            String s = EntityUtils.toString(response.getEntity());

            //{"VERIFY_CODE_STATUS": "艾科瑞特，让企业业绩长青", "VERIFY_CODE_ENTITY": {"VERIFY_CODE": "NM5X"}}

            System.out.println(s);

            return  JSONObject.parseObject(s).getJSONObject("VERIFY_CODE_ENTITY").get("VERIFY_CODE").toString();

            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }

            return "";
        }

    //识别验证码
    public JSONObject discernYzm2(String imageData){

        String host = "https://imgurlocr.market.alicloudapi.com";
        String path = "/urlimages";
        String method = "POST";
        String appcode = "cf19b80fd52b456da31d13045cb1ff85";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("image", imageData);


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
            System.out.println(response.toString());

            String s = EntityUtils.toString(response.getEntity());

            //{"VERIFY_CODE_STATUS": "艾科瑞特，让企业业绩长青", "VERIFY_CODE_ENTITY": {"VERIFY_CODE": "NM5X"}}

            System.out.println(s);

            return  JSONObject.parseObject(s);

            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }

            return null;
        }




    @Test
    public void  loginWb(){

      //  JSONObject jsonObject = yzm();

//{"code":"SUCCESS","content":{"data":"data:image/jpg;base64,R0lGODlhRgAoAPcAAB8JUxNOMwVsE1gCR05vJWZVZxco1Q9b71AbiHBjgxzjSmunOWfuFn3gDm3t\r\nJ2//bQWJnSPumleOjV+jzWf7+JVHMKF8ePEnMIgHuKYvv6UzvKQ5uqY6vaQ9uKsfyK0by64Yzawd\r\nyrAbzrEd0LgU9qYuwKkmw6klxK0kyaorw68ozKswxK80yKw7w7EhzrElzrEqzbEh0LIl0LQn0LQp\r\n0bUu0rQu1Lkk17wu2bAzy7U8zLYw0rgx0rk107g21Lo51KNHtaJJtKJPs6lLvKNTs6ZVuKJdsaRa\r\ns6ZftapVvKhet6pYu6NhsKRpsKZysKZ2sad5sapztq1+tq54ubJ7vZJ7+KtGwLRHyrVNy7hNzrdE\r\n0LhC0bpF0r1E179G2L1M1b1J2rBSw7VRyrdeyrhQzrtezr1c0r1T4bpryr1o07l0yMBL2MRU2sFa\r\n1sVb281W5cJh18Rj18Zg28Vr1sZo2cxt38B9zsBy0cV81cpz3Ml12sxz3st36c564tF64oe+E73h\r\nM82RFeSTGufYAc3eTsbBdM/za6mAsquFs6yHtK2JtK6Mta+JuLGLurCRtrKWuLSUu7OYubmdv6u2\r\nqrehvLmlvbuovoOX97aIwLyExr2DybyNxr2Sxb2Uxr6nxr6twb+ww4XP17TG3vqhgcCAzMGJzceB\r\n1siD2siO1cCQycadzsiR08yS2NGO39Cb3MeL5NGC4dKU4tWZ4diT5sWkzMCoxcStyc2t1MGzxMW0\r\nycW5yMm0zcm6y82z0tGg29Kg3dOn3tai39Wl39Ks2tKz2dK519es4Nmj5Niv4Nqm8tyx5d+w6eCt\r\n6+G6+drPp8f+scnDysjIyM7G0M/I0dLA1dTD2NXE2dLJ1NXK19TO1dbK2NfM2dnN3NbR19fS2NnV\r\n2t3Z3t7G493M4t7J6N7S4N/a4ODN5ODL8OLT5OXR6eLc5Obd6OjV7erZ7urX9u/e9uTj5Ofh6ejj\r\n6evg7ejm6evm7O3t7e/s8PDr8fDt8PTt9vPy8/j3+Pr5+vz6/P7+/iwAAAAARgAoAEcI/wD/CRxI\r\nsKDBgwgTKlyoMN+BA4UKGfkXLxYXESJ4rLrHSwOIDyWSpPGCkYMugfGA3RGjYweNGj7CQICgsEIF\r\nhgVxgQARgsm/fbR+iDBj7x80EyA2gOpHEJyBhwdEpLBCxo2bNnT0vBmxk4Uqgv3m9bKFs6xCAwgH\r\nDIgWDR7baEUPwnOEJEwWM1APmMWJtmCCBAcZMDAYKBBOZ84E7TXKREiTR9729rNHjx4hQmV6yMAo\r\nYsSPPe4Wl7XncSeiXcbsaMEICyrPa9bQvHCxyoIFA0wF9nN3RgQIHmy87HCBUQaNFn+SJ790aa8h\r\nQyBc5Ko0sNeVBw9uQYKGZRHBvjgDQP/FuGbZvty5B4YKhZMAAb4Jnz0rOGjQqFE4BQhYuGDBXvB7\r\nNdDAPwggINqBCCK0Dz3qwGNPegkeFNE/Ekiw04U7idACJ+QMVM400FiCiBEpzFADDXnQo0Y4+wi0\r\nTzMy7ESDLBAKdMEFCdrDxE4KKABVRCCI4Ig9TezkBDz/jAMGCF9w400QINiwBhg++OCFKea0qA45\r\n+9xDT43/iCLKYuBgeIIHO8lwTD0/GYEhVLkZsI8//1RRRRk/XDiCbxl2EYcedWCxAnEZFKScQv04\r\nshMKhwikDhoj0FBKPpGAgAEGBRnASyrXMJVbXv/0sw8zQkUQwToRDtQPNJ/ggks3cO3/E40tb6lB\r\nBCNw/WNAW7nKGleoA9nDa1HqcBINVKrU0YYusUaTakIAPrsQVM9GK+21BAEAwHfYdmvQBBMoBAgg\r\nZVFAAbYOOODtugjak4039LSI4GXy0EKFEkQcEUUt6rArEC4pYKjBE9Dsc15C++iCCR7C1FMEDGa8\r\n4kkwctCAURftHHQjghEVcuEJG4RAHAg+9JHOP/Q80UEKIWAIAgpR2BMXPuQQwwefKMxU00172QMF\r\nCD8Wko8recYwBT2OtKxBItiI00d0U9iDywYXxgCGH62g4ks69Cwk5mL2QAlCj/30o05e0IADxE6f\r\n8KNOGiPAMEw/lRhwzjvh7KLJFiPk/0AKMcrYMQcbYygHzoM5toyhiSLE4Ic+/ygC9ENfiOCDMP3Y\r\nBl4+4iQT404j7An6npzx4MYsyiVXljQXanBIE0O4QMMvAoHTAU8dHCHFNv1ANQwzp3BgJ0ag43DD\r\nhSrgwU4961DTyRxcLHEgJTuF4IRA1WABgg7F/GOJpRj8KpABPPwwxCJQUcONOOKsjwxJIJgK5oHP\r\n7ZSCdz9tAoMIcKDTCAwgaNRA0HIP8RkEKvbQxP50oAp+PKsfTthJGKARl3JAAzusgAYaoEGd8eVK\r\nNwmxRwACMIlJQGUGmYgGOdChDXikZz2iEVau7CGNb+TjGw5iiwGKIsNh/aRXM3xLNGgkMSgYQIVX\r\n0ZDXuqw1ELXsZR/dyEUv0KFEanWLiQipz7UeghD3wAch8jnIffKzH4X050DR+ktgBlOQwhzGGRHS\r\nVqb8dRBBKEZaWKRjgsDFrQMJiAIF0mNBSECChIwLIQUoAB3TJZCAAAA7\r\n","key":"11d37122206e2f105ba9c410d83774e1696754805-IC-9e90f8465fc55bc2015fe24dfb4a0009-116.230.135.220"}}

 /*       if ("SUCCESS".equals(jsonObject.get("code"))){

            randCodeId =   jsonObject.getJSONObject("content").get("key").toString();
            code = discernYzm1(jsonObject.getJSONObject("content").get("data").toString());

            //{"VERIFY_CODE_STATUS": "艾科瑞特，让企业业绩长青", "VERIFY_CODE_ENTITY": {"VERIFY_CODE": "NM5X"}}

        }else {
            System.out.println("获取验证码失败=========="+jsonObject);
        }
*/

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


             //   getImgStr(page.getHtmlElementById("randCode"));

                HtmlForm form = page.getForms().get(0);
                //用户名text框
                HtmlTextInput txtUName = (HtmlTextInput) form.getInputByName("username");
                txtUName.setValueAttribute(username);
                //密码框
                HtmlPasswordInput txtPwd = (HtmlPasswordInput) form.getInputByName("password");
                txtPwd.setValueAttribute(password);

                Thread.sleep(1000);
                logger.debug("s====="+page.getHtmlElementById("randCode").getAttributeNode("src").getValue());

                yzm = discernYzm1(page.getHtmlElementById("randCode").getAttributeNode("src").getValue());

                //验证码
                HtmlTextInput code = (HtmlTextInput) form.getInputByName("code");
                code.setValueAttribute(yzm);
                //获得提交按钮
                HtmlInput submit = form.getInputByValue("立即登录");
                //登录进入
                page = (HtmlPage) submit.click();

                Thread.sleep(2000);

                String loadPage = page.asText();

                if (!loadPage.contains("投保处理")) {
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

    /**
     * 得到网页中图片的地址
     */
    public static Set<String> getImgStr(String htmlStr) {
        Set<String> pics = new HashSet<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

}




