package com.sy.util.file.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @deccription TODO
 *
 * @author zhanbiane
 * 2018年5月11日
 */
public class CsvUtil {
	public static void main(String[] args) throws Exception {  
        File file = new File("e:\\tmp\\CsvUtil\\csv\\charm.csv");  
//        FileReader fReader = new FileReader(file);  
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");
        CSVReader csvReader = new CSVReader(isr);  
        String[] strs = csvReader.readNext();  
        if(strs != null && strs.length > 0){  
            for(String str : strs)  
                if(null != str && !str.equals(""))  
                    System.out.print(str + " , ");  
            System.out.println("\n---------------");  
        }  
        List<String[]> list = csvReader.readAll();  
        for(String[] ss : list){  
            for(String s : ss)  
                if(null != s && !s.equals(""))  
                    System.out.print(s + " , ");  
            System.out.println();  
        }  
        csvReader.close();  
    } 
}
