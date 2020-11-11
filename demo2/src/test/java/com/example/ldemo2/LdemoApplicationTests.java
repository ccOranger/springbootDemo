package com.example.ldemo2;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
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
public class LdemoApplicationTests {

	@Test
	public void contextLoads() {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.open-open.com").get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Element link = doc.select("a").first();
		String relHref = link.attr("href"); // == "/"
		String absHref = link.attr("abs:href"); // "http://www.open-open.com/"
	}

	@Test
	public void contextLoads1() throws IOException, InterruptedException {

		String url = "http://106.14.190.165:7588/licc/web/insurances/employerInsurance";
		// Jsoup解析处理
		Document doc = Jsoup.parse(getDocument(url), url);


		Elements links = doc.select("a[href]");
		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");
		String context=doc.toString();

		print("\nMedia: (%d)", media.size());
		for (Element src : media) {
			if (src.tagName().equals("img")) {

				download(src.attr("abs:src"),new File("e:"+src.attr("src")));

				print(" * %s: <%s> %sx%s (%s)",
						src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
						trim(src.attr("alt"), 20));
			}else{
				print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
			}
		}

		for(Element link : imports) {

			download(link.attr("abs:href"),new File("e:"+link.attr("href")));

			print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
		}

		for (Element link : links) {

			download(link.attr("abs:href"),new File("e:"+link.attr("href")));
			print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
		}



		OutputStreamWriter pw = null;
		pw = new OutputStreamWriter(new FileOutputStream("e:/"+System.currentTimeMillis()+".html"),"utf-8");
		pw.write(context);
		pw.close();


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
			byte[] byteArr = new byte[1024];
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

	@Test
	public void  test() throws IOException {

		String url = "http://106.14.190.165:7588/licc/web/insurances/employerInsurance";

		Document doc = Jsoup.connect(url).get();

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

			download(link.attr("abs:href"),new File("e:"+link.attr("href")));

			print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
		}

		for (Element link : links) {

			download(link.attr("abs:href"),new File("e:"+link.attr("href")));
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
		/*String url="https://www.marklines.com/cn/vehicle_sales/search_country/search/?searchID=587200";
          Connection connect = Jsoup.connect(url).userAgent("")
                 .header("Cookie", "PLAY_LANG=cn; _plh=b9289d0a863a8fc9c79fb938f15372f7731d13fb; PLATFORM_SESSION=39034d07000717c664134556ad39869771aabc04-_ldi=520275&_lsh=8cf91cdbcbbb255adff5cba6061f561b642f5157&csrfToken=209f20c8473bc0518413c226f898ff79cd69c3ff-1539926671235-b853a6a63c77dd8fcc364a58&_lpt=%2Fcn%2Fvehicle_sales%2Fsearch&_lsi=1646321; _ga=GA1.2.2146952143.1539926675; _gid=GA1.2.1032787565.1539926675; _plh_notime=8cf91cdbcbbb255adff5cba6061f561b642f5157")
                  .timeout(360000000);
          Document document = connect.get();*/
		        WebClient wc = new WebClient(BrowserVersion.CHROME);
		        //是否使用不安全的SSL
		        wc.getOptions().setUseInsecureSSL(true);
		        //启用JS解释器，默认为true
		         wc.getOptions().setJavaScriptEnabled(true);
		         //禁用CSS
		         wc.getOptions().setCssEnabled(false);
		         //js运行错误时，是否抛出异常
		wc.getOptions().setThrowExceptionOnScriptError(false);
		         //状态码错误时，是否抛出异常
		        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
		        //是否允许使用ActiveX
		         wc.getOptions().setActiveXNative(false);
		         //等待js时间
		         wc.waitForBackgroundJavaScript(600*1000);
		         //设置Ajax异步处理控制器即启用Ajax支持
		      wc.setAjaxController(new NicelyResynchronizingAjaxController());
		         //设置超时时间
		         wc.getOptions().setTimeout(1000000);
		        //不跟踪抓取
		         wc.getOptions().setDoNotTrackEnabled(false);
		         WebRequest request=new WebRequest(new URL(url));
		          request.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0");
		          request.setAdditionalHeader("Cookie","PLAY_LANG=cn; _plh=b9289d0a863a8fc9c79fb938f15372f7731d13fb; PLATFORM_SESSION=39034d07000717c664134556ad39869771aabc04-_ldi=520275&_lsh=8cf91cdbcbbb255adff5cba6061f561b642f5157&csrfToken=209f20c8473bc0518413c226f898ff79cd69c3ff-1539926671235-b853a6a63c77dd8fcc364a58&_lpt=%2Fcn%2Fvehicle_sales%2Fsearch&_lsi=1646321; _ga=GA1.2.2146952143.1539926675; _gid=GA1.2.1032787565.1539926675; _plh_notime=8cf91cdbcbbb255adff5cba6061f561b642f5157");
		         try {
			    //模拟浏览器打开一个目标网址
			            HtmlPage htmlPage = wc.getPage(request);
			             //为了获取js执行的数据 线程开始沉睡等待
			             Thread.sleep(1000);//这个线程的等待 因为js加载需要时间的
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
