package com.example.ldemo;

import com.example.ldemo.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloTests {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private HelloService helloService;

	@Test
	public void getHello() {
		helloService.getHello("zhihao.miao");
	}

	@Test
	public void testMap(){
		Map map = new HashMap<>();

		Map map1 =  new ConcurrentHashMap<>();

		Map map2 = new Hashtable<>();


	}

}
