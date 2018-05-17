package com.sy.util.system;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @deccription 读取系统配置文件（system.properties）
 *
 * @author zhanbiane
 * 2018年5月16日
 */
public class PropertyTest {

	public static void main(String[] args) throws IOException {
//		String path = "com/sy/util/system/test.properties";
		String filePath = "test.properties";
//		PropertyTest.class.getClassLoader().getResourceAsStream(path);
//		ClassLoader.getSystemResourceAsStream(path);
//		PropertyTest.class.getResourceAsStream(filePath);
		writePro(getPro2(filePath));
    }
	
	public void getAll() {
        Properties pps = System.getProperties();
        pps.list(System.out);
		
	}
	
	//以下2种方法读取Properties
	/**
	 * PropertyResourceBundle
	 */
	public static ResourceBundle getPro1(String filePath) throws IOException {
		InputStream is = PropertyTest.class.getResourceAsStream(filePath);
		InputStreamReader isr = new InputStreamReader(is, "utf8");
		ResourceBundle rb = new PropertyResourceBundle(isr);
		Enumeration<String> em = rb.getKeys();
		while(em.hasMoreElements()) {
			String name = em.nextElement();
			System.out.print(name+"=");
			System.out.println(rb.getString(name));
		}
		return rb;
	}
	
	/**
	 * Properties
	 */
	public static Properties getPro2(String filePath) throws IOException {
		InputStream is = PropertyTest.class.getResourceAsStream(filePath);
		InputStreamReader isr = new InputStreamReader(is, "utf8");
		Properties p = new Properties();
		p.load(isr);
		Set<Object> set = p.keySet();
		Iterator<Object> itr = set.iterator();
		while(itr.hasNext()) {
			String name = itr.next().toString();
			System.out.print(name+"=");
			System.out.println(p.getProperty(name));
		}
		return p;
	}
	
	//写入数据
	public static Properties writePro(Properties p) throws IOException {
		p.setProperty("height", "170");//此处未真正写入文件
		String filepath = "D:\\workspace\\myworkspace\\javaUtils\\src\\main\\java\\com\\sy\\util\\system\\test.properties";
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filepath),"utf8");
		p.store(out, "log");
		Set<Object> set = p.keySet();
		Iterator<Object> itr = set.iterator();
		while(itr.hasNext()) {
			String name = itr.next().toString();
			System.out.print(name+"=");
			System.out.println(p.getProperty(name));
		}
		return p;
	}
}
