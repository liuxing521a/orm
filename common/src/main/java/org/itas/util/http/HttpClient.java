package org.itas.util.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map.Entry;

/**
 * https请求
 * 
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2014年12月22日下午3:09:02
 */
public class HttpClient extends AbstractHttp {

	@Override
	protected String fetch(String url, String method) throws Exception {
		// 创建URL对象
		URL myURL = new URL(url);
		// 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
		HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
		conn.setRequestProperty("charset", "utf-8");

		conn.setConnectTimeout(getConnectTimeOut());
		conn.setReadTimeout(getReadTimeOut());

		// method
		conn.setRequestMethod(method);

		/* begin headers */
		for (Entry<String, String> head : getHeaders().entrySet()) {
			conn.addRequestProperty(head.getKey(), head.getValue());
		}/* end */

		conn.setDoOutput(true);
		conn.setDoInput(true);

		/* begin body */
		try (OutputStream os = conn.getOutputStream()) {
			os.write(getBodys());
			os.flush();
		}
		/* end */

		boolean isGzip = isGzip(conn);
		String charset = charEncoding(conn);
		try (InputStream is = conn.getInputStream()) {
			return streamToString(is, charset, isGzip);
		}
	}
}
