package com.sy.util.img;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.jsoup.Connection.Base;


/**  
 * @Description: TODO(不同地址下得到的图片转换成base64)
 * @author zhanbian 
 * @date 2016年4月18日 下午2:55:30
 */ 
public class Base64Img {

//	public static void main(String[] args) throws Exception {
//		String imgUrl="http://www.ctps.cn/PhotoNet/Profiles2011/20110503/20115302844162622467.jpg";
//		String path="C:/Users/阳/Desktop/12.jpg";
//		String savaPath1="C:/Users/阳/Desktop/待完成/";
//		String savaPath2="C:/Users/阳/Desktop/待完成/";
//		String savaPath3="C:/Users/阳/Desktop/待完成/";
//		File file=new File(path);
//		File saveFile1=new File(savaPath1);
//		File saveFile2=new File(savaPath2);
//		File saveFile3=new File(savaPath3);
//		if(!saveFile1.exists()){saveFile1.mkdirs();}
//		if(!saveFile2.exists()){saveFile2.mkdirs();}
//		if(!saveFile3.exists()){saveFile3.mkdirs();}
//		
//		
//		//网络
//		URL url = new URL(imgUrl);
//		String str = imgToBase64(url);
//		base64ToImg(str, savaPath1+"1.jpg");
//	
//		
//		//本地1
//		String str2 = imgToBase64(file);
//		base64ToImg(str2, savaPath2+"2.jpg");
//		
//		//本地2,这个方法得到的图片生成的大小大于本地一
//		String str3= imgToBase64_2(file);
//		base64ToImg(str3, savaPath2+"3.jpg");		
//		
//	}
	
	
public static void main(String[] args) throws IOException {
	String imgStr = "";
	BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Public\\Nwt\\cache\\recv\\徐晗/TEST.txt"));
	String str = null ;
	StringBuffer sb = new StringBuffer();
	 while ((str = br.readLine()) != null) 
     {
           sb.append(str);
     }
	base64ToImg(sb.toString(), "D:\\work\\tmp\\img/4.jpg");
//	String str = imgToBase64(new File("D:\\work\\tmp\\img/1.jpg"));
//	String str = imgToBase64_2(new File("D:\\work\\tmp\\img/1.jpg"));
//	System.out.println(str);
}
	
	/**
	 * 网络获取到的图片进行base64编码
	 * @param imgURL
	 * @return
	 */
	public static String imgToBase64(URL imgURL){
		ByteArrayOutputStream baos=null;
		try {
			BufferedImage bufferedImg = ImageIO.read(imgURL);
			baos=new ByteArrayOutputStream();
			ImageIO.write(bufferedImg, "jpg", baos);
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return encryptBASE64(baos.toByteArray());
	}
	
	/**
	 * 本地文件中的图片进行base64编码
	 * @param file
	 * @return
	 */
	public static String imgToBase64(File file){
		ByteArrayOutputStream baos=null;
		try {
			BufferedImage bufferedImg = ImageIO.read(file);
			baos=new ByteArrayOutputStream();
			ImageIO.write(bufferedImg, "jpg", baos);
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		return encryptBASE64(baos.toByteArray());
	}	
	
	
	public static String imgToBase64_2(File file){
		InputStream is=null;
		byte[] b=null;
		if(file.exists()){
			try {
				is=new FileInputStream(file);
				b= new byte[is.available()];
				is.read(b);
				is.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return  encryptBASE64(b);
	}
	
	
	public static void base64ToImg(String baseImg,String file){
		FileOutputStream fos=null;
		try {
			fos =  new FileOutputStream(file);
			byte[] b = dencordBASE64(baseImg);
			for(int i=0;i<b.length;i++){
				if(b[i]<0){
					b[i]+=256;
				}
			}
			fos.write(b);
			fos.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static byte[] dencordBASE64(String baseImg) {
		return Base64.getDecoder().decode(baseImg);
	}
	
	private static String encryptBASE64(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}
	
}
