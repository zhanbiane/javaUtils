package com.sy.util.http.plus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class Httpsurlconnection {

	public static void main(String[] args) {
		String urlstr = "";
		String chartype = "utf8";
		String params = "key=value$key2=value2";
		HttpsURLConnection conn = null;
		BufferedReader brin = null;
		OutputStreamWriter oswout = null;
		
		
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化 
			TrustManager[] tm = {new MyTrustmanager()};
			SSLContext ssl = SSLContext.getInstance("SSL", "SunJSSE");
			ssl.init(null, tm, new SecureRandom());
			
			URL url = new URL(urlstr);
			conn = (HttpsURLConnection)url.openConnection();
			conn.setSSLSocketFactory(ssl.getSocketFactory());
			
			conn.setRequestMethod("post");
			conn.setDoInput(true);
			conn.setDoOutput(true);
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
			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
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
}
