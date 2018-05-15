package com.sy.util.file.jsoup;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest {

	public static void main(String[] args) {
		String path = "E:/表/表结构";
		File dir = new File(path);
		if(dir.exists()&&!dir.isFile()){
			File[] files = dir.listFiles();
			System.out.println(files.length);
			int count = 0;
			for(File file:files){
				String filename = file.getName();
				String pro = filename.substring(filename.lastIndexOf(".")+1);
				if(Objects.equals(pro.toLowerCase(),"html")){
					try {
						Document dom = Jsoup.parse(file, "GBK");
						Elements tables = dom.getElementsByTag("table");
						if(tables.size()==2){
							count++;
							Element table = tables.get(0);
							Element caption = table.getElementsByTag("caption").get(0);
							String titleAll = caption.text();
							String title = titleAll.substring(0,titleAll.length()-5).trim();//标题
							System.out.println("标题是："+title);
							Elements trs = table.getElementsByTag("tr");
							for(int i=1;i<trs.size();i++){
								Elements tds = trs.get(i).getElementsByTag("td");
								if(tds.size()==9){
									tds.get(0).text().trim();//序号
									tds.get(1).text().trim();//列名
									tds.get(2).text().trim();//数据类型
									for(Element td:tds){
										System.out.print(td.text().trim()+",");
									}
								}else{
									System.out.println("不同的表格。。");
								}
							}
							
							
							
							
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					System.out.println("排除:"+filename);
				}
				
			}
			System.out.println("操作的文件总数量："+count);
		}
		
	}
}
