package com.graduationDesign.util;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
public class HttpUtils {
    static public String doGet(String url, String queryString) {
        try {
            URL u = new URL(url + queryString);
            URI uri = new URI(u.getProtocol(), null, u.getHost(), u.getPort(), u.getPath(), u.getQuery(), null);
            HttpGet method = new HttpGet(uri);
            DefaultHttpClient client = new DefaultHttpClient();
            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15 * 1000);
            HttpResponse response = client.execute(method);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    static public String doPost(String url, String queryString) throws Exception {
        URL u = new URL(url);
        URI uri = new URI(u.getProtocol(), null, u.getHost(), u.getPort(), u.getPath(), u.getQuery(), null);
        HttpPost method = new HttpPost(uri);
        List nameValuePairs = new ArrayList ();
        nameValuePairs.add(new BasicNameValuePair("data", queryString));
        UrlEncodedFormEntity en = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
        method.setEntity(en);
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15 * 1000);
        HttpResponse response = client.execute(method);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity(), "utf-8");
        }else {
            return "connect error statusCode="+response.getStatusLine().getStatusCode();
        }
    }
};