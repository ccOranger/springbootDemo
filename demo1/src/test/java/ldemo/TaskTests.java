package ldemo;

import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import com.example.ldemo.utils.AsyncTask;
import com.example.ldemo.utils.Baidu;
import com.example.ldemo.utils.HttpClient;
import com.example.ldemo.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * @package: com.example.ldemo
 * @className: ${TYPE_NAME}
 * @description: 类作用描述
 * @author: 李臣臣
 * @createDate: 2019/4/16 18:00
 * @updateUser: 李臣臣
 * @updateDate: 2019/4/16 18:00
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskTests {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AsyncTask asyncTask;

    @Test
    public void AsyncTaskTest1() throws Exception {


           // logger.debug(i+"aaaaaaaaaaaaaaaaaaaaaaaaaaaa");

            asyncTask.doTask2(1);
        // asyncTask.doTask2(1);
        try {
            logger.info("begin to deal other Task!");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        logger.info("All tasks finished.");
    }

    /**
    * @Description 测试类用isCancelled判断异步任务是否取消，isDone判断任务是否执行结束
    * @Author  李臣臣
    * @Date   2019/12/25 0025 13:35
    * @Param
    * @Return
    * @Exception
    *
    */
    @Test
    public void testDealHaveReturnTask() throws Exception {

        Future<String> future = asyncTask.dealHaveReturnTask();
        logger.info("begin to deal other Task!");
        while (true) {
            if(future.isCancelled()){
                logger.info("deal async task is Cancelled");
                break;
            }
            if (future.isDone() ) {
                logger.info("deal async task is Done");
                logger.info("return result is " + future.get());
                break;
            }
            logger.info("wait async task to end ...");
            Thread.sleep(1000);
        }
    }

    @Test
    public  void  testMap(){
        Map map = new ConcurrentHashMap<>();

    }


    @Test
    public void  baidu(){

        String token =  new Baidu().getAuth("dtckzGYLtDPlbXm2Mq5AsXTs","ABGHTrpAopULO79GuLft4QKoOrbdUe3i");
        logger.debug(token+"<<<<<<<<<<<<<<<<<<<<<");

        //24.7b7b817d75314d7ebb34f6bc0577662a.2592000.1572328149.282335-17377209
    }

    @Test
    public void  general_basic(){


        String pinanUrl = "https://pacas-login.pingan.com.cn/cas/genRandCode?param=1569575040634&appId=9e90f8465fc55bc2015fe24dfb4a0009";

        Map p = new HashMap<>();
        p.put( "appId", "9e90f8465fc55bc2015fe24dfb4a0009");


        String pResult =  new HttpClient().doPost(pinanUrl,p);

        logger.debug(pResult+"<<<<<<<<<<<<<<<<<<<<<");


        JSONObject jsonObject = JSONObject.parseObject(pResult);

        String image = jsonObject.getJSONObject("content").getString("key");


        try {
           // image= URLDecoder.decode(new BASE64Encoder().encode(image.getBytes("UTF-8")), "UTF-8");
            image= new BASE64Encoder().encode("http://www.yezhuwuyou.top/a.jpg".getBytes("UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
        String token = "24.7b7b817d75314d7ebb34f6bc0577662a.2592000.1572328149.282335-17377209";

        Map map = new HashMap<>();
      //  map.put("image",image);
        map.put("url",image);
        map.put("language_type","");
        map.put("detect_direction","");
        map.put("detect_language","");
        map.put("probability","");


       String result =  new HttpClient().doPost(url+"?access_token="+token,map);

        logger.debug(result+"<<<<<<<<<<<<<<<<<<<<<");

    }
    @Test
    public void  general_basic1(){


        String pinanUrl = "https://pacas-login.pingan.com.cn/cas/genRandCode?param=1569575040634&appId=9e90f8465fc55bc2015fe24dfb4a0009";

        Map p = new HashMap<>();
        p.put( "appId", "9e90f8465fc55bc2015fe24dfb4a0009");


        String pResult =  new HttpClient().doPost(pinanUrl,p);

        logger.debug(pResult+"<<<<<<<<<<<<<<<<<<<<<");


        JSONObject jsonObject = JSONObject.parseObject(pResult);

        String image = jsonObject.getJSONObject("content").getString("data");


        // 初始化一个AipOcr
        AipOcr client = new AipOcr("17377209", "dtckzGYLtDPlbXm2Mq5AsXTs", "ABGHTrpAopULO79GuLft4QKoOrbdUe3i");



        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");


        String imgBase64 = image.replaceAll("data:image/jpg;base64,","");
        BASE64Decoder d = new BASE64Decoder();
        try {
            byte[] data = d.decodeBuffer(imgBase64);

            FileOutputStream os = new FileOutputStream("D:\\tupian\\d2.jpg");
            os.write(data);
            os.close();


            // 参数为二进制数组
            org.json.JSONObject res1 = client.basicGeneral(data, options);
            org.json.JSONObject a = client.basicGeneral("D:\\tupian\\a.jpg", options);
            org.json.JSONObject res = client.basicGeneral("D:\\tupian\\d1.png", options);
            System.out.println(res1);
            System.out.println(a);
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }





     //  String result =  new HttpClient().doPost(url+"?access_token="+token,map);

    //    logger.debug(result+"<<<<<<<<<<<<<<<<<<<<<");

    }


    @Test
    public void t() {
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
        bodys.put("v_pic", "data:image/jpeg;base64,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");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
