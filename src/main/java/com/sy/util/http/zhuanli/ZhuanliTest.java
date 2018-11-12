package com.sy.util.http.zhuanli;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @deccription TODO
 *
 * @author zhanbiane
 * 2018年8月27日
 */
public class ZhuanliTest {

	public static void main(String[] args) {
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
	    
	    try {
			// 创建一个get请求用来获取必要的Cookie，如_xsrf信息
			HttpGet get = new HttpGet("http://www.zhihu.com/");
			get.addHeader("abc", "adc");
			res = httpClient.execute(get, context);
			// 获取常用Cookie,包括_xsrf信息
			System.out.println("请求header==================");
			Header[] headers = get.getAllHeaders();
			for (Header header : headers) {
				System.out.println(header.getName()+":"+header.getValue());
			}
			
			System.out.println("访问知乎首页后的获取的常规Cookie:===============");
			for (Cookie c : cookieStore.getCookies()) {
			  System.out.println(c.getName() + ": " + c.getValue());
			}
			res.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
}
