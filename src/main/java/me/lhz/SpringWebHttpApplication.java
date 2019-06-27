package me.lhz;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.*;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * spirng-web的http模块对Okhttp3、Apache HttpClient、java.net.HttpURLConnection做了统一的封装
 *
 */
public class SpringWebHttpApplication {
    URI uri;
    HttpMethod httpMethod;
    ClientHttpRequestFactory clientHttpRequestFactory;
    ClientHttpRequest clientHttpRequest;
    ClientHttpResponse clientHttpResponse;
    public static void main(String[] args) throws Exception{
        SpringWebHttpApplication app = new SpringWebHttpApplication();
        app.uri = new URI("https://www.baidu.com/");
        app.httpMethod = HttpMethod.GET;
        app.clientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory();
        System.out.println("-------------使用Okhttp----------------");
        app.execute();
        System.out.println("-------------使用Apache HttpClient----------------");
        app.clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        app.execute();
        System.out.println("-------------java.net.HttpURLConnection----------------");
        app.clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        app.execute();
    }
    private void execute() throws Exception{
        clientHttpRequest = clientHttpRequestFactory.createRequest(uri,httpMethod);
        clientHttpResponse = clientHttpRequest.execute();
        System.out.println(clientHttpResponse.getStatusCode());
        System.out.println(parseString(clientHttpResponse.getBody()));
    }
    private static String parseString(InputStream inputStream) throws Exception{
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "/n");
        }
        return sb.toString();
    }
}
