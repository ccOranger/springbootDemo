package com.example.ldemo2.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @package: com.example.ldemo.controller
 * @className: ${TYPE_NAME}
 * @description: 类作用描述
 * @author: 李臣臣
 * @createDate: 2019/4/11 15:39
 * @updateUser: 李臣臣
 * @updateDate: 2019/4/11 15:39
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@RestController
@RequestMapping(value = "/hello")
@Api(value="hello",description = "测试专用")
public class HelloController {
    @Value("${hello.name}")
    private String name;


    @ApiOperation(value = "测试专用", notes = "测试专用")
    @GetMapping(value = "/get")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "名字",paramType = "string")
    })
    public String getHello(String name){
        return  "hello ";
    }
    @ApiOperation(value = "aop测试", notes = "aop测试")
    @GetMapping(value = "/getAop")
    public String getAop(){
        return  "aop测试";
    }





    @ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    public void upload(@RequestParam("files") MultipartFile[] files) throws Exception {
        InputStream is = null;
        OutputStream os = null;
        try {
            if(files != null && files.length > 0) {
                //循环获取file数组中得文件
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    //保存文件
                    if (!file.isEmpty()) {
                        is = file.getInputStream();
                        String path = "E:/workspace/Project/Platform/Web-iot-FE/bill/file/" + Long.toString(System.currentTimeMillis() / 1000)+file.getOriginalFilename();
                        os = new FileOutputStream(path);
                        int bytesRead = 0;
                        byte[] buffer = new byte[8192];
                        while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }else{
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(os != null){
                os.close();
            }
            if(is != null){
                is.close();
            }
        }
    }
}
