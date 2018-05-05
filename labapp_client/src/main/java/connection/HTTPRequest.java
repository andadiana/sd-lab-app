package connection;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HTTPRequest {


    public static String sendGet(String path) throws Exception {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://localhost:8080" + path);
            CloseableHttpResponse response = client.execute(httpGet);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }catch (Exception e) {
            throw new Exception("Connection to server failed!");
        }
    }

    public static void sendPost(String path, String json) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080" + path);
        StringEntity entity = new StringEntity(json);

        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        if (response.getStatusLine().getStatusCode() != 200) {
            client.close();
            throw new Exception("Request failed!");
        }
    }

    public static void sendPut(String path, String json) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut("http://localhost:8080" + path);
        StringEntity entity = new StringEntity(json);

        httpPut.setEntity(entity);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPut);
        if (response.getStatusLine().getStatusCode() != 202) {
            client.close();
            throw new Exception("Request failed!");
        }
    }

    public static void sendDelete(String path) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete("http://localhost:8080" + path);
        CloseableHttpResponse response = client.execute(httpDelete);

        if (response.getStatusLine().getStatusCode() != 202) {
            client.close();
            throw new Exception("Request failed!");
        }
    }


}
