package com.sy.util.http.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.sy.util.file.GetTypeByHead;

/**
 * @deccription 文件下载
 * 
 *	使用commons-io-2.6.jar
 *
 * @author zhanbiane
 * 2018年6月23日
 */
public class FileDownLoad {

	public static void main(String[] args) throws IOException {
//		String url = "http://nszx.hb-n-tax.gov.cn/xxgk/jcms_files/jcms1/web2/site/zfxxgk/download/downfile.jsp?filename=180604163253217.xlsx";
		String url = "http://www.xmzfcg.gov.cn/filemanager/download/download.jsp?id=4877f224cc21ace3bbbecae1a7a1641d&sn=35156";
		java.net.URL l_url = new java.net.URL(url);
		System.out.println(l_url.getFile());
		File file=new File("F:/tmp/abcs.pdf");
		FileUtils.copyURLToFile(l_url, file);
		String type = GetTypeByHead.getFileType(file.getPath());
		System.out.println(type);
		
		

BufferedInputStream bis = null;
HttpURLConnection urlconnection = null;
//URL url = null;         
//        url = new URL(strUrl);
    urlconnection = (HttpURLConnection) l_url.openConnection();
    urlconnection.connect();
bis = new BufferedInputStream(urlconnection.getInputStream());
    System.out.println("file type:"+HttpURLConnection.guessContentTypeFromStream(bis));
	}
}
