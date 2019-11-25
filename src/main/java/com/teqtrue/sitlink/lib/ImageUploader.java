package com.teqtrue.sitlink.lib;

import com.teqtrue.sitlink.model.Image;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class ImageUploader {
  public static Image upload(String imageData) {
    if (imageData == null || imageData.length() == 0) return null;
    HttpPost post = new HttpPost("https://api.imgur.com/3/image");
    post.addHeader("Authorization", "Client-ID 50f7d3db60cd85e");
    CloseableHttpClient httpClient = HttpClients.createDefault();
    imageData = imageData.substring(imageData.indexOf(","));
    try {
      StringEntity entity = new StringEntity(imageData);
      post.setEntity(entity);
      CloseableHttpResponse res = httpClient.execute(post);
      JSONObject json = new JSONObject(EntityUtils.toString(res.getEntity()));
      JSONObject data = json.getJSONObject("data");
      return new Image(data.getString("id"), data.getInt("width"), data.getInt("height"));
    } catch (Exception e) {
      return null;
    }
  }
}
