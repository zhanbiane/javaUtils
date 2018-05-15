package com.sy.util.regex;

import java.util.regex.Pattern;

/**
 * 正则常用校验
 * @deccription TODO
 * @author zhanbiane
 * 2018年3月14日
 */
public class RegexUtil {
	
	/**
	 * 自定义验证
	 * @param regex 这则表达式
	 * @param input 需验证的字符串
	 * @return
	 */
	public static boolean check(String regex,String input) {
		if(regex==null||input==null) {return false;}
		return Pattern.matches(regex, input);
	}

	/**
	 * 验证手机号 中国内地+86，香港+00852
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		if(mobile==null) {return false;}
        String regex = "(\\+\\d+)?1[34578]\\d{9}$";
        return Pattern.matches(regex,mobile);
    }
	
	/**
	 * 验证固定电话 (中国：0086+当地区号+电话号码)
	 * @param phone
	 * @return
	 */
	public static boolean checkPhone(String phone) {
		if(phone==null) {return false;}
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        return Pattern.matches(regex,phone);
    }
	
	/**
     * 邮箱验证
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        if(email==null){return false;}
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }
    
    /**
     * 验证身份证号
     * @param identityCardNo
     * @return
     */
    public static boolean checkIdentityCardNo(String identityCardNo){
        if(identityCardNo==null){return false;}
        String regex = "(^\\d{17}(\\d|x|X)$)|(^\\d{15}$)";
        return Pattern.matches(regex,identityCardNo);
    }
    
    /**
     * 验证车牌号
     * @param plateno
     * @return
     */
    public static boolean checkPlateno(String plateno){
        if(plateno==null){return false;}
        String regex = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
        return Pattern.matches(regex,plateno);
    }
	
    /**
     * 是否有中文
     * @param str
     * @return
     */
    public static boolean containHanZi(String str){
        if(str==null){return false;}
        return java.util.regex.Pattern.compile("[\u4e00-\u9fa5]").matcher(str).find();
    }
    
    /**
     * 验证汉字
     * @param hanzi
     * @return
     */
    public static boolean checkHanzi(String hanzi){
        if(hanzi==null){return false;}
        String regex = "^[\u4e00-\u9fa5]+$";
        return Pattern.matches(regex,hanzi);
    }
    
    /**
     * URL验证
     * @param url
     * @return
     */
    public static boolean checkURL(String url) { 
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?"; 
        return Pattern.matches(regex, url); 
    } 
}
