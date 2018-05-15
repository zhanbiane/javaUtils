package com.sy.util.file.dom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 使用jar包，dom4j、xml-apis
 * @Description: dom4j读写
 * 实现一个小功能，读取文件，删除节点班级，学号修改为4位如0001，添加一位学生，保存到文件
 * @author zhanbian
 * @date 2017年7月3日 下午2:09:52
 */
public class DomReadWrite {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws DocumentException, IOException {
		String path = "test.xml";
		File file = new File(path);
		if(!file.exists()){
			System.out.println("创建功能请往上看");
			return;
		}else{
			SAXReader reader = new SAXReader();
			Document document = reader.read(file);//这获得xml的内容
			System.out.println(document.asXML());//看看文件中都有什么内容
			Element root = document.getRootElement();//获得root节点
			Iterator<Element> elem_stus = root.elementIterator("student");
			while(elem_stus.hasNext()){
				Element elem_stu = elem_stus.next();
				String numvalue = elem_stu.attributeValue("num");
				if(numvalue.length()==3){
					//修改学号
					elem_stu.attribute("num").setValue("0"+numvalue);
					elem_stu.element("学号").setText("0"+numvalue);
				}
				Element clas = elem_stu.element("班级");
				//删除班级节点
				elem_stu.remove(clas);
			}
			//创建第一层子节点并设置属性
			Element element1 = root.addElement("student");
			element1.addAttribute("num", "0002").addAttribute("class", "c1");//设置学号，班级
			
			//创建第二层节点
			element1.addElement("学号").addText("0002");
			element1.addElement("姓名").addText("关羽");
			element1.addElement("性别").addText("男");
			element1.addElement("年龄").addText("13");
			
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf8");
			
			XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
			writer.write(document);
			writer.close();
		}
	}
}
