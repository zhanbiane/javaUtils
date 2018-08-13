package com.sy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

/**
 * @deccription 处理String常见情况
 *
 * @author zhanbiane
 * 2018年7月2日
 */
public class StringUtil {

	public static void main(String[] args) throws ParseException {
		System.out.println(" sss ss d ".trim()+"ABC");
		System.out.println(" s　　　　   　　　        ".trim()+"ABC");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
//		DateTime dt = new DateTime("2018年5月30日");
		System.out.println(sdf2.format(sdf.parse("2018年5月30日")));
	}
}
