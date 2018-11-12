package com.sy.util.http.zhuanli;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @deccription TODO
 *
 * @author zhanbiane
 * 2018年8月27日
 */
public class ZhuanliLogin {
	private final static Logger logger = LoggerFactory.getLogger(ZhuanliLogin.class);
	

	public static void main(String[] args) {
		loginZhuanli("", "");
		
		
		
	}
	public static String loginZhuanli(String username,String password) {
	    // 全局请求设置
	    RequestConfig globalConfig = RequestConfig.custom()
	    		.setConnectTimeout(15000).setConnectionRequestTimeout(15000).setSocketTimeout(15000)
	    		.setCookieSpec(CookieSpecs.STANDARD).build();
	    // 创建cookie store的本地实例
	    CookieStore cookieStore = new BasicCookieStore();
	    // 创建HttpClient上下文
	    HttpClientContext context = HttpClientContext.create();
	    context.setCookieStore(cookieStore);
	 
	    // 创建一个HttpClient
	    CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
	        .setDefaultCookieStore(cookieStore).build();
	    
	    CloseableHttpResponse res = null;	
		
		//1.登陆页
	    try {
			HttpGet get = new HttpGet("http://www.pss-system.gov.cn/sipopublicsearch/portal/uilogin-forwardLogin.shtml");
			for (Entry<String, String> entry : loginHeader().entrySet()) {
				get.addHeader(entry.getKey(),entry.getValue());
			}
			printHeader("进入登陆页", get.getAllHeaders());
			res = httpClient.execute(get, context);
			printHeader("进入登陆页返回", res.getAllHeaders());
			res.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//2.下载图片
		//3.识别图片
		//4.checkLoginTimes-check
		//5.sessionDeBugAC
		//6.wee_security_check
		//7.成功登陆页
		//8.首页
		//9.验证存活
		return null;
	}
	
	
	
	
	
	
	public static Map<String,String> loginHeader(){
		Map<String,String> header = new HashMap<>();
		header.put("Accept", "application/json, text/javascript, */*; q=0.01");
		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Accept-Language", "zh-CN,zh;q=0.9");
		header.put("Referer", "http://www.pss-system.gov.cn/sipopublicsearch/portal/uiIndex.shtml");
	
		return header;
	}
	
	/**
	 * 主页请求头
	 */
	public static Map<String,String> indexHeader(){
		Map<String,String> header = new HashMap<>();
		header.put("Accept", "application/json, text/javascript, */*; q=0.01");
		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Accept-Language", "zh-CN,zh;q=0.9");
		header.put("X-Requested-With", "XMLHttpRequest");
		header.put("Content-Type", "application/x-www-form-urlencoded");
		header.put("Referer", ": http://www.pss-system.gov.cn/sipopublicsearch/patentsearch/showNavigationClassifyNum-showBasicClassifyNumPageByIPC.shtml?params=D7B3D1618C9AC685055FF6612F62529676324C8B6E7F921902B2C40318E0E7BB");
	
		return header;
	}
	
	public static void printHeader(String title,Header[] headers) {
		logger.info("======{}======",title);
		for (Header header : headers) {
			System.out.println(header.getName()+":"+header.getValue());
		}
	}
	
}
