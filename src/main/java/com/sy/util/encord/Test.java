package com.sy.util.encord;

import java.io.UnsupportedEncodingException;

/**
 * @deccription TODO
 *
 * @author zhanbiane
 * 2018年8月7日
 */
public class Test {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String chinese = "Google host 鎸佺画鏇存柊鍦板潃锛歨ttp://blog.my-eclipse.cn/host-google.html";//java内部编码
		String gbkChinese = new String(chinese.getBytes("GBK"),"UTF-8");//转换成gbk编码
		System.out.println(gbkChinese);//乱码
		String unicodeChinese = new String(gbkChinese.getBytes("ISO-8859-1"),"GBK");//java内部编码
		System.out.println(unicodeChinese);//中文
		String utf8Chinese = new String(unicodeChinese.getBytes("UTF-8"),"ISO-8859-1");//utf--8编码
		System.out.println(utf8Chinese);//乱码
		unicodeChinese = new String(utf8Chinese.getBytes("ISO-8859-1"),"UTF-8");//java内部编码
		System.out.println(unicodeChinese);//中文
	}
}
