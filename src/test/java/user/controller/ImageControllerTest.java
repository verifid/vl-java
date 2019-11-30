package user.controller;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.Gson;
import image.controller.ImageController;
import image.models.ImageUploadRequest;
import image.models.ImageUploadResponse;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import util.AppParams;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ImageControllerTest {

    public static final Gson gson = new Gson();

    @Rule
    public WireMockRule mockRule = new WireMockRule(9090);


    private ImageController imageController;

    @Before
    public void init() {
        imageController = new ImageController(AppParams.TEST_BASE_SCHEME, AppParams.TEST_BASE_URL);
    }

    @After
    public void nullifyAfterTest() {
        imageController = null;
        mockRule.stop();
    }


    @Test
    public void testUploadIdentitySuccess() {
        ImageUploadRequest imageUploadReq = new ImageUploadRequest("userId", "/test/deneme.jpeg");
        stubFor(post(urlPathEqualTo(AppParams.UPLOAD_ID_ENDPOINT))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withRequestBody(equalTo(gson.toJson(imageUploadReq)))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK)));

        CloseableHttpResponse response = imageController.uploadIdentity(imageUploadReq);

        assertNotNull(response);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testUploadIdentityFail() {
        ImageUploadRequest imageUploadReq = new ImageUploadRequest("notFoundUserId", "/ff/deneme.fff");


        stubFor(post(urlPathEqualTo(AppParams.UPLOAD_ID_ENDPOINT))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withRequestBody(equalTo(gson.toJson(imageUploadReq)))
                .willReturn(aResponse().withStatus(HttpStatus.SC_NO_CONTENT)));

        CloseableHttpResponse response = imageController.uploadIdentity(imageUploadReq);

        assertEquals(HttpStatus.SC_NO_CONTENT, response.getStatusLine().getStatusCode());


        imageUploadReq = new ImageUploadRequest("notFoundUserId2", "/ff/deneme2.fff");

        stubFor(post(urlPathEqualTo(AppParams.UPLOAD_ID_ENDPOINT))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withRequestBody(equalTo(gson.toJson(imageUploadReq)))
                .willReturn(aResponse().withStatus(HttpStatus.SC_BAD_REQUEST)));

        response = imageController.uploadIdentity(imageUploadReq);

        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusLine().getStatusCode());
    }


    @Test
    public void testUploadProfileSuccess() {
        ImageUploadRequest imageUploadReq = new ImageUploadRequest("42", "/example/test.jpeg");


        stubFor(post(urlPathEqualTo(AppParams.UPLOAD_PROFILE_ENDPOINT))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withRequestBody(equalTo(gson.toJson(imageUploadReq)))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK)));

        CloseableHttpResponse response = imageController.uploadProfile(imageUploadReq);

        assertNotNull(response);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testUploadProfileFail() {
        ImageUploadRequest imageUploadReq = new ImageUploadRequest("43", "/example/testf.jpeg");


        stubFor(post(urlPathEqualTo(AppParams.UPLOAD_PROFILE_ENDPOINT))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withRequestBody(equalTo(gson.toJson(imageUploadReq)))
                .willReturn(aResponse().withStatus(HttpStatus.SC_NO_CONTENT)));

        CloseableHttpResponse response = imageController.uploadProfile(imageUploadReq);

        assertNotNull(response);
        assertEquals(HttpStatus.SC_NO_CONTENT, response.getStatusLine().getStatusCode());


        imageUploadReq = new ImageUploadRequest("44", "/example/testf.jpeg");


        stubFor(post(urlPathEqualTo(AppParams.UPLOAD_PROFILE_ENDPOINT))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withRequestBody(equalTo(gson.toJson(imageUploadReq)))
                .willReturn(aResponse().withStatus(HttpStatus.SC_BAD_REQUEST)));

        response = imageController.uploadProfile(imageUploadReq);

        assertNotNull(response);
        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusLine().getStatusCode());
    }
}
