package com.sy.util.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

	public static void main(String[] args) {  
        String line = "schoolmate";  
        String regex = "school";//正则  
        Pattern patt = Pattern.compile(regex);  
        Matcher m = patt.matcher(line);  
          
        //等同于Pattern.matches(regex, line)  
        System.out.println(m.matches());//结果，false  
        System.out.println(m.lookingAt());//结果，true  
//      matcher 要求整个序列都匹配，而lookingAt 不要求。  
          
        common("2222-22-9e");  
    }  
    //常用的验证项  
    public static void common(String str){  
        System.out.println("原有的字符串"+str);  
        System.out.println("yyyy-mm-dd："+Pattern.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$", str));
        System.out.println("是否是数字："+Pattern.matches("^[0-9]+$", str));  
        System.out.println("是否有汉字："+Pattern.compile("[\u4e00-\u9fa5]").matcher(str).find());  
        System.out.println("验证手机号："+Pattern.matches("^0?1[34578]\\d{9}$", str));  
        System.out.println("验证邮箱："+Pattern.matches("^[a-zA-Z0-9_-]+@\\w+\\.[a-zA-Z0-9]{2,3}$", str));  
    }
}
