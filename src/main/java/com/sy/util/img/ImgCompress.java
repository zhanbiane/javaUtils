package com.sy.util.img;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;


/**  
 * @Description: TODO(图片压缩)
 * @author zhanbian 
 * @date 2016年4月18日 下午3:28:12
 */ 
public class ImgCompress {

	private static Image img; //原图
	private static int imgHeight;//原图的高度
	private static int imgWidth;//原图的宽度
	
	public static void main(String[] args) {
		String fileurl = "C:/Users/阳/Desktop/图片/preview.jpg";
		String outfile = "C:/Users/阳/Desktop/图片/2.jpg";
		
		try {
//			imgCompress(fileurl, outfile, 200, 200);
			byte[] b = imgToBase64(new File(fileurl));
			GetImageByBytes(b, outfile, 200, 200);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static byte[] imgToBase64(File file){
		ByteArrayOutputStream baos=null;
		try {
			BufferedImage bufferedImg = ImageIO.read(file);
			baos=new ByteArrayOutputStream();
			ImageIO.write(bufferedImg, "jpg", baos);
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return baos.toByteArray();
	}	
	
	
	public static void GetImageByBytes(byte[] bytes,String outfile,int w,int h) throws IOException{
		ByteArrayInputStream is = new ByteArrayInputStream(bytes); 
		img = ImageIO.read(is);
		imgHeight = img.getHeight(null);
		imgWidth = img.getWidth(null);
		System.out.println("图片实际高度"+imgHeight);
		System.out.println("图片实际宽度"+imgWidth);
		compressBase(outfile,w,h);
    }
	
	
	public static void imgCompress(String fileurl,String outfile,int w,int h) throws IOException {
		File file =  new File(fileurl);
		if(!file.exists())
			return;
		img = ImageIO.read(file);
		imgHeight = img.getHeight(null);
		imgWidth = img.getWidth(null);
		System.out.println("图片实际高度"+imgHeight);
		System.out.println("图片实际宽度"+imgWidth);
		compressBase(outfile,w,h);
	}
	
	/**
	 * 判断压缩基准，宽/高
	 * @param w
	 * @param h
	 * @throws IOException 
	 */
	private static void compressBase(String outfile,int w,int h) throws IOException{
		if((double)imgHeight/imgWidth>(double)h/w){//以高为基准，等比例缩放
			w = imgWidth*h/imgHeight;
			toCompress(outfile, w, h);
		}else{//以宽为基准
			h = imgHeight*w/imgWidth;
			toCompress(outfile, w, h);
		}
	}
	
	private static void toCompress(String outfile,int w,int h) throws IOException{
		System.out.println("图片现在高度"+h);
		System.out.println("图片现在宽度"+w);
		
		BufferedImage bufferedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//		bufferedImg.getGraphics().drawImage(img, 0, 0, w, h, null);
		bufferedImg.getGraphics().drawImage(img.getScaledInstance(w, h,  Image.SCALE_SMOOTH), 0,0,null);//图片平滑度提升，生成速度稍慢
		FileOutputStream fos =new FileOutputStream(outfile);
		ImageIO.write(bufferedImg, "jpg", fos);
		fos.close();
	}
	
	
	//  http://www.shaoqun.com/a/143860.aspx
	//  生成高质量图片的差异
	
}
