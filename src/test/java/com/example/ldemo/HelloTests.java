package com.example.ldemo;

import com.example.ldemo.utils.RedisClientTemplate;
import com.example.ldemo.service.HelloService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisCluster;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloTests {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private HelloService helloService;

	@Autowired
	RedisClientTemplate redisClientTemplate;

	@Autowired
	private JedisCluster jedisCluster;

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
List alist = new ArrayList<>();
		List llist = new LinkedList<>();

		Set bs  = new HashSet();
		Map ss = new TreeMap();

		Set sss = new TreeSet<>();

	}




	@Test
	public void lam() {
		List<String> languages = Arrays.asList("Java","Python","scala","Shell","R");
		System.out.println("Language starts with J: ");
		filterTest(languages,x -> x.startsWith("J"));
		System.out.println("\nLanguage ends with a: ");
		filterTest(languages,x -> x.endsWith("a"));
		System.out.println("\nAll languages: ");
		filterTest(languages,x -> true);
		System.out.println("\nNo languages: ");
		filterTest(languages,x -> false);
		System.out.println("\nLanguage length bigger three: ");
		filterTest(languages,x -> x.length() > 4);
	}


	@Test
	public void  listt(){

		List<String> list = new ArrayList<>();
		List<String> listq = new ArrayList<>();
		list.add("12312");
		list.add("12311312312");


		list.forEach(x -> listq.add(x));


		list.forEach(x -> {
			if (x.startsWith("1"))
				listq.add(x);
		});



	}

	public static void filterTest(List<String> languages, Predicate<String> condition) {
		languages.stream().filter(x -> condition.test(x)).forEach(x -> System.out.println(x + " "));
	}


	@Test
	public void redisTest(){

		redisClientTemplate.setToRedis("redis","测试");
		System.out.println("================="+redisClientTemplate.getRedis("redis"));

	}

	@Test
	public void jedisClusterTest(){

		jedisCluster.set("redis","测试1111","","",1);
		System.out.println("======<>>>>>>>>==========="+redisClientTemplate.getRedis("redis"));

	}

}
