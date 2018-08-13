package com.sy.util.time;

import org.joda.time.DateTime;

/**
 * @deccription TODO
 *
 * @author zhanbiane
 * 2018年7月11日
 */
public class JodaTime {

	public static void main(String[] args) {
		DateTime dt = new DateTime();
		System.out.println(dt.getMinuteOfDay());
		System.out.println(dt.getMinuteOfHour());
	}
}
