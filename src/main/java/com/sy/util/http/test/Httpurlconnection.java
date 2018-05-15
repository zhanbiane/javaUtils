package com.sy.util.http.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class Httpurlconnection {
	
	public static void main(String[] args) {
		post();
	}
	
	public static void post(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("channel_no", "SHDP");
		map.put("trade_code", "C0001");
		map.put("channel_date", "20170302");
		map.put("channel_time", "09:45:20");
		map.put("channel_serial", "1231212333");
		
		String json = JSONObject.toJSONString(map);
		
		
		String urlstr = "http://172.17.20.116:7060/qdzh/request";
		String chartype = "utf8";
		String params = json;
		HttpURLConnection conn = null;
		BufferedReader brin = null;
		OutputStreamWriter oswout = null;
		try {
			URL url = new URL(urlstr);
			conn = (HttpURLConnection)url.openConnection();
			//设置请求header,一堆参数
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
			conn.setConnectTimeout(30000);//超时时间，毫秒
			conn.setReadTimeout(30000);//从主机读取数据超时时间，毫秒
			 
			conn.setRequestMethod("post");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			if(params!=null&&params.length()>0){
				oswout = new OutputStreamWriter(conn.getOutputStream(),chartype);
				oswout.write(params);	
				oswout.flush();
			}
			int contentLength = Integer.parseInt(conn.getHeaderField("Content-Length")); 
			System.out.println(contentLength);
			if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
				//读取返回信息
				String lineStr;
				StringBuffer sb = new StringBuffer();
				brin = new BufferedReader(new InputStreamReader(conn.getInputStream(), chartype));
				while((lineStr = brin.readLine())!=null){
					sb.append(lineStr);
				}
				System.out.println(sb.toString());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(oswout!=null){
				try {
					oswout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(brin!=null){
				try {
					brin.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				conn.disconnect();
				conn = null;
			}
		}
		
	}
	
	
	//GET请求
	private static void get(){
		String urlstr = "http://www.baidu.com";
		String chartype = "utf8";
		HttpURLConnection conn = null;
		BufferedReader brin = null;
		try {
			URL url = new URL(urlstr);
			conn = (HttpURLConnection)url.openConnection();
			//设置请求header
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			//建立连接
			conn.connect();
			System.out.println(conn.getResponseCode());
			if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
				//读取返回信息
				String lineStr;
				StringBuffer sb = new StringBuffer();
				brin = new BufferedReader(new InputStreamReader(conn.getInputStream(), chartype));
				while((lineStr = brin.readLine())!=null){
					sb.append(lineStr);
				}
				System.out.println(sb.toString());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(brin!=null){
				try {
					brin.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				conn.disconnect();
				conn = null;
			}
		}
	}
}
