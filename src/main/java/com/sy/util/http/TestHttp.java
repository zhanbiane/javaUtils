package com.sy.util.http;

import org.joda.time.DateTime;

/**
 * @deccription TODO
 *
 * @author zhanbiane
 * 2018年8月23日
 */
public class TestHttp {

	public static void main(String[] args) {
		System.out.println(new DateTime().getMillis());
		System.out.println(System.currentTimeMillis());
		System.out.println(new DateTime().getMillisOfSecond());
	}
}
