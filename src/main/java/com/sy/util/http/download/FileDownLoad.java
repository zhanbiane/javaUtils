package com.sy.util.http.download;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

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
		java.net.URL l_url = new java.net.URL("http://nszx.hb-n-tax.gov.cn/xxgk/jcms_files/jcms1/web2/site/zfxxgk/download/downfile.jsp?filename=180604163253217.xlsx");
		File file=new File("F:/tmp/a.xlsx");
		FileUtils.copyURLToFile(l_url, file);
	}
}
