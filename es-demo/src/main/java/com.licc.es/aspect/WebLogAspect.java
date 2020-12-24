package com.licc.es.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @package: com.example.ldemo
 * @className: ${TYPE_NAME}
 * @description: 类作用描述
 * @author: 李臣臣
 * @createDate: 2019/4/15 16:55
 * @updateUser: 李臣臣
 * @updateDate: 2019/4/15 16:55
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */

@Aspect
@Component
public class WebLogAspect {
    Logger logger = LoggerFactory.getLogger(WebLogAspect.class);


    /***
     *使用@Aspect注解将一个java类定义为切面类
     *使用@Pointcut定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等。
     *根据需要在切入点不同位置的切入内容
     *使用@Before在切入点开始处切入内容
     *使用@After在切入点结尾处切入内容
     *使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
     *使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容
     *使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑
     */


    /***
     * 同步问题，所以我们可以引入ThreadLocal对象
     * Thread 为每一个线程都维护了ThreadLocalMap，这个map。
     * ThreadLocalMap是在ThreadLocal中使用内部类编写的，但引用对象在Thread中
     * ThreadLocalMap的key是ThreadLocal对象，value是要存储的对象
     *
     * ThreadLocal设计目的是为了能够在当前线程中有属于自己的变量，并不是为了解决并发或者共享变量的问题
     *
     *
     *
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();


    @Pointcut("execution(public * com.licc.es.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        logger.info("开始....");
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null){
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);

        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()) +" ms");


        //防止内存泄露，对象使用完，删除
        startTime.remove();

    }

}