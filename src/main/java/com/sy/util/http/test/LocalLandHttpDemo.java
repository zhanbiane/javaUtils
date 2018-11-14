package com.sy.util.http.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class LocalLandHttpDemo {
	
	private String _viewsState = "%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%2BgY8cCBhbGlnbj0iY2VudGVyIj48c3BhbiBzdHlsZT0iZm9udC1zaXplOiB4LXNtYWxsIj4mbmJzcDs8YnIgLz4NCiZuYnNwOzxhIHRhcmdldD0iX3NlbGYiIGhyZWY9Imh0dHA6Ly93d3cubGFuZGNoaW5hLmNvbS8iPjxpbWcgYm9yZGVyPSIwIiBhbHQ9IiIgd2lkdGg9IjI2MCIgaGVpZ2h0PSI2MSIgc3JjPSIvVXNlci9kZWZhdWx0L1VwbG9hZC9mY2svaW1hZ2UvdGRzY3dfbG9nZS5wbmciIC8%2BPC9hPiZuYnNwOzxiciAvPg0KJm5ic3A7PHNwYW4gc3R5bGU9ImNvbG9yOiAjZmZmZmZmIj5Db3B5cmlnaHQgMjAwOC0yMDE4IERSQ25ldC4gQWxsIFJpZ2h0cyBSZXNlcnZlZCZuYnNwOyZuYnNwOyZuYnNwOyA8c2NyaXB0IHR5cGU9InRleHQvamF2YXNjcmlwdCI%2BDQp2YXIgX2JkaG1Qcm90b2NvbCA9ICgoImh0dHBzOiIgPT0gZG9jdW1lbnQubG9jYXRpb24ucHJvdG9jb2wpID8gIiBodHRwczovLyIgOiAiIGh0dHA6Ly8iKTsNCmRvY3VtZW50LndyaXRlKHVuZXNjYXBlKCIlM0NzY3JpcHQgc3JjPSciICsgX2JkaG1Qcm90b2NvbCArICJobS5iYWlkdS5jb20vaC5qcyUzRjgzODUzODU5YzcyNDdjNWIwM2I1Mjc4OTQ2MjJkM2ZhJyB0eXBlPSd0ZXh0L2phdmFzY3JpcHQnJTNFJTNDL3NjcmlwdCUzRSIpKTsNCjwvc2NyaXB0PiZuYnNwOzxiciAvPg0K54mI5p2D5omA5pyJJm5ic3A7IOS4reWbveWcn%2BWcsOW4guWcuue9kSZuYnNwOyZuYnNwO%2BaKgOacr%2BaUr%2BaMgTrmtZnmsZ%2Foh7vlloTnp5HmioDogqHku73mnInpmZDlhazlj7gmbmJzcDs8YnIgLz4NCuWkh%2BahiOWPtzog5LqsSUNQ5aSHMDkwNzQ5OTLlj7cg5Lqs5YWs572R5a6J5aSHMTEwMTAyMDAwNjY2KDIpJm5ic3A7PGJyIC8%2BDQo8L3NwYW4%2BJm5ic3A7Jm5ic3A7Jm5ic3A7PGJyIC8%2BDQombmJzcDs8L3NwYW4%2BPC9wPh8BBWRCQUNLR1JPVU5ELUlNQUdFOnVybChodHRwOi8vd3d3LmxhbmRjaGluYS5jb20vVXNlci9kZWZhdWx0L1VwbG9hZC9zeXNGcmFtZUltZy94X3Rkc2N3MjAxM195d18xLmpwZyk7ZGQx32XoN9wPjqaN6P3TeGtXIrkZmLspKSpi6rwfeafDGw%3D%3D";
	private String _eventvalidation = "%2FwEWAgKStN2aBwLN3cj%2FBAWSI1H2hkr64yC0%2FHTpN9gOp1cYZSRKApIEJS4YxEuH";
	
	public String urlDecode(String input) {
		String output = input;
		try {
		 output = URLDecoder.decode(input, "gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}

	public void sendRequestForList() {

		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// 创建httppost
		HttpPost httppost = new HttpPost("http://www.landchina.com/default.aspx?ComName=default&tabid=264");
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("__VIEWSTATE", this.urlDecode(this._viewsState)));
		formparams.add(new BasicNameValuePair("__EVENTVALIDATION", this.urlDecode(this._eventvalidation)));
		formparams.add(new BasicNameValuePair("hidComName", "default"));
		formparams.add(new BasicNameValuePair("mainModuleContainer_492_1114_495_TabMenu1_selected_index", "0"));
		formparams.add(new BasicNameValuePair("top1_QueryConditionItem", "e1098f89-81bb-4e36-bfb7-be69c34d8b4b"));
		formparams.add(new BasicNameValuePair("top1_QuerySubmitConditionData", this.urlDecode("8f464b85-2802-458a-8ee6-66ce6186d803%3A1101%A8%88%7E%B1%B1%BE%A9%CA%D0%CA%D0%CF%BD%C7%F8%7Ce1098f89-81bb-4e36-bfb7-be69c34d8b4b%3A%7E")));
		formparams.add(new BasicNameValuePair("top1_QuerySubmitOrderData", ""));
		formparams.add(new BasicNameValuePair("top1_RowButtonActionControl", ""));
		formparams.add(new BasicNameValuePair("top1_QuerySubmitPagerData", "1"));
		formparams.add(new BasicNameValuePair("top1_QuerySubmitSortData", ""));
		formparams.add(new BasicNameValuePair("top2_QuerySubmitConditionData", ""));
		formparams.add(new BasicNameValuePair("top2_QuerySubmitOrderData", ""));
		formparams.add(new BasicNameValuePair("top2_RowButtonActionControl", ""));
		formparams.add(new BasicNameValuePair("top2_QuerySubmitPagerData", "1"));
		formparams.add(new BasicNameValuePair("top2_QuerySubmitSortData", ""));
		formparams.add(new BasicNameValuePair("top3_QuerySubmitConditionData", ""));
		formparams.add(new BasicNameValuePair("top3_QuerySubmitOrderData", ""));
		formparams.add(new BasicNameValuePair("top3_RowButtonActionControl", ""));
		formparams.add(new BasicNameValuePair("top3_QuerySubmitPagerData", "1"));
		formparams.add(new BasicNameValuePair("top3_QuerySubmitSortData", ""));
		
		
		

		UrlEncodedFormEntity uefEntity;
		CookieStore cookieStore = new BasicCookieStore();
		httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "gb2312");
			httppost.setEntity(uefEntity);

			httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//			httppost.setHeader("X-Requested-With", "XMLHttpRequest");
//			httppost.setHeader("X-Requested-With", "XMLHttpRequest");
//			httppost.setHeader("X-Requested-With", "XMLHttpRequest");
//			httppost.setHeader("X-Requested-With", "XMLHttpRequest");
//			httppost.setHeader("X-Requested-With", "XMLHttpRequest");
//			httppost.setHeader("X-Requested-With", "XMLHttpRequest");
//			httppost.setHeader("X-Requested-With", "XMLHttpRequest");
//			httppost.setHeader("X-Requested-With", "XMLHttpRequest");
//			httppost.setHeader("X-Requested-With", "XMLHttpRequest");
//			httppost.setHeader("X-Requested-With", "XMLHttpRequest");
			

			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);

			List<Cookie> cookies = cookieStore.getCookies();
			for (int i = 0; i < cookies.size(); i++) {
				System.out.println("set cookie:" + cookies.get(i).getName() + "|" + cookies.get(i).getValue());
			}

			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					System.out.println("--------------------------------------");
					System.out.println("Response content: " + EntityUtils.toString(entity, "gb2312"));
					System.out.println("--------------------------------------");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return;

	}
	
	public static void main(String[] args) {
		
		LocalLandHttpDemo localLandHttpDemo=new LocalLandHttpDemo();
		localLandHttpDemo.sendRequestForList();
		
	}

}
