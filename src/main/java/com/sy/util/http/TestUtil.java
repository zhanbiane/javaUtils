package com.sy.util.http;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sy.util.http.HttpClientUtil.httpRequestMethod;

/**
 * @deccription TODO
 *
 * @author zhanbiane
 * 2018年8月23日
 */
public class TestUtil {
	private static Logger logger = LoggerFactory.getLogger(TestUtil.class);

	
    /** 请求超时设置*/
    private Builder requestConfig = RequestConfig.custom()
    		.setConnectTimeout(15000).setConnectionRequestTimeout(7500).setSocketTimeout(15000);

    /** 编码设置*/
    private static final String encoding = "UTF-8";

    /** 请求方式枚举*/
    public static enum httpRequestMethod {
        GET, POST;
    };
    
    /** 请求链接 */
    private String url;
    /** 默认请求方式 */
    private httpRequestMethod method = httpRequestMethod.GET;
    /** 参数 */
    private HttpRequestBody requestBody;
    
    
    private static TestUtil instance = null;
    
    public static TestUtil create(String url) {
    	if (instance == null) {
			synchronized (TestUtil.class) {
				if (instance == null) {
					instance = new TestUtil(url);
				}
			}
		}
		return instance;
    }
    
	public TestUtil(String url) {
		this.url = url;
	}

	/**
     * 请求方式，get/post
     * @param method
     * @return
     */
    public TestUtil setMethod(httpRequestMethod method) {
    	this.method = method;
    	return this;
    }
    
    /**
     * 设置请求参数
     * 目前只对form做处理
     * @param requestBody
     * @return
     */
    public TestUtil setRequestBody(HttpRequestBody requestBody) {
    	this.requestBody = requestBody;
    	return this;
    }
    
    /**
     * 设置代理
     * @param ip
     * @param port
     * @param http/https
     * @return
     */
    public TestUtil setProxy(String ip,int port,String protocol) {
    	HttpHost proxy = new HttpHost(ip, port, protocol);
    	requestConfig = requestConfig.setProxy(proxy);
    	return this;
    }
    
    /**
     * 执行请求
     * @throws UnsupportedEncodingException 
     */
    public void execute() {
    	CloseableHttpResponse response = null;
		if (httpRequestMethod.GET == method) {
			logger.info("\n请求链接：{}\n请求方式：{}",url,method);
//            response = sendGetRequest(url, params);
        } else if (httpRequestMethod.POST == method) {
            try {
				response = sendPostRequest();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    public static void main(String[] args) {
    	Map<String, Object> params = new HashMap<>();
    	params.put("a", "a");
    	params.put("b", "b");
    	
		TestUtil.create("http://www.hfcredit.gov.cn/credit-website/publicity/doublePublicity/getXZXKPageInfo.do")
		.setMethod(httpRequestMethod.POST)
		.setRequestBody(HttpRequestBody.form(params, encoding))
		.setProxy("127.0.0.1", 8888, "http")
		.execute();
	}
    
    /**
     * post请求
     * @return
     */
    private CloseableHttpResponse sendPostRequest() {
    	HttpPost httpPost = new HttpPost(this.url);
    	httpPost.setConfig(this.requestConfig.build());
    	String bodyStr = null;
    	String encode = null;
    	if(null != this.requestBody) {
    		encode = this.requestBody.getEncoding();
    		try {
				bodyStr = new String(this.requestBody.getBody(), encode);
				logger.info("\n请求链接：{}\n请求方式：{}\n请求参数：{}\n编码：{}",url,method,bodyStr,encode);
				List<NameValuePair> nvpList = URLEncodedUtils.parse(bodyStr, CharsetUtils.get(encode));
				httpPost.setEntity(new UrlEncodedFormEntity(nvpList, encode));
				return getHttpClient().execute(httpPost);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
		return null;
    }
   
    /**
     * 获取http连接
     * 
     * @return
     * @throws Exception
     * @author dubl
     */
    public static CloseableHttpClient getHttpClient(){
        CloseableHttpClient httpClient = null;
        try {
            // 创建连接
            httpClient = HttpClients.createDefault();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("创建http连接出错。" + e.getMessage());
        }
        return httpClient;
    }
}

class HttpRequestBody implements Serializable {
	private static final long serialVersionUID = -179188964646570175L;

	public static abstract class ContentType {

        public static final String JSON = "application/json";

        public static final String XML = "text/xml";

        public static final String FORM = "application/x-www-form-urlencoded";

        public static final String MULTIPART = "multipart/form-data";
    }

    private byte[] body;

    private String contentType;

    private String encoding;

    public HttpRequestBody() {
    }

    public HttpRequestBody(byte[] body, String contentType, String encoding) {
        this.body = body;
        this.contentType = contentType;
        this.encoding = encoding;
    }

    public String getContentType() {
        return contentType;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public static HttpRequestBody json(String json, String encoding) {
        try {
            return new HttpRequestBody(json.getBytes(encoding), ContentType.JSON, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("illegal encoding " + encoding, e);
        }
    }

    public static HttpRequestBody xml(String xml, String encoding) {
        try {
            return new HttpRequestBody(xml.getBytes(encoding), ContentType.XML, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("illegal encoding " + encoding, e);
        }
    }

    public static HttpRequestBody custom(byte[] body, String contentType, String encoding) {
        return new HttpRequestBody(body, contentType, encoding);
    }

    public static HttpRequestBody form(Map<String,Object> params, String encoding){
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }
        try {
            return new HttpRequestBody(URLEncodedUtils.format(nameValuePairs, encoding).getBytes(encoding), ContentType.FORM, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("illegal encoding " + encoding, e);
        }
    }

    public byte[] getBody() {
        return body;
    }
}


