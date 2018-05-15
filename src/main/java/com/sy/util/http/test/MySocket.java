package com.sy.util.http.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;

/**
 * 未验证
 * @Description: TODO(用一句话描述该文件做什么)
 * @author zhanbian 
 * @date 2017年1月19日 下午2:13:03
 */
public class MySocket {

	public static void main(String[] args) throws IOException {
		String urlstr = "http://www.taobao.com/lk/lk?lk=12342123";
		String params = "key=value&key2=value2";
		String chartype = "utf8";
		URL url = new URL(urlstr);
		System.out.println(url.getHost()+","+url.getPath()+","+url.getPort());
		String path = url.getPath();//工程访问路径
		String host = url.getHost();//域名
		int part = url.getPort();
		
		Socket socket = new Socket(host, part);
		
		StringBuffer sb =  new StringBuffer();
		sb.append("GET " + path + " HTTP/1.1\r\n");
		sb.append("Host: " + host + "\r\n");
		sb.append("Connection: Keep-Alive\r\n"); 
		sb.append("Content-Type: application/x-www-form-urlencoded; charset=utf-8 \r\n"); 
		sb.append("Content-Length: ").append(sb.toString().getBytes().length).append("\r\n");
		// 这里一个回车换行，表示消息头写完，不然服务器会继续等待  
        sb.append("\r\n"); 
        
        OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
        out.write(params);
        out.flush();//参数缓存
        
        InputStream is = socket.getInputStream();
		is.read();// 换行符的ascii码值为10//可以根据Content-Length中通过.split(":")[1].trim()方法找到content长度
		
		BufferedReader brin = new BufferedReader(new InputStreamReader(is, chartype));
		StringBuffer result = new StringBuffer();
		String linestr;
        while((linestr=brin.readLine())!=null){
        	result.append(linestr);
        }
        System.out.println(result.toString());
        if(is!=null){
        	is.close();
        }
		brin.close();
		out.close();
		socket.close();
		socket = null;
	}
}
