package com.example.ldemo;

import com.example.ldemo.entity.redisMessage.GoodsMessage;
import com.example.ldemo.entity.redisMessage.UserMessage;
import com.example.ldemo.service.HelloService;
import com.example.ldemo.utils.RedisOperationHepler;
import com.example.ldemo.utils.redis.Publisher;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisCluster;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Predicate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloTests {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private HelloService helloService;

	@Autowired
	private JedisCluster jedisCluster;

	@Autowired
	private Publisher publisher;

	@Autowired
	private RedisOperationHepler redisOperationHepler;

	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void getHello() {
		helloService.getHello("zhihao.miao");
	}

	@Test
	public void testMap(){
		Map map = new HashMap<>();

		map.put("1",1);
		map.put(null,111);
		map.put("2222",null);

		Map map1 =  new ConcurrentHashMap<>();
		map1.put("1",1);
		map1.get("1");

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
	public void jedisClusterTest(){

		jedisCluster.set("redis","测试1111");
		System.out.println("====================="+jedisCluster.get("redis"));


		redisOperationHepler.set("redisOperationHepler","redisOperationHepler");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"+redisOperationHepler.get("redisOperationHepler"));


	}

	@Test
	public void pushMessage() {
		UserMessage userMessage = new UserMessage();
		userMessage.setMsgId(UUID.randomUUID().toString().replace("-",""));
		userMessage.setUserId("1");
		userMessage.setUsername("admin");
		userMessage.setUsername("root");
		userMessage.setCreateStamp(System.currentTimeMillis());
		publisher.pushMessage("user",userMessage);
		GoodsMessage goodsMessage = new GoodsMessage();
		goodsMessage.setMsgId(UUID.randomUUID().toString().replace("-",""));
		goodsMessage.setGoodsType("苹果");
		goodsMessage.setNumber("十箱");
		goodsMessage.setCreateStamp(System.currentTimeMillis());
		publisher.pushMessage("goods",goodsMessage);
	}
	@Test
	public void pushmapMessage() {

		Map map = new HashMap<>();
		map.put("name","张三");
		map.put("age","12");
		publisher.pushMessage("map",map);
	}


	@Test
	public void  tt(){
		ExecutorService newExecutorService = Executors.newFixedThreadPool(3);
		ExecutorService newExecutorService1 = Executors.newScheduledThreadPool(1);
		ExecutorService newExecutorService2 = Executors.newCachedThreadPool();
		ExecutorService newExecutorService3 = Executors.newSingleThreadExecutor();
	}



	@Test
	public void redisTest(){

		for (int i=0;i<10;i++){
			try {
				Thread.sleep(2000);
				if (tryLock("111","2222")){
					System.out.println("TTTTTTTTTTTTTTTTTTTTT");
				}else {
					System.out.println("fffffffffffffffffffffffffffff");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}



	//分布式锁
	public boolean tryLock(String key,String value){

		return redisTemplate.opsForValue().setIfAbsent(key,value,10, TimeUnit.SECONDS);
}


	//分布式锁2.0
	public String deductStock() {
		// 给每一把锁分配不同的值
		String clientId = UUID.randomUUID().toString();
		try {
			// 获得锁
			Boolean result = redisTemplate.opsForValue().setIfAbsent("lockKey", clientId, 10, TimeUnit.SECONDS);
			// 业务代码
			if (!result) {
				return "error";
			}
			int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock").toString());
			if (stock > 0) {
				int realStock = stock - 1;
				redisTemplate.opsForValue().set("stock", realStock + "");
				System.out.println("扣减成功，剩余库存：" + redisTemplate.opsForValue().get("stock"));
			} else {
				System.out.println("扣减失败，库存不足");
			}
		} finally {
			//因为我们设置锁的时间是 10 s，如果业务执行之间大于 10 s，锁就会失效，第二个线程会获得锁，这是为了防止第一个线程执行结束的时候误删第二个线程的锁，就需要在删除的时候进行判断，我们给每一把锁设置一个 UUID 的值，这样可以做到在删除的时候判断是否是自己的锁。
			// 释放锁
			if (clientId.equals(redisTemplate.opsForValue().get("lockKey"))) {
				redisTemplate.delete("lockKey");
			}
		}
		return "success";
	}




	/* 图片转base64字符串
	 * @param imgFile 图片路径
	 * @return base64字符串格式的图片
	 */
	@Test
	public void  imageToBase64Str() {

		String imgFile = "C:\\Users\\Administrator\\Desktop\\ea7feecf07585dcee2feafe137a4e14.png";
		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = new FileInputStream(imgFile);
			data = new byte[inputStream.available()];  //根据文件流字节大小初始化data字节数组大小
			inputStream.read(data);  //将流读入data
			inputStream.close();  //关闭流
		} catch (IOException e) {
			e.printStackTrace();
		}
		//将图片数组加密
		BASE64Encoder encoder = new BASE64Encoder();
		System.out.println("dada====="+ encoder.encode(data));
	}

}
