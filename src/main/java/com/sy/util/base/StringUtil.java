package com.sy.util.base;

import java.io.UnsupportedEncodingException;

/**
 * @deccription 常见字符串处理工具
 *
 * @author zhanbiane 2018年11月29日
 */
public class StringUtil {
	
	
	/**
	 * 半角转全角
	 * 
	 * @param input String.
	 * @return 全角字符串.
	 */
	public static String ToSBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);

			}
		}
		return new String(c);
	}

	/**
	 * 全角转半角
	 * 
	 * @param input String.
	 * @return 半角字符串
	 */
	public static String ToDBC(String input) {
		if(input == null){
			return "";
		}
		
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		String returnString = new String(c);
		return returnString;
	}
	

	/**
	 * String转unicode
	 * 
	 * @param string
	 * @return
	 */
	public static String string2Unicode(String string) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			// 取出每一个字符
			char c = string.charAt(i);
			// 转换为unicode,缺位不齐
			if (c < 0x10) {
				unicode.append("\\u000" + Integer.toHexString(c));
			} else if (c < 0x100) {
				unicode.append("\\u00" + Integer.toHexString(c));
			} else if (c < 0x1000) {
				unicode.append("\\u0" + Integer.toHexString(c));
			}else {				
				unicode.append("\\u" + Integer.toHexString(c));
			}
		}
		return unicode.toString();
	}

	/**
	 * unicode 转 string
	 * @param unicode
	 * @return
	 * 2018年11月30日
	 */
	public static String unicode2String(String unicode) {
		StringBuffer string = new StringBuffer();
		String[] hex = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) {
			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);
			// 追加成string
			string.append((char) data);
		}
		return string.toString();
	}
	
	/**
	 * 反转字符串
	 * @param value
	 * @return
	 * 2018年11月30日
	 */
	public static String reverse(String value){
		if(value == null){
			return null;
		}
		return new StringBuffer(value).reverse().toString();
	}
	
	/**
	 * 根据字节截取字符串(默认只有单字节字符和双字节字符)
	 * @param resourceString
	 * @param length
	 * @return
	 * 2018年11月30日
	 */
	public static String subString(String resourceString,int length){
		if (resourceString == null || "".equals(resourceString) || length < 1) {
			return resourceString;
		}
		char[] chr = resourceString.toCharArray();
		int strNum = 1;
		for (char c : chr) {
			if(strNum <= length) {
				if(c > 0x80) {
					length--;
				}
			}else {
				break;
			}
			strNum++;
		}
		length = length == 0?1:length;
		return resourceString.substring(0, length);
	}
	
	
	
	

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(subString("3i李白11112323", 5));
		System.out.println(reverse("l刘欢3"));
		String uni = string2Unicode("Z");
		System.out.println(unicode2String(uni)+"SS");
		System.out.println(string2Unicode(unicode2String(uni)));
	}

}
