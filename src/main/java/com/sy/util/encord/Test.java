package com.sy.util.encord;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @deccription TODO
 *
 * @author zhanbiane
 * 2018年8月7日
 */
public class Test {

	public static void main(String[] args) throws UnsupportedEncodingException {
//		String chinese = "Google host 鎸佺画鏇存柊鍦板潃锛歨ttp://blog.my-eclipse.cn/host-google.html";//java内部编码
//		String gbkChinese = new String(chinese.getBytes("GBK"),"UTF-8");//转换成gbk编码
//		System.out.println(gbkChinese);//乱码
//		String unicodeChinese = new String(gbkChinese.getBytes("ISO-8859-1"),"GBK");//java内部编码
//		System.out.println(unicodeChinese);//中文
//		String utf8Chinese = new String(unicodeChinese.getBytes("UTF-8"),"ISO-8859-1");//utf--8编码
//		System.out.println(utf8Chinese);//乱码
//		unicodeChinese = new String(utf8Chinese.getBytes("ISO-8859-1"),"UTF-8");//java内部编码
//		System.out.println(unicodeChinese);//中文
		
//		String str ="{\"controls\":[{\"id\":\"_common_hidden_viewdata\",\"value\":\"{\"rowguid\":\"6cb42d62-d84e-45ae-9a21-f70f564ddb6b\"}\"}],\"custom\":{\"showToolbar\":true,\"strJson\":\"{\"title1\":\"\u9879\u76EE\",\"rowguid1\":\"d2706ec0-381c-4592-9655-58a9fdb6199a\",\"url1\":\"..\\/..\\/dataexchange\\/bjexprojectreg\\/bjexprojectdetail2?RowGuid=d2706ec0-381c-4592-9655-58a9fdb6199a\",\"isshow\":true,\"title19\":\"\u5408\u540C\u4E0E\u5C65\u7EA6\",\"rowguid19\":\"\",\"url19\":\"..\\/..\\/dataexchange\\/bjexcontractperformance\\/bjExContractPerformanceDetail?RowGuid=\",\"title2\":\"\u62DB\u6807\u9879\u76EE\",\"rowguid2\":\"91d1a48a-fc64-40f4-b4bd-3b5c54cb1c00\",\"url2\":\"..\\/..\\/dataexchange\\/bjextenderprojectreg\\/bjextenderprojectdetail2?RowGuid=91d1a48a-fc64-40f4-b4bd-3b5c54cb1c00\",\"title24\":\"\u62DB\u6807\u5F02\u5E38\u60C5\u51B5\u62A5\u544A\",\"rowguid24\":\"\",\"url24\":\"..\\/..\\/dataexchange\\/bjextenderabnormityreport\\/bjExTenderAbnormityReportDetail?RowGuid=\",\"title4\":\"\u62DB\u6807\u516C\u544A\u4E0E\u8D44\u683C\u9884\u5BA1\u516C\u544A\",\"rowguid4\":\"76cd58e6-d318-439f-b562-78c2e998f92c\",\"url4\":\"..\\/..\\/dataexchange\\/bjextenderquainqueryreg\\/bjextenderquainquerydetail2?RowGuid=76cd58e6-d318-439f-b562-78c2e998f92c\",\"title10\":\"\u5F00\u6807\u660E\u7EC6(\u8D27\u7269)\",\"rowguid10\":\"\",\"url10\":\"H4200002714023078002001\",\"title23\":\"\u8D44\u683C\u9884\u5BA1\\/\u8D44\u683C\u9884\u5BA1\u6F84\u6E05\",\"rowguid23\":\"\",\"url23\":\"..\\/..\\/dataexchange\\/bjexqualiinqueryclari\\/bjexqualiinqueryclaridetail?RowGuid=\",\"title6\":\"\u6295\u6807\u9080\u8BF7\u4E66\",\"rowguid6\":\"\",\"url6\":\"\",\"title8\":\"\u5F00\u6807\u8BB0\u5F55\",\"rowguid8\":\"011ddae8-add4-4169-b0ca-39eed0360759\",\"url8\":\"..\\/..\\/dataexchange\\/bjexbidopeningrecord\\/bjexbidopeningrecorddetail2?RowGuid=011ddae8-add4-4169-b0ca-39eed0360759\",\"title15\":\"\u4E2D\u6807\u5019\u9009\u4EBA\",\"rowguid15\":\"c06b828b-8b98-4d80-ac47-89b9e4db1926\",\"url15\":\"..\\/..\\/dataexchange\\/bjextendercandidate\\/bjextendercandidatelistall?sectionno=H4200002714023078002001\",\"title7\":\"\u53D8\u66F4\u516C\u544A\",\"rowguid7\":\"\",\"url7\":\"\",\"title17\":\"\u4E2D\u6807\u4EBA\",\"rowguid17\":\"\",\"url17\":\"\",\"title18\":\"\u4E2D\u6807\u7ED3\u679C\u516C\u544A\",\"rowguid18\":\"72c4f674-18f7-42f7-af5b-634ddfe606ae\",\"url18\":\"..\\/..\\/dataexchange\\/bjexwinresultanno\\/bjexwinresultannodetail2?RowGuid=72c4f674-18f7-42f7-af5b-634ddfe606ae\",\"title3\":\"\u6807\u6BB5\",\"rowguid3\":\"d939a58e-1530-4fc8-8f10-bcd089f379a0\",\"url3\":\"..\\/..\\/dataexchange\\/bjexsectionreg\\/bjexsectiondetail2?RowGuid=d939a58e-1530-4fc8-8f10-bcd089f379a0\",\"title11\":\"\u5F00\u6807\u660E\u7EC6(\u670D\u52A1)\",\"rowguid11\":\"\",\"url11\":\"H4200002714023078002001\",\"title20\":\"\u8D44\u683C\u9884\u5BA1\u516C\u544A\",\"rowguid20\":\"\",\"url20\":\"\",\"title5\":\"\u62DB\u6807\u6587\u4EF6\\/\u62DB\u6807\u6587\u4EF6\u6F84\u6E05\u4E0E\u4FEE\u6539\",\"rowguid5\":\"f85ebc76-d8fa-4933-b2e9-2508c9a4f56a\",\"url5\":\"..\\/..\\/dataexchange\\/bjextenderfileclarifymodireg\\/bjextenderfileclarimodidetail?RowGuid=f85ebc76-d8fa-4933-b2e9-2508c9a4f56a\",\"title16\":\"\u4E2D\u6807\u5019\u9009\u4EBA\u516C\u793A\",\"rowguid16\":\"3eda57b8-32bb-4a27-b061-bce04215ad5d\",\"url16\":\"..\\/..\\/dataexchange\\/bjextendercandidateanno\\/bjextendercandidateannodetail2?RowGuid=3eda57b8-32bb-4a27-b061-bce04215ad5d\",\"title22\":\"\u8D44\u683C\u9884\u5BA1\u7ED3\u679C\",\"rowguid22\":\"\",\"url22\":\"..\\/..\\/dataexchange\\/bjexqualiinqueryresult\\/bjExQualiInqueryResultDetail?RowGuid=\",\"title9\":\"\u5F00\u6807\u660E\u7EC6(\u5DE5\u7A0B)\",\"rowguid9\":\"\",\"url9\":\"H4200002714023078002001\"}\",\"huiYuanShowDel\":true,\"showAdd\":true,\"huiYuanShowAdd\":true,\"showTerminate\":false,\"showDel\":false,\"showReset\":false},\"status\":{\"code\":\"200\",\"text\":\"\",\"url\":\"\"}}";
//		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    
//	    Matcher matcher = pattern.matcher(str);
//	    char ch;
//	    while (matcher.find()) {
//	        ch = (char) Integer.parseInt(matcher.group(2), 16);
//	        str = str.replace(matcher.group(1), ch + "");    
//	    }
//        System.out.println(str);
		
		String a = URLDecoder.decode("%D4%C6%C4%CF", "GBK");
		System.out.println(a);
		a = new String(a.getBytes("GBK"),"UTF-8");//java内部编码
		System.out.println(a);//中文
		String b = URLEncoder.encode("▓~上", "gbk");
		System.out.println(b);
		
	}
}
