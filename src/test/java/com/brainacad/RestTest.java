package com.brainacad;

import com.github.fge.jsonschema.core.report.ProcessingReport;
import org.apache.http.HttpResponse;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.util.*;

import static com.brainacad.JsonUtils.*;


public class RestTest{

    private static final String URL="https://reqres.in/";

    @Test//GET метод
    public void checkGetResponseStatusCode() throws IOException {
        String endpoint="/api/users";
        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.get(URL+endpoint,"page=2");
        //получаем статус код из ответа
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);
    }

    @Test//GET метод
    public void checkGetResponseBodyNotNull() throws IOException {
        String endpoint="/api/users";

        HttpResponse response = HttpClientHelper.get(URL+endpoint,"page=2");

        //Конвертируем входящий поток тела ответа в строку
        String body=HttpClientHelper.getBodyFromResponse(response);
        System.out.println(body);
        Assert.assertNotEquals("Body shouldn't be null", null, body);
    }

    @Test//POST метод
    public void checkPostResponseStatusCode() throws IOException {
        String endpoint="/api/users";
        //создаём тело запроса
        String requestBody="{\"name\": \"morpheus\",\"job\": \"leader\"}";
        //Выполняем REST POST запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.post(URL+endpoint,requestBody);
        //получаем статус код из ответа
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 201", 201, statusCode);
    }

    @Test//POST метод
    public void checkPostResponseBodyNotNull() throws IOException {
        String endpoint="/api/users";
        //создаём тело запроса
        String requestBody="{\"name\": \"morpheus\",\"job\": \"leader\"}";

        //Выполняем REST POST запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.post(URL+endpoint,requestBody);

        //Конвертируем входящий поток тела ответа в строку
        String body=HttpClientHelper.getBodyFromResponse(response);
        System.out.println(body);
        Assert.assertNotEquals("Body shouldn't be null", null, body);
    }

    //TODO: напишите по тесткейсу на каждый вариант запроса на сайте https://reqres.in
    //TODO: в тескейсах проверьте Result Code и несколько параметров из JSON ответа (если он есть

   @Test//LIST USERS

    public void listUsers() throws IOException {
        String endpoint="/api/users";
        HttpResponse response = HttpClientHelper.get(URL+endpoint,"page=2");
       int statusCode = response.getStatusLine().getStatusCode();
       Assert.assertEquals("Response status code should be 200", 200, statusCode);
        String jsonPath = "$.data[*].first_name";
        String body=HttpClientHelper.getBodyFromResponse(response);
        List listUsers = listFromJSONByPath(body,jsonPath);
        List expectedList = Arrays.asList("Eve", "Charles", "Tracey");
        System.out.println(listUsers);
        Assert.assertEquals("LIST USERS BAG", expectedList, listUsers);
    }

    @Test//single users

    public void singleUsers() throws IOException {
        String endpoint="/api/users/2";
        HttpResponse response = HttpClientHelper.get(URL+endpoint, "" );
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals("Response status code should be 200", 200, statusCode);
        String jsonPath = "$.data.id";
        String body=HttpClientHelper.getBodyFromResponse(response);
        int intUsers = intFromJSONByPath(body,jsonPath);
        int expectedInt = 2;
        System.out.println(intUsers);
        Assert.assertEquals("single users bag", expectedInt, intUsers);
    }

    @Test//postCreated

    public void postCreated() throws IOException {
        String endpoint="/api/users";
        HttpResponse response = HttpClientHelper.post(URL+endpoint,"{\"name\":\"alex\",\"job\":\"pm\"}" );
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals("Response status code should be 201", 201, statusCode);
        String jsonPath = "$.createdAt";
        String body=HttpClientHelper.getBodyFromResponse(response);
        String createdAtUserId = stringFromJSONByPath(body,jsonPath);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZoneUTC();
        DateTime dt = formatter.parseDateTime(stringFromJSONByPath(body,jsonPath));
        System.out.println(createdAtUserId);
        Assert.assertTrue("postCreated bag", dt.plusMinutes(-10).isBeforeNow());
    }

    @Test// json test
    public void validateJsonTest() throws Exception {
        String endpoint="/api/users";
        HttpResponse response = HttpClientHelper.get(URL+endpoint, "page=2" );
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals("Response status code should be 200", 200, statusCode);
        String body=HttpClientHelper.getBodyFromResponse(response);
        ProcessingReport result = MyJsonValidator.validateJson(body, "schemas/schema1.json");
        Assert.assertTrue(result.toString(),result.isSuccess());
    }

    //comment for push to git
}