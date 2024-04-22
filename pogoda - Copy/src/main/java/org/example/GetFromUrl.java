package org.example;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class GetFromUrl {
    public static JSONObject GetFromUrl(String url) {
        HttpEntity entity;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        request.addHeader("custom-key", "programming");
        try (CloseableHttpResponse response = httpclient.execute(request)) {

            // Get HttpResponse Status
            //System.out.println(response.getStatusLine().toString());

            entity = response.getEntity();
            Header headers = entity.getContentType();
            // System.out.println(headers);
//        System.out.println(entity.toString());
            String result = EntityUtils.toString(entity);
            JSONObject jsonObject = new JSONObject(result);
        return jsonObject;
        }
        catch (IOException e){
            System.out.println("exception");
        }
        return null;
    }
}
