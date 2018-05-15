package com.sy.util.http.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;


/**  
 * @Description: TODO(apache提供的请求工具，包括httpclient-4.3以上,httpcore,commons-logging,commons-codec)
 * @author zhanbian 
 * @date 2017年1月19日 上午10:26:02
 * DefaultHttpClient —> CloseableHttpClient
 * HttpResponse —> CloseableHttpResponse
 * 感觉就是请求用entity得到也是entity
 */ 
public class Httpclient {
	
	public static void main(String[] args) throws IOException {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("channel_no", "SHDP");
		map.put("trade_code", "C0002");
		map.put("channel_date", "20170305");
		map.put("channel_time", "09:45:20");
		map.put("channel_serial", "434343434");
		
		String json = JSONObject.toJSONString(map);
		
		
		HttpPost post = new HttpPost("http://172.17.20.116:7060/qdzh/request");
		CloseableHttpClient client = HttpClients.createDefault();
		//这里一个单独的对象设置超时时间
		RequestConfig config = RequestConfig.custom().setConnectTimeout(30000)//毫秒
				.setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
		post.setConfig(config);
		
//		post.addHeader("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
//		nvp.add(new BasicNameValuePair("channel_no", "SHDP"));//设置参数
//		nvp.add(new BasicNameValuePair("trade_code", "C0001"));//设置参数
//		nvp.add(new BasicNameValuePair("channel_date", "20170302"));//设置参数
//		nvp.add(new BasicNameValuePair("channel_time", "09:45:20"));//设置参数
//		nvp.add(new BasicNameValuePair("channel_serial", "1231212333"));//设置参数
//		
//		//将参数加入请求
//		post.setEntity(new UrlEncodedFormEntity(nvp, "utf8"));
		
		post.setEntity(new StringEntity(json, "utf8"));
		
		//执行请求
		//建立的http连接，仍旧被response1保持着，允许我们从网络socket中获取返回的数据
	    //为了释放资源，我们必须手动消耗掉response1或者取消连接（使用CloseableHttpResponse类的close方法）
		CloseableHttpResponse response = client.execute(post);
		System.out.println(response.getStatusLine());
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity);
		EntityUtils.consume(entity);//应该是关闭流
		System.out.println("请求结果"+result);
		
		HttpPost post2 = new HttpPost("http://172.17.20.116:7060/qdzh/request");
		CloseableHttpClient client2 = HttpClients.createDefault();
		//这里一个单独的对象设置超时时间
		RequestConfig config2 = RequestConfig.custom().setConnectTimeout(30000)//毫秒
				.setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
		post.setConfig(config2);
		
//		post.addHeader("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
//		nvp.add(new BasicNameValuePair("channel_no", "SHDP"));//设置参数
//		nvp.add(new BasicNameValuePair("trade_code", "C0001"));//设置参数
//		nvp.add(new BasicNameValuePair("channel_date", "20170302"));//设置参数
//		nvp.add(new BasicNameValuePair("channel_time", "09:45:20"));//设置参数
//		nvp.add(new BasicNameValuePair("channel_serial", "1231212333"));//设置参数
//		
//		//将参数加入请求
//		post.setEntity(new UrlEncodedFormEntity(nvp, "utf8"));
		
		post2.setEntity(new StringEntity(json, "utf8"));
		
		//执行请求
		//建立的http连接，仍旧被response1保持着，允许我们从网络socket中获取返回的数据
	    //为了释放资源，我们必须手动消耗掉response1或者取消连接（使用CloseableHttpResponse类的close方法）
		CloseableHttpResponse response2 = client2.execute(post2);
		System.out.println(response2.getStatusLine());
		HttpEntity entity2 = response2.getEntity();
		String result2 = EntityUtils.toString(entity2);
		EntityUtils.consume(entity2);//应该是关闭流
		System.out.println("请求结果"+result2);
	}
}
