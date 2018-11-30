package com.sy.util.base;

/**
 * @deccription 关于编码转换
 *
 * @author zhanbiane
 * 2018年11月30日
 */
public class EncodeUtil {

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
	
	public static void main(String[] args) {
		System.out.println(string2Unicode("严"));
	}
}
