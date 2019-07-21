package client;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.net.URI;

public class ApacheClient {

    private CloseableHttpClient httpClient;
    private String scheme;
    private String baseUrl;

    public ApacheClient(CloseableHttpClient httpClient, String baseUrl, String scheme) {
        this.httpClient = httpClient;
        this.baseUrl = baseUrl;
        this.scheme = scheme;
    }

    private URIBuilder getBuilder() {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(scheme).setHost(baseUrl);    //api.verifid.app
        return builder;
    }

    public CloseableHttpResponse get(String endpoint) {
        URI uri = getUrı(endpoint);

        HttpGet request = new HttpGet(uri);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return response;
    }


    public CloseableHttpResponse post(String endpoint, String requestJson) {
        StringEntity requestEntity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
        HttpPost httpPost = new HttpPost(getUrı(endpoint));
        httpPost.setEntity(requestEntity);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return response;
    }


    private URI getUrı(String endpoint) {
        URIBuilder urıBuilder = getBuilder();
        urıBuilder.setPath(endpoint);   // /v1/user/sendData
        URI uri = null;
        try {
            uri = urıBuilder.build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return uri;
    }
}
