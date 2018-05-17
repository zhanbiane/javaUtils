package com.sy.util.base;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @deccription 获得ip地址
 *
 * @author zhanbiane
 * 2018年5月17日
 */
public class IpUtil {

	//获得本地ip地址
	/**
	 * 此方法不一定是你想要的局域网ip
	 */
	public static String getLocalHost() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}
	
	/**
	 * 查看本机所有的ip
	 */
	public static void getAllLocalHost() throws UnknownHostException {
		InetAddress[] a = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
		for(InetAddress b:a) {
			System.out.println(b.getHostAddress());
			
		}
	}
	
	/**
	 * 通过请求域名获得ip
	 * 这个或许是你想要的
	 */
	@SuppressWarnings("resource")
	public static String getLocalHostByHost(String host) throws IOException {
		return new Socket("www.baidu.com", 80).getLocalAddress().getHostAddress();
	}
	
	/**
	 * 获得指定域名的ip
	 */
	public static String getHostIp(String host) throws UnknownHostException {
		return InetAddress.getByName(host).getHostAddress();
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(getHostIp("www.baidu.com"));
		System.out.println(getLocalHostByHost("www.baidu.com"));
	}
}
