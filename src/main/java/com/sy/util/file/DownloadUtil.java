package com.sy.util.file;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @deccription 文件下载
 *
 * @author zhanbiane
 * 2018年7月3日
 */
public class DownloadUtil {

	public static void downImg(URL imgUrl,String LocalFilePath) {

        try {
            DataInputStream dataInputStream = new DataInputStream(imgUrl.openStream());

            FileOutputStream fileOutputStream = new FileOutputStream(new File(LocalFilePath));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context=output.toByteArray();
            fileOutputStream.write(context);
            
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
}
