package com.sy.util.http.test;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.joda.time.DateTime;

import com.alibaba.fastjson.JSONObject;


/**  
 * @Description: TODO(基于httpclient更简单的处理方式)
 * @author zhanbian 
 * @date 2017年1月20日 下午3:15:45
 * 感觉比httpurlconnection慢，性能没做分享
 */ 
public class Httpfluent {

	public static void main(String[] args) throws ClientProtocolException, IOException, InterruptedException {
//		String result = Request.Get("http://www.baidu.com")
//		.connectTimeout(30000).socketTimeout(30000).execute().returnContent().asString();
//		System.out.println(result);
		
		//post,写法上确实方便了，具体功能实现能不能弥补多引入一个包的不舒服呢，有待详细考察
//		String a = Request.Post("http://172.17.20.116:7060/qdzh/request")
//		.bodyForm(Form.form().add("channel_no", "SHDP").add("trade_code", "C0001").add("channel_date", "20170302")
//				.add("channel_time", "09:45:20").add("channel_serial", "1231212333").build()).execute().returnContent().asString();
//		System.out.println(a);
		
		
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("channel_no", "SHDP");
//		map.put("trade_code", "C0001");
//		map.put("channel_date", "20170305");
//		map.put("channel_time", "09:45:20");
//		map.put("channel_serial", "434343434");
//		map.put("fund_type", "02");
//		map.put("sort_type", "02");
//		map.put("node_id", "EMM00000004");
		
		DateTime dt = new DateTime();
		JSONObject jsonparam = new JSONObject();
		jsonparam.put("channel_no", "SHDP");
		jsonparam.put("trade_code", "N0001");
		jsonparam.put("channel_date", dt.toString("yyyyMMdd"));
		jsonparam.put("channel_time", dt.toString("HH:mm:ss"));
		jsonparam.put("channel_serial", "434343434");
		jsonparam.put("classify_code", "BYBRDXW");//新闻类型
		jsonparam.put("page_num", "2");
		jsonparam.put("page_size", "20");
		
//		String json = JSONObject.toJSONString(map);
		String b = Request.Post("http://172.17.20.149:7060/qdzh/request")
		.body(new StringEntity(jsonparam.toJSONString(),"utf-8")).execute().returnContent().asString();
		System.out.println(b);
		
//		Thread.sleep(10000);
//		System.out.println();
//		
//		String b2 = Request.Post("http://172.17.20.116:7060/qdzh/request")
//		.body(new StringEntity(json,"utf-8")).execute().returnContent().asString();
//		System.out.println(b2);
		
		
//		String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22macau,macau%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
//		String result = Request.Get(url).execute().returnContent().asString();
//		System.out.println(result);
//		
//		JSONObject json = JSONObject.parseObject(result);
//		
//		System.out.println(json.getJSONObject("query").getInteger("count")==0);
		
		
		
	//////////////新的接口试验//////////////////////////////
//		String posturl = "http://139.217.17.123:80/hexin";
//		String result = Request.Post(posturl).bodyForm(Form.form().add("method", "quote").add("datetime", "0(0-0)")
//				.add("datatype", "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22").add("Code", "1A0001,399001,399005,399006").build())
//				.execute().returnContent().asString();
//		System.out.println(result);
		
		
	}
}
