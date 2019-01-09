package com.sy.util.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @deccription 常见字符串处理工具
 *
 * @author zhanbiane 2018年11月29日
 */
public class StringUtil {
	
	
	/**
	 * 分离数量和单位
	 * @param unitStr
	 * @return 返回结果根据空格分开
	 */
	public static String[] splitCell(String unitStr) {
		if(unitStr!=null) {
			String[] s = new String[2];
			String u = replaceBlank(unitStr);
			String num = findOne(u,"^([0-9]+\\.?[0-9]*)[\\s\\S]*");
			s[0]=num;
			if(num != null && num.length()<unitStr.length()) {
				String unit = u.substring(num.length());
				s[1]=unit;
			}
			return s;
		}
		return null;
	}
	
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
	
	/**
	 * 去除所有空格、制表符、换行符
	 * @param str
	 * @return
	 * 2018年11月30日
	 * 
	 * unicode正则表达
	 * P：标点字符
	 * L：字母； 
	M：标记符号（一般不会单独出现）； 
	Z：分隔符（比如空格、换行等）； 
	S：符号（比如数学符号、货币符号等）； 
	N：数字（比如阿拉伯数字、罗马数字等）； 
	C：其他字符
	参考：https://blog.csdn.net/tian330726/article/details/50906318
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			dest = str.replaceAll("[\\pZ\\s]", "");
		}
		return dest;
	}
	
	/**
	 * unicode 去除不显示的特殊占位字符
	 * @param str
	 * @return
	 */
	public static String replaceBlankC(String str) {
		String dest = "";
		if (str != null) {
			dest = str.replaceAll("[\\pC\\s]", "");
		}
		return dest;
	}
	
	/**
	 * 查找一个匹配的字符串
	 * @param text
	 * @param regx
	 * @return
	 */
	public static String findOne(String text,String regx) {
		Pattern regex = Pattern.compile(regx);
		Matcher matcher = regex.matcher(text);
		String str = null;
		while(matcher.find()) {
			str = matcher.group(1);
		};
		return str;
	}
	
	/**
	 * 判断是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if(null != str && !"".equals(str)) {
			return false;
		}
		return true;
	}
}
