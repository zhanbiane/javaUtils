package com.sy.util.http.test;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @deccription TODO
 *
 * @author zhanbiane
 * 2018年8月15日
 */
public class Test {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet("https://www.tax.sh.gov.cn/xbwz/qyda/qyda03/QYDAcfcxCtrl-cfxxcx.pfv?curPage=1&swjgDm=13100000000&ajmc=&jddw=&jdrq01=2015-01-01&jdrq02=2018-08-15");
		CloseableHttpClient client = HttpClients.createDefault();
		//这里一个单独的对象设置超时时间
		RequestConfig config = RequestConfig.custom().setConnectTimeout(30000)//毫秒
				.setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
		get.setConfig(config);
		get.setHeader("user-agent", "MozillaChromeSafari");
		
		CloseableHttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity);
		EntityUtils.consume(entity);//应该是关闭流
		System.out.println("请求结果"+result);		
	}
}
