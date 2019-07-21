package user.controller;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.Gson;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import user.models.User;
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
        String endPoint = "/v1/user/sendData";
        Gson gson = new Gson();

        User user = new User();
        user.setName("Tony");
        user.setSurname("Montana");
        user.setCountry("Cuba");
        user.setGender("Male");
        user.setDateOfBirth("27.01.1958");

        stubFor(post(urlPathEqualTo(endPoint))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withRequestBody(equalTo(gson.toJson(user)))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK)));

        CloseableHttpResponse closeableHttpResponse = userController.sendUserData(user);
        assertNotNull(closeableHttpResponse);
        assertEquals(HttpStatus.SC_OK, closeableHttpResponse.getStatusLine().getStatusCode());
    }


    @Test
    public void testSendDataFail() throws Exception {
        String endPoint = "/v1/user/sendData";
        Gson gson = new Gson();

        User user = new User();
        user.setName("Tony");
        user.setSurname("Montana");
        user.setCountry("Cuba");
        user.setGender("Male");
        user.setDateOfBirth("27.01.1958");

        stubFor(post(urlPathEqualTo(endPoint))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withRequestBody(equalTo(gson.toJson(user)))
                .willReturn(aResponse().withStatus(HttpStatus.SC_BAD_REQUEST)));

        CloseableHttpResponse closeableHttpResponse = userController.sendUserData(user);
        assertNotNull(closeableHttpResponse);
        assertEquals(HttpStatus.SC_BAD_REQUEST, closeableHttpResponse.getStatusLine().getStatusCode());
    }
}
