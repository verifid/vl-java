package image.controller;

import client.ApacheClient;
import com.google.gson.Gson;
import image.models.ImageUploadRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import util.AppParams;

public class ImageController {

    private ApacheClient client;
    private Gson gson = new Gson();

    public ImageController() {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        client = new ApacheClient(closeableHttpClient, AppParams.BASE_URL, AppParams.BASE_SCHEME);
    }

    public ImageController(String baseScheme, String baseUrl) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        client = new ApacheClient(closeableHttpClient, baseUrl, baseScheme);
    }

    public CloseableHttpResponse uploadIdentity(ImageUploadRequest imageUploadRequest) {
        return client.post(AppParams.UPLOAD_ID_ENDPOINT, gson.toJson(imageUploadRequest));
    }

    public CloseableHttpResponse uploadProfile(ImageUploadRequest imageUploadRequest) {
        return client.post(AppParams.UPLOAD_PROFILE_ENDPOINT, gson.toJson(imageUploadRequest));
    }
}
