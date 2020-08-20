package com.example.ldemo.plugin;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MybatisGenerator {
    public static void main(String[] args) throws Exception {
        System.out.print("请谨慎使用，防止覆盖已生成的Bean信息，是否继续？(Y 继续)：");
        Scanner s = new Scanner(System.in);
        String next = s.nextLine();
        if("Y".equals(next.toUpperCase())){
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            InputStream is= MybatisGenerator.class.getClassLoader().getResource("generatorConfig.xml").openStream();
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(is);
            is.close();
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            System.out.println("warnings:"+warnings);
            System.out.println("代码已生成！");
        }else{
            System.out.println("正在退出！");
            System.exit(1);
        }
    }
}
