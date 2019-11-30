package user.controller;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import user.models.User;
import user.models.UserDataResponse;
import user.models.UserVerifyRequest;
import util.AppParams;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserControllerTest {

    @Rule
    public WireMockRule mockRule = new WireMockRule(9090);


    private UserController userController;

    @Before
    public void init() {
        userController = new UserController(AppParams.TEST_BASE_SCHEME, AppParams.TEST_BASE_URL);
    }

    @After
    public void nullifyAfterTest() {
        userController = null;
        mockRule.stop();
    }


    @Test
    public void testSendDataParameter() {
        User user = new User();
        user.setName("Tony");
        user.setSurname("Montana");
        user.setCountry("Cuba");
        user.setGender("Male");
        user.setDateOfBirth("27.01.1958");


        assertEquals("Tony", user.getName());
        assertEquals("Montana", user.getSurname());
        assertEquals("Cuba", user.getCountry());
        assertEquals("Male", user.getGender());
        assertEquals("27.01.1958", user.getDateOfBirth());
    }


    @Test
    public void testSendDataSuccess() throws Exception {
        Gson gson = new Gson();

        User user = new User();
        user.setName("Tony");
        user.setSurname("Montana");
        user.setCountry("Cuba");
        user.setGender("Male");
        user.setDateOfBirth("27.01.1958");

        UserDataResponse userDataResponse = new UserDataResponse();
        userDataResponse.setUserId("111");
        userDataResponse.setCode(0);
        userDataResponse.setMessage("");
        userDataResponse.setType("");

        stubFor(post(urlPathEqualTo(AppParams.SEND_USER_DATA_ENDPOINT))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withRequestBody(equalTo(gson.toJson(user)))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK).withBody(gson.toJson(userDataResponse))));

        CloseableHttpResponse response = userController.sendUserData(user);
        assertNotNull(response);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        HttpEntity httpEntity = response.getEntity();
        String responseJson = EntityUtils.toString(httpEntity);
        assertEquals(gson.toJson(userDataResponse), responseJson);
    }


    @Test
    public void testSendDataFail() throws Exception {
        Gson gson = new Gson();

        User user = new User();
        user.setName("Tony");
        user.setSurname("Montana");
        user.setCountry("Cuba");
        user.setGender("Male");
        user.setDateOfBirth("27.01.1958");

        stubFor(post(urlPathEqualTo(AppParams.SEND_USER_DATA_ENDPOINT))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withRequestBody(equalTo(gson.toJson(user)))
                .willReturn(aResponse().withStatus(HttpStatus.SC_BAD_REQUEST)));

        CloseableHttpResponse closeableHttpResponse = userController.sendUserData(user);
        assertNotNull(closeableHttpResponse);
        assertEquals(HttpStatus.SC_BAD_REQUEST, closeableHttpResponse.getStatusLine().getStatusCode());
    }


    @Test
    public void testVerifySuccess() throws Exception {
        Gson gson = new Gson();

        UserVerifyRequest userverifyReq = new UserVerifyRequest();
        userverifyReq.setLanguage("TR");
        userverifyReq.setUserId("1111");

        stubFor(post(urlPathEqualTo(AppParams.VERIFY_USER_ENDPOINT))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withRequestBody(equalTo(gson.toJson(userverifyReq)))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK)));

        CloseableHttpResponse closeableHttpResponse = userController.verify(userverifyReq);
        assertNotNull(closeableHttpResponse);
        assertEquals(HttpStatus.SC_OK, closeableHttpResponse.getStatusLine().getStatusCode());
    }


    @Test
    public void testVerifyFail() {
        Gson gson = new Gson();

        UserVerifyRequest userverifyReq = new UserVerifyRequest();
        userverifyReq.setLanguage("cc");
        userverifyReq.setUserId("1112");

        stubFor(post(urlPathEqualTo(AppParams.VERIFY_USER_ENDPOINT))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withRequestBody(equalTo(gson.toJson(userverifyReq)))
                .willReturn(aResponse().withStatus(HttpStatus.SC_BAD_REQUEST)));

        CloseableHttpResponse closeableHttpResponse = userController.verify(userverifyReq);
        assertNotNull(closeableHttpResponse);
        assertEquals(HttpStatus.SC_BAD_REQUEST, closeableHttpResponse.getStatusLine().getStatusCode());
    }
}
