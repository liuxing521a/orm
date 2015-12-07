package org.itas.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.itas.util.Utils.Objects;

/**
 * http一些基本的方法
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2014年12月22日下午2:00:24
 */
abstract class AbstractHttp {
	
	 /**
     * 字符集编码
     */
    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:\"|')?([^\\s,;\"']*)");
    
    /**
     * 读超时时间
     */
    private static final int READ_TIME_OUT = 15*1000;

    /**
     * 连接超时时间
     */
    private static final int CONNECTION_TIME_OUT = 15*1000;
    
    /**
     * 编码
     */
    private static final String CHARSET = "UTF-8";
    
    /**
     * http头参数
     */
    private Map<String, String> headers;
    
    /**
     * http 请求参数
     */
    private Map<String, String> params = new LinkedHashMap<String, String>();
    
    /**
     * http 请求内容
     */
    private String body;
    
    /**
     * 读超时
     */
    private int readTimeOut = READ_TIME_OUT;
    
    /**
     * 连接超时
     */
    private int connectTimeOut = CONNECTION_TIME_OUT;
    
    /**
     * 编码
     */
    private String charEncoding = CHARSET;
    
    
	/**
     * 发送http请求
     * @param url  http地址
     * @param method http请求方法 GET|POST
     * @return
     */
    protected abstract String fetch(String url, String method) throws Exception;

    /**
     * http GET请求
     * @param url  http地址
     * @returnresponse
     * @throws IOException
     */
    public String get(String url) throws Exception {
    	return fetch(getUrlWithParams(url, params), "GET");
    }
   
    /**
     * 发送POST请求
     * @param url 请求地址
     * @return response
     * @throws IOException
     */
    public String post(String url) throws Exception {
        return fetch(getUrlWithParams(url, params), "POST");
    }
 
    public Map<String, String> getHeaders() {
    	if (Objects.isNull(headers)) {
    		return Collections.emptyMap();
    	}
    	
		return headers;
	}

    public void addHeader(String key, String value) {
    	if (Objects.isNull(headers)) {
    		headers = new HashMap<String, String>();
    	}
    	
    	headers.put(key, value);
    }

    public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getParams() {
		if (Objects.isNull(params)) {
    		return Collections.emptyMap();
    	}
		
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public void addParam(String key, String value) {
    	if (Objects.isNull(params)) {
    		params = new LinkedHashMap<String, String>();
    	}
    	
    	params.put(key, value);
    }

	public void setCookie(String cookie) {
		if (Objects.isNull(params)) {
    		params = new HashMap<String, String>();
    	}
    	
    	params.put("cookie", cookie);
	}
	
	public String getCookie() {
		if (Objects.isNull(params)) {
    		return "";
    	}
    	
    	return params.get("cookie");
	}
	
	protected byte[] getBodys() throws UnsupportedEncodingException {
		if (Objects.isEmpty(body)) {
			return new byte[0];
		}
		
		return body.getBytes(charEncoding);
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
    public int getReadTimeOut() {
		return readTimeOut;
	}

	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	public int getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}
	
	public String getCharEncoding() {
		return charEncoding;
	}

	public void setCharEncoding(String charEncoding) {
		this.charEncoding = charEncoding;
	}
	
	protected boolean isGzip(HttpURLConnection conn) throws IOException {
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			return false;
		}
		
		String contentEncoding = conn.getHeaderField("Content-Encoding");
		return "gzip".equalsIgnoreCase(contentEncoding);
	}
	
	protected String charEncoding(HttpURLConnection conn) throws IOException {
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			return null;
		}
		
		String contentType = conn.getHeaderField("Content-Type");
        return getContentTypeCharset(contentType);
	}
	
	/**
     * 检测网页编码
     * @param contentType
     * @return
     */
    private String getContentTypeCharset(String contentType) {
        if (Objects.isNull(contentType)) {
        	return null;
        }
        
        Matcher matcher = charsetPattern.matcher(contentType);
        if (matcher.find()) {
            String charset = matcher.group(1).trim();
            
            charset = charset.replace("charset=", "");
            if (Objects.isEmpty(charset)) {
            	return null;
            }
            
            try {
                if ("gb2312".equalsIgnoreCase(charset) || "gbk".equalsIgnoreCase(charset)) {
                    charset = "gb18030";
                }
                
                if (Charset.isSupported(charset)) {
                	return charset;
                }

                charset = charset.toUpperCase(Locale.ENGLISH);
                if (Charset.isSupported(charset)) {
                	return charset;
                }
            } catch (IllegalCharsetNameException e) {
                return null;
            }
        }
        
        return null;
    }
 
    /**
     * 读取http流
     * @param is httpStream
     * @param charset 字符编码
     * @param isGzip 是否压缩
     * @return 
     * @throws IOException
     */
    protected String streamToString(InputStream is, String charset, boolean isGzip) throws IOException {
        if (Objects.isEmpty(charset)) {
            charset = "utf-8";
        }
        
        try (InputStreamReader inputReader = isGzip ?
			new InputStreamReader(new GZIPInputStream(is), charset) : new InputStreamReader(is, charset);
        	BufferedReader reader = new BufferedReader(inputReader)) {
        	StringBuffer result = new StringBuffer();

        	String line = "";
        	while (Objects.nonNull(line = reader.readLine())) {
        		if (Objects.nonEmpty(line)) {
        			result.append(line).append("\r\n");
        		}
        	}
        	
        	return result.toString();
        }
    }
    
    /**
     * 获取url和请求参数组合
     * @param url 请求url
     * @param params 请求附带参数
     * @return 带参数的请求url
     * @throws IOException
     */
    public static String getUrlWithParams(String url, Map<String, String> params) throws IOException {
        if (Objects.isEmpty(params)) {
        	return url;
        }
        
        StringBuffer urlBuf = new StringBuffer(url);
        
        int spit = url.indexOf('?');
        if (spit == -1) {
        	urlBuf.append("?");
        }
        
        for (Map.Entry<String, String> entry : params.entrySet()) {
        	urlBuf.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
        	urlBuf.append('=');
        	urlBuf.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        	urlBuf.append('&');
		}
        
        urlBuf.deleteCharAt(urlBuf.length() - 1);
        return urlBuf.toString();
    }
 
    /**
     * 获取请求参数
     * @param url
     * @return
     * @throws IOException
     */
    public static Map<String, String> getQueryParams(String url) throws IOException {
 
        int start = url.indexOf('?');
        String paramStr = url.substring(start, url.length()).trim();
        if (Objects.isEmpty(paramStr)) {
        	return Collections.emptyMap();
        }
        
        Map<String, String> params = new HashMap<String, String>();
      
        int equals;
        String key;
        String value;
        while (start != -1) {
            // read parameter name
            equals = url.indexOf('=', start);
            if (equals != -1) {
                key = url.substring(start + 1, equals);
            } else {
                key = url.substring(start + 1);
            }
 
            // read parameter value
            value = "";
            if (equals != -1) {
                start = url.indexOf('&', equals);
                if (start != -1) {
                    value = url.substring(equals + 1, start);
                } else {
                    value = url.substring(equals + 1);
                }
            }
 
            params.put(URLDecoder.decode(key, "UTF-8"), URLDecoder.decode(value, "UTF-8"));
        }
 
        return params;
    }
    
    public static Map<String, String> parserParams(String queryString) throws IOException {
    	 
        Map<String, String> params = new HashMap<String, String>();
      
        int start = 0;
        int equals;
        String key;
        String value;
        while (start != -1) {
            // read parameter name
            equals = queryString.indexOf('=', start);
            if (equals != -1) {
                key = queryString.substring(start + 1, equals);
            } else {
                key = queryString.substring(start + 1);
            }
 
            // read parameter value
            value = "";
            if (equals != -1) {
                start = queryString.indexOf('&', equals);
                if (start != -1) {
                    value = queryString.substring(equals + 1, start);
                } else {
                    value = queryString.substring(equals + 1);
                }
            }
 
            params.put(URLDecoder.decode(key, "UTF-8"), URLDecoder.decode(value, "UTF-8"));
        }
 
        return params;
    }
 
    /**
     * 丢弃请求参数
     * @param url 
     * @return
     * @throws IOException
     */
    public static String removeParams(String url) throws IOException {
        int q = url.indexOf('?');
        if (q != -1) {
            return url.substring(0, q);
        } else {
            return url;
        }
    }

}
