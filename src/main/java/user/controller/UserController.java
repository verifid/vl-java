package user.controller;

import client.ApacheClient;
import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import user.models.User;
import util.AppParams;

public class UserController {

    private ApacheClient client;
    private Gson gson = new Gson();

    public UserController() {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        client = new ApacheClient(closeableHttpClient, AppParams.BASE_URL, AppParams.BASE_SCHEME);
    }

    public UserController(String baseScheme, String baseUrl) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        client = new ApacheClient(closeableHttpClient, baseUrl, baseScheme);
    }

    public CloseableHttpResponse sendUserData(User user) {
        return client.post(AppParams.SEND_USER_DATA_ENDPOINT, gson.toJson(user));
    }

}
