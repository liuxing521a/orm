package org.itas.util.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * https请求
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2014年12月22日下午3:09:02
 */
public class HttpsClient extends AbstractHttp {

	@Override
	protected String fetch(String url, String method) throws Exception {
		 TrustManager[] tm = { new ItasX509TrustManager() }; 
         SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE"); 
         sslContext.init(null, tm, new java.security.SecureRandom()); 
         // 从上述SSLContext对象中得到SSLSocketFactory对象 
         SSLSocketFactory ssf = sslContext.getSocketFactory(); 
         // 创建URL对象 
         URL myURL = new URL(url); 
         // 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象 
         HttpsURLConnection conn = (HttpsURLConnection) myURL.openConnection(); 
         conn.setSSLSocketFactory(ssf); 
         // 取得该连接的输入流，以读取响应内容 
         

         conn.setConnectTimeout(getConnectTimeOut());
         conn.setReadTimeout(getReadTimeOut());
 
        // method
         conn.setRequestMethod(method);
 
         /*begin headers*/
         for (Entry<String, String> head : getHeaders().entrySet()) {
        	 conn.addRequestProperty(head.getKey(), head.getValue());
         }/*end*/
 
        conn.setDoOutput(true);
        conn.setDoInput(true);
        
        /*begin body*/
        try (OutputStream os = conn.getOutputStream()) {
        	os.write(getBodys());
        	os.flush();
        }
        /*end*/
        
        boolean isGzip = isGzip(conn);
        String charset = charEncoding(conn);
        try (InputStream is = conn.getInputStream()) {
        	return streamToString(is, charset, isGzip);
        }
	}
	

	private class ItasX509TrustManager implements X509TrustManager {

		ItasX509TrustManager() throws Exception {
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {

		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}
}
