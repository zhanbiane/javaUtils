package com.sy.util.img;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;


/**  
 * @Description: TODO(不同地址下得到的图片转换成base64)
 * @author zhanbian 
 * @date 2016年4月18日 下午2:55:30
 */ 
public class Base64Img {

	public static void main(String[] args) throws Exception {
		String imgUrl="http://www.ctps.cn/PhotoNet/Profiles2011/20110503/20115302844162622467.jpg";
		String path="C:/Users/阳/Desktop/12.jpg";
		String savaPath1="C:/Users/阳/Desktop/待完成/";
		String savaPath2="C:/Users/阳/Desktop/待完成/";
		String savaPath3="C:/Users/阳/Desktop/待完成/";
		File file=new File(path);
		File saveFile1=new File(savaPath1);
		File saveFile2=new File(savaPath2);
		File saveFile3=new File(savaPath3);
		if(!saveFile1.exists()){saveFile1.mkdirs();}
		if(!saveFile2.exists()){saveFile2.mkdirs();}
		if(!saveFile3.exists()){saveFile3.mkdirs();}
		
		
		//网络
		URL url = new URL(imgUrl);
		String str = imgToBase64(url);
		base64ToImg(str, savaPath1+"1.jpg");
	
		
		//本地1
		String str2 = imgToBase64(file);
		base64ToImg(str2, savaPath2+"2.jpg");
		
		//本地2,这个方法得到的图片生成的大小大于本地一
		String str3= imgToBase64_2(file);
		base64ToImg(str3, savaPath2+"3.jpg");		
		
	}
	
	/**
	 * 网络获取到的图片进行base64编码
	 * @param imgURL
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String imgToBase64(URL imgURL){
		ByteArrayOutputStream baos=null;
		try {
			BufferedImage bufferedImg = ImageIO.read(imgURL);
			baos=new ByteArrayOutputStream();
			ImageIO.write(bufferedImg, "jpg", baos);
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		sun.misc.BASE64Encoder encode=new sun.misc.BASE64Encoder();
		return encode.encode(baos.toByteArray());
	}
	
	/**
	 * 本地文件中的图片进行base64编码
	 * @param file
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String imgToBase64(File file){
		ByteArrayOutputStream baos=null;
		try {
			BufferedImage bufferedImg = ImageIO.read(file);
			baos=new ByteArrayOutputStream();
			ImageIO.write(bufferedImg, "jpg", baos);
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		sun.misc.BASE64Encoder encode=new sun.misc.BASE64Encoder();
		return encode.encode(baos.toByteArray());
	}	
	
	
	@SuppressWarnings("restriction")
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
		sun.misc.BASE64Encoder encode=new sun.misc.BASE64Encoder();
		return encode.encode(b);
	}
	
	
	@SuppressWarnings("restriction")
	public static void base64ToImg(String baseImg,String file){
		FileOutputStream fos=null;
		try {
			fos =  new FileOutputStream(file);
			sun.misc.BASE64Decoder decode = new sun.misc.BASE64Decoder();
			byte[] b = decode.decodeBuffer(baseImg);
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
}
