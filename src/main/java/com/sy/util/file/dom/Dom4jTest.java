package com.sy.util.file.dom;

import java.io.IOException;
import java.util.Iterator;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @Description: 
 * 使用jar包，dom4j、xml-apis
 *
 * @author zhanbian
 * @date 2017年7月3日 上午10:55:26
 */
public class Dom4jTest {

	public static void main(String[] args) throws ClientProtocolException, IOException, DocumentException {
//		String posturl = "http://139.217.17.123:80/hexin";
		String posturl = "http://139.219.3.212:80/hexin";
		String result = Request.Post(posturl).bodyForm(Form.form().add("method", "quote").add("datetime", "0(0-0)")
				.add("datatype", "6,7,8,9,10").add("Code", "IBEX,MIB").build())
				.execute().returnContent().asString();
		System.out.println(result);
		System.out.println("——————————————————————————————");
		
		Document document = DocumentHelper.parseText(result);
		Element root = document.getRootElement();//第一层
		System.out.println(((Node) root.elements().get(0)).getName());
		Element elem_DataResult = root.element("DataResult");//第二层
		@SuppressWarnings("unchecked")
		Iterator<Element> elem_Records = elem_DataResult.elementIterator("Record");//第三层
		Element elem_Record;
		while(elem_Records.hasNext()){
			elem_Record = elem_Records.next();
			@SuppressWarnings("unchecked")
			Iterator<Element> elem_Items = elem_Record.elementIterator("Item");
			while(elem_Items.hasNext()){
				Element elem_Item = elem_Items.next();
				if(elem_Item.attributeValue("Id").equals("5")&&elem_Item.attribute("Market")!=null){
					System.out.print("Market:"+elem_Item.attributeValue("Market"));
					System.out.print(",编号:"+elem_Item.elementText("Value"));
				}
				if(elem_Item.attributeValue("Id").equals("7"))
					System.out.print(",今开:"+elem_Item.elementText("Value"));
				if(elem_Item.attributeValue("Id").equals("8"))
					System.out.print(",最高:"+elem_Item.elementText("Value"));
				if(elem_Item.attributeValue("Id").equals("9"))
					System.out.print(",最低:"+elem_Item.elementText("Value"));
				if(elem_Item.attributeValue("Id").equals("10"))
					System.out.print(",最新价:"+elem_Item.elementText("Value"));
				if(elem_Item.attributeValue("Id").equals("6"))
					System.out.println(",昨收价:"+elem_Item.elementText("Value"));
			}
		}
	}
}
