package com.example.ldemo2;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sun.javafx.application.PlatformImpl;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HtmlTests2 {

	private  static WebEngine webEngine;//webEngine必须要有引用在使用，不能定义成局部变量，否则被gc回收就没有输出结果



	public static void main(String[] args) throws InterruptedException {

		PlatformImpl.startup(() -> {
			new HtmlTests2().doDom();
			System.out.println("finish");

		});
	}


	@Test
	public  void  test4(){

		PlatformImpl.startup(() -> {
			doDom();
			System.out.println("finish");

		});

	}
	public void doDom(){

		 webEngine = new WebEngine();
		webEngine.getLoadWorker().stateProperty()
				.addListener((obs, oldValue, newValue) -> {
					if (newValue == Worker.State.SUCCEEDED) {
						String html = (String) webEngine
								.executeScript("document.documentElement.innerHTML");
						System.out.println(html);

						try {
							test33(html);
						} catch (IOException e) {
							e.printStackTrace();
						}

						System.exit(0);
					}
					System.out.println(newValue + " ");
				}); // addListener()
		webEngine.load("http://106.14.190.165:7588/licc/web/insurances/employerFillPolicy/employerFillPolicy?orderId=1301698881255505922");

        /*
        String s="<html>\n" +
                "\n" +
                "<head>\n" +
                "<title>我的第一个 HTML 页面</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<p>body 元素的内容会显示在浏览器中。</p>\n" +
                "<p id='a'>title 元素的内容会显示在浏览器的标题栏中。</p>\n" +
                "</body>\n" +
                "<script>var a =document.getElementById('a');\n" +
                "a.style.color='red';</script>\n" +
                "</html>\n";
        webEngine.loadContent(s);*/
	}


	public boolean download(String urlString, File localFile) {

		//如果文件夹不存在则创建

		if  (!localFile .exists())
		{
			System.out.println("//不存在");
			try {
				localFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else
		{
			System.out.println("//目录存在");
		}


		try {
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(5*1000);
			InputStream inputStream = connection.getInputStream();
			byte[] byteArr = new byte[10240];
			int len;
			File dir = localFile.getParentFile();
			if (dir.exists() == false)
				dir.mkdirs();
			OutputStream outputStream = new FileOutputStream(localFile);
			while ((len = inputStream.read(byteArr)) != -1) {
				outputStream.write(byteArr, 0, len);
			}
			outputStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}


	public void  test33(String docs) throws IOException {

//		String url = "http://106.14.190.165:7588/licc/web/insurances/employerFillPolicy/employerFillPolicy?orderId=1300355249776754690";
		String url = "http://106.14.190.165:7588/licc/web/insurances/employerFillPolicy/employerFillPolicy?orderId=1301698881255505922";
//		String url = "https://www.yezhuwuyou.com/#/home";

		Document doc = Jsoup.parse(docs,url);

		Elements links = doc.select("a[href]");
		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");
		String context=doc.toString();

		print("\nMedia: (%d)", media.size());
		for (Element src : media) {
			if (src.tagName().equals("img")) {

				download(src.attr("abs:src"),new File("e:/html"+src.attr("src")));

				print(" * %s: <%s> %sx%s (%s)",
						src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
						trim(src.attr("alt"), 20));
			}else{
				print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
			}
		}

		for(Element link : imports) {

			download(link.attr("abs:href"),new File("e:/html"+link.attr("href")));

			print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
		}

		for (Element link : links) {

			download(link.attr("abs:href"),new File("e:/html"+link.attr("href")));
			print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
		}



		OutputStreamWriter pw = null;
		pw = new OutputStreamWriter(new FileOutputStream("e:/html/"+System.currentTimeMillis()+".html"),"utf-8");
		pw.write(context);
		pw.close();

	}


	@Test
	public void  test() throws IOException {

//		String url = "http://106.14.190.165:7588/licc/web/insurances/employerFillPolicy/employerFillPolicy?orderId=1300355249776754690";
		String url = "http://106.14.190.165:7588/licc/web/insurances/employerInsurance";
//		String url = "https://www.yezhuwuyou.com/#/home";

		Document doc = null;
		try {
			doc = Jsoup.parse(getDocument(url),url);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Elements links = doc.select("a[href]");
		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");
		String context=doc.toString();

		print("\nMedia: (%d)", media.size());
		for (Element src : media) {
			if (src.tagName().equals("img")) {

				download(src.attr("abs:src"),new File("e:/html"+src.attr("src")));

				print(" * %s: <%s> %sx%s (%s)",
						src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
						trim(src.attr("alt"), 20));
			}else{
				print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
			}
		}

		for(Element link : imports) {

			download(link.attr("abs:href"),new File("e:/html"+link.attr("href")));

			print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
		}

		for (Element link : links) {

			download(link.attr("abs:href"),new File("e:/html"+link.attr("href")));
			print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
		}



		OutputStreamWriter pw = null;
		pw = new OutputStreamWriter(new FileOutputStream("e:/html/"+System.currentTimeMillis()+".html"),"utf-8");
		pw.write(context);
		pw.close();

	}

		private static void print(String msg, Object... args) {
			System.out.println(String.format(msg, args));
		}

		private static String trim(String s, int width) {
			if (s.length() > width)
				return s.substring(0, width-1) + ".";
			else
				return s;
		}


	public static String getDocument(String url) throws IOException, InterruptedException{
		WebClient wc = new WebClient(BrowserVersion.CHROME);
		//是否使用不安全的SSL
		wc.getOptions().setUseInsecureSSL(true);
		//启用JS解释器，默认为true
		wc.getOptions().setJavaScriptEnabled(true);
		//禁用CSS
		wc.getOptions().setCssEnabled(true);
		//js运行错误时，是否抛出异常
		wc.getOptions().setThrowExceptionOnScriptError(false);
		//状态码错误时，是否抛出异常
		wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
		//是否允许使用ActiveX
		wc.getOptions().setActiveXNative(false);
		//等待js时间
		wc.waitForBackgroundJavaScript(6000*1000);
		//设置Ajax异步处理控制器即启用Ajax支持
		wc.setAjaxController(new NicelyResynchronizingAjaxController());
		//设置超时时间
		wc.getOptions().setTimeout(1000000);
		//不跟踪抓取
		wc.getOptions().setDoNotTrackEnabled(true);
		WebRequest request=new WebRequest(new URL(url));
		request.setAdditionalHeader("Authorization", "d1e0fa18302a99ef3f566421a69318eb");
		request.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0");
		request.setAdditionalHeader("Cookie","Hm_lvt_117e36d8e26416aeb6355c74ff6b6de3=1597903217,1598854820; Hm_lpvt_117e36d8e26416aeb6355c74ff6b6de3=1598860587; authorization=d1e0fa18302a99ef3f566421a69318eb; userInfo=%257B%2522id%2522%253A%25221252832654852751362%2522%252C%2522account%2522%253A%252215900935478%2522%252C%2522password%2522%253Anull%252C%2522phone%2522%253A%252215900935478%2522%252C%2522userName%2522%253Anull%252C%2522headUrl%2522%253Anull%252C%2522openId%2522%253A%2522odIUfxFymLyQTlnlQZ-kBMxLzrbo%2522%252C%2522type%2522%253A%25220%2522%252C%2522sourceType%2522%253A%25221%2522%252C%2522sourceCity%2522%253A%2522%2522%252C%2522sourceRegion%2522%253A%2522%2522%252C%2522sourceCityId%2522%253A%2522%2522%252C%2522sourceRegionId%2522%253A%2522%2522%252C%2522isBuy%2522%253A%25221%2522%252C%2522isOld%2522%253A%25221%2522%252C%2522status%2522%253A%25220%2522%252C%2522createTime%2522%253A%25222020-04-22%252013%253A32%253A36%2522%252C%2522modifyTime%2522%253A%25222020-04-28%252015%253A44%253A42%2522%257D");
		try {
			//模拟浏览器打开一个目标网址
			HtmlPage htmlPage = wc.getPage(request);
			//为了获取js执行的数据 线程开始沉睡等待
			Thread.sleep(30000);//这个线程的等待 因为js加载需要时间的
			//以xml形式获取响应文本
			String xml = htmlPage.asXml();
			//并转为Document对象return
			return xml;
			//System.out.println(xml.contains("结果.xls"));//false
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
