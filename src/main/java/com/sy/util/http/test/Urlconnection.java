package com.sy.util.http.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;


/**  
 * @Description: TODO(JDK通信链接的父类，包括网络连接)
 * @author zhanbian 
 * @date 2017年1月19日 上午10:24:27
 */ 
public class Urlconnection {


	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("channel_no", "SHDP");
		map.put("trade_code", "C0001");
		map.put("channel_date", "20170303");
		map.put("channel_time", "10:20:20");
		map.put("channel_serial", "1231212333");
		
		String json = JSONObject.toJSONString(map);
		
		String urlstr = "http://172.17.20.116:7060/qdzh/request";
		String chartype = "utf8";
		String params = json;
		URLConnection conn = null;
		BufferedReader brin = null;
		OutputStreamWriter oswout = null;
		try {
			URL url = new URL(urlstr);
			conn = url.openConnection();
			//设置请求header
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
			conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			// 发送POST请求必须设置如下两行  
			conn.setDoOutput(true);  
			conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
			if(params!=null&&params.length()>0){
				oswout = new OutputStreamWriter(conn.getOutputStream(),chartype);
				oswout.write(params);
				// flush输出流的缓冲  
                oswout.flush(); 
			}
			int contentLength = Integer.parseInt(conn.getHeaderField("Content-Length")); 
			System.out.println(contentLength);
			if(contentLength>0){
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
					oswout=null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(brin!=null){
				try {
					brin.close();
				} catch (IOException e) {
					brin=null;
					e.printStackTrace();
				}
			}
		}
	}

}
