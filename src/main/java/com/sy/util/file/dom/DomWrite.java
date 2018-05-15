package com.sy.util.file.dom;

import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class DomWrite {

	public static void main(String[] args) throws IOException {
		//创建根节点/创建节点使之变成跟节点
		Element root = DocumentHelper.createElement("students");
		Document documte = DocumentHelper.createDocument(root);//创建根节点
		
		//创建第一层子节点并设置属性
		Element element1 = root.addElement("student");
		element1.addAttribute("num", "001").addAttribute("class", "c1");//设置学号，班级
		
		//创建第二层节点
		element1.addElement("学号").addText("001");
		element1.addElement("班级").addText("c1");
		element1.addElement("姓名").addText("黎明");
		element1.addElement("性别").addText("男");
		element1.addElement("年龄").addText("13");
		
		String path = "test.xml";
		OutputFormat format = OutputFormat.createPrettyPrint();//整齐的排版
		//OutputFormat.createCompactFormat();//紧凑的排版
		format.setEncoding("utf8");
		
		XMLWriter writer = new XMLWriter(new FileOutputStream(path), format);
		writer.write(documte);//写入文件
		writer.close();
	}
}
