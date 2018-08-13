package com.sy.util.http.test;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @deccription TODO
 *
 * @author zhanbiane
 * 2018年7月30日
 */
public class MainTest {
	public static void main(String[] args) throws ParseException, IOException {

		HttpGet get = new HttpGet("http://www.credithebei.gov.cn:8082/was5/web/search?page=1&channelid=267168&orderby=RELEVANCE&perpage=100&outlinepage=10&searchscope=&timescope=&timescopecolumn=&orderby=RELEVANCE&andsen=&total=&orsen=&exclude=");
		HttpHost proxy = new HttpHost("localhost",8888);
		RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(10000).setSocketTimeout(15000).build();
		CloseableHttpClient httpClient= HttpClientBuilder.create().setDefaultRequestConfig(config).build();
//		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(get);
		System.out.println(response.getStatusLine());
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity);
		EntityUtils.consume(entity);//应该是关闭流
		System.out.println("请求结果"+result);
	
	}

	/**
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * 
	 */
	public MainTest() throws ClientProtocolException, IOException {}
}
