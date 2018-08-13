package com.sy.util.file.excel;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * @deccription TODO
 *
 * @author zhanbiane
 * 2018年7月3日
 */
public class JXLExcelIn {

	public static void main(String args[]){
        Workbook wb = null;
        try {
            //1.创建工作簿
            wb = Workbook.getWorkbook(new File("D:\\work\\建行行政处罚项目\\税务\\安徽\\anhui/1801111551209957753.xls"));
            //2.获取sheet
            Sheet sheet = wb.getSheet(0);
            //3.简单的获取并打印Excel数据
            //sheet.getRows() 获取总的行数  sheet.getColumns() 获取一行的列数
            for(int i = 0;i<sheet.getRows();i++){
                for(int j=0;j<sheet.getColumns();j++){
                    Cell cell = sheet.getCell(j,i);//获取每一个单元格，参数是行和列坐标
                    System.out.print(cell.getContents()+"  ");
                }
                System.out.println();//隔行
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(wb!=null){
                wb.close();
            }
        }
    }

}
