package com.example.ldemo.service;



/**
 *  @author: 李臣臣
 *  @Date: 2020/04/30 0030 14:34
 *  @Description: 单例模式
 */
public class Singleton {


    /**
     * 1、volatile如何保证内存可见性
     * 2、volatile如何禁止指令重排序
     * 3、内存屏障
     * 4、内存可见性
     * 5、关于volatile的单例模式
     */
    private static volatile Singleton singleton;

    private Singleton(){

    }

    public static Singleton getSingleton() {

        if (singleton != null){
            return singleton;
        }
        synchronized (Singleton.class){
            if (singleton == null){
                singleton = new Singleton();
            }
            return singleton;
        }

    }
}
