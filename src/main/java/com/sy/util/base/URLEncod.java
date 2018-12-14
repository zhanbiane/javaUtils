package com.sy.util.base;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class URLEncod {

	/**
	 * 键值对转GET请求参数链接
	 * @param host
	 * @param params
	 * @return
	 */
	public static String getUrl(String host, Map<String,Object> params,String charset) {
		if(params == null) {
			return host;
		}
		List<NameValuePair> nameValuePairs = new ArrayList<>();
		params.forEach((key,value) ->{
			nameValuePairs.add(new BasicNameValuePair(key, value.toString()));
		});
		return host+"?"+URLEncodedUtils.format(nameValuePairs, charset);
	}
	
	/**
	 * 如 https://blog.csdn.net/zhujianlin1990/article/details/51469359
	 * 返回 https://blog.csdn.net
	 * @param url
	 * @return 
	 */
	public static String getHost(String url) {
		java.net.URL URL;
		try {
			URL = new java.net.URL(url);
			return URL.getProtocol()+"://"+URL.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * urlEncord
	 * @param str
	 * @param charset
	 * @return
	 * 2018年12月14日
	 */
	public static String urlEncord(String str,String charset) {
		try {
			return java.net.URLEncoder.encode(str, charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
