package com.sy.util.base;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @deccription 获得ip地址
 *
 * @author zhanbiane
 * 2018年5月17日
 */
public class IpUtil {

	//获得本地ip地址
	/**
	 * 常用的获取ip的方法
	 */
	public static String getLocalHost() throws UnknownHostException {
		 String sysType = System.getProperties().getProperty("os.name");
		 if(sysType.toLowerCase().startsWith("win")) {  
			 return InetAddress.getLocalHost().getHostAddress();
		 }else{
			 return getIpByEthNum("eth0"); // 兼容Linux
		 }
		
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
	 * 若不想获得虚拟机的ip可用这个
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
	
	/**
     * 根据网络接口获取IP地址
     * @param ethNum 网络接口名，Linux下是eth0
     * @return
     */
    @SuppressWarnings("rawtypes")
	private static String getIpByEthNum(String ethNum) {
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (ethNum.equals(netInterface.getName())) {
					Enumeration addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = (InetAddress) addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
        }
        return "获取服务器IP错误";
    }
	
	
	public static void main(String[] args) throws IOException {
		System.out.println(getLocalHost());
		System.out.println(getHostIp("www.baidu.com"));
		System.out.println(getLocalHostByHost("www.baidu.com"));
	}
	
}
