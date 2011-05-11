package com.ppstream.mt.utils;

import java.io.IOException;
import java.util.List;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * httpclient-4.1.1
 * 
 * @author liupeng
 * 
 */
public class HttpClientUtils {

	private static HttpClientUtils instance = null;

	public static HttpClientUtils getInstance() {
		if (instance == null) {
			instance = new HttpClientUtils();
		}
		return instance;
	}

	private static final String CHARSET_UTF8 = "UTF-8";
	
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	// 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复
	private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
		// 自定义的恢复策略
		public boolean retryRequest(IOException exception, int executionCount,
				HttpContext context) {
			// 设置恢复策略，在发生异常时候将自动重试3次
			if (executionCount >= 3) {
				// Do not retry if over max retry count
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				// Retry if the server dropped connection on us
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				// Do not retry on SSL handshake exception
				return false;
			}
			HttpRequest request = (HttpRequest) context
					.getAttribute(ExecutionContext.HTTP_REQUEST);
			boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
			if (!idempotent) {
				// Retry if the request is considered idempotent
				return true;
			}
			return false;
		}
	};

	/**
	 * 模拟post请求
	 * 
	 * @param url
	 * @param nvps
	 * @return
	 */
	@SuppressWarnings("finally")
	public String post(String url, List<NameValuePair> nvps) {
		HttpResponse response = null;
		String result = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			long time1 = System.currentTimeMillis();
			response = httpclient.execute(httpost);
			httpost.abort();
			long time2 = System.currentTimeMillis();
			System.out.println("post time = " + (time2 - time1)); // 耗时
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
			try {
				HttpEntity resEntity = response.getEntity();
			    byte[] bytes;
				bytes = EntityUtils.toByteArray(resEntity);
			    result = new String(bytes, "UTF-8");
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
	}

	/**
	 * 获取DefaultHttpClient实例
	 * 
	 * @param charset
	 *            参数编码集, 可空
	 * @return DefaultHttpClient 对象
	 */
	private static DefaultHttpClient getDefaultHttpClient(final String charset) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		// 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
		httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
		httpclient.getParams().setParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		httpclient.getParams().setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET,
				charset == null ? CHARSET_UTF8 : charset);
		httpclient.setHttpRequestRetryHandler(requestRetryHandler);
		return httpclient;
	}

	/**
	 * 获取URL内容
	 * cookie暂不考虑
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String getContentByUrl(String url) {
		HttpClient httpclient = getDefaultHttpClient(null);

		if (url == null || !url.startsWith("http://"))
			url = "http://" + url;
		url = url.replaceAll(" ", "%20");// 某些url里含有空格
		String result = "";
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("User-Agent","	Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			
			HttpResponse response = httpclient.execute(httpget);
			
		    int statusCode = response.getStatusLine().getStatusCode();
		    if ((statusCode == HttpStatus.SC_MOVED_PERMANENTLY) || (statusCode == HttpStatus.SC_MOVED_TEMPORARILY) ||
	                (statusCode == HttpStatus.SC_SEE_OTHER) || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
	            // 此处重定向处理  此处还未验证
	            String newUri = response.getLastHeader("Location").getValue();
	            httpclient = new DefaultHttpClient();
	            httpget = new HttpGet(newUri);
	            response = httpclient.execute(httpget);
	        }
		    
		    HttpEntity entity = response.getEntity();
		    
		    byte[] bytes = EntityUtils.toByteArray(entity);
		    result = new String(bytes, CHARSET_UTF8);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}finally{
			httpclient.getConnectionManager().shutdown();
			return result;
		}
	}

}
