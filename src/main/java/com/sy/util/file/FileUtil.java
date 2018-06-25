package com.sy.util.file;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @deccription 文件读写
 *
 * @author zhanbiane
 * 2018年6月11日
 */
public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static void main(String[] args) {
		File file = new File("F:/tmp/aa");
		delDir(file);
		System.out.println();
	}
	
	
	
	
	//创建文件夹
	public static boolean mkdirs(File path) {
		if(!path.exists()||!path.isDirectory()) {
			return path.mkdirs();
		}
		return true;
	}
	//创建文件及文件夹
	public static boolean createFile(File file) {
		try {
			File parent = file.getParentFile();
			mkdirs(parent);
			return file.createNewFile();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return false;
	}
	//删除文件
	public static boolean delFile(File file) {
		if(file.exists()) {
			return file.delete();
		}
		return true;
	}
	//删除目录
	public static void delDir(File dir) {
		if(dir.exists()) {
			int i = 0;
			File[] files = dir.listFiles();
			if(files.length == 0) {
				dir.delete();
			}
			for(File file:files) {
				if(file.isFile()) {
					file.delete();
					i++;
				}else {
					delDir(file);
				}
			}
			logger.info("删除文件{}个",i);
		}
	}
}
