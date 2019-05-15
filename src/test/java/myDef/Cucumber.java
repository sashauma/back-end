package myDef;

import com.brainacad.HttpClientHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import java.io.IOException;

public class Cucumber {

    private static String URL;
    private static HttpResponse response;

    @Given("i have server by url {string}")
    public void i_have_server_by_url(String url) {
        URL=url;
    }

    @When("i send GET request on endpoind {string} and parametrs {string}")
    public void i_send_GET_request_on_endpoind_and_parametrs(String endpoint, String parametrs) throws IOException {
        response = HttpClientHelper.get(URL + endpoint, parametrs);
    }

    @Then("I get response status code {int}")
    public void i_get_response_status_code(int responseCode) {
        Assert.assertEquals(
                String.format("I get response status code %s",responseCode),
                response.getStatusLine().getStatusCode(),responseCode);
    }

    @Then("i get response - shouldn't be {string}")
    public void i_get_response_shouldn_t_be(String string) throws IOException {
        String body = HttpClientHelper.getBodyFromResponse(response);
        Assert.assertNotEquals("Body shouldn't be null", null, body);
    }

//    @Then("i should get statusCode {int}")
//    public void iShouldGetStatusCode(int responceCode) {
//        int statusCode = response.getStatusLine().getStatusCode();
//        Assert.assertEquals("Response status code should be 201", responceCode, statusCode);
//    }

//    @When("i send post request on endpoind {string} and requestBody {string}")
//    public void iSendPostRequestOnEndpoindAndRequestBodyNameMorpheusJobLeader(String arg0) {
//    }
}