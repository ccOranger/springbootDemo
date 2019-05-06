package com.example.ldemo;

import com.example.ldemo.service.HelloService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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

		map.put("1",1);

		Map map1 =  new ConcurrentHashMap<>();
		map1.put("1",1);

		Map map2 = new Hashtable<>();

		Set set = new HashSet<>();

		set.add("");


		CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList(new ArrayList<>());
		List<String> list= Collections.synchronizedList(Lists.newArrayList());



	}

}
