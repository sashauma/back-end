Feature: test

  Scenario: verify that GET request return status code 200
    Given i have server by url "https://reqres.in"
    When i send GET request on endpoind "/api/users" and parametrs "page=2"
    Then I get response status code 200

  Scenario: get method for reqres.in/api/users/page=2 (Body shouldn't be null)
    Given i have server by url "https://reqres.in"
    When i send GET request on endpoind "/api/users" and parametrs "page=2"
    Then i get response - shouldn't be "null"

  Scenario: zapchasti.ria.com, get method - Response status code 200
    Given i have server by url "https://zapchasti.ria.com/"
    Then I get response status code 200

  Scenario: itliga.com.ua, get method - Response status code 200
    Given i have server by url "http://itliga.com.ua/"
    Then I get response status code 200

#  Scenario: post method - Response status code should be 201
#    Given i have server by url "https://reqres.in"
#    When i send post request on endpoind "/api/users" and requestBody "{\"name\": \"morpheus\",\"job\": \"leader\"}"
#    Then i should get statusCode 201

#  @Test//POST метод
#  public void checkPostResponseStatusCode() throws IOException {
#  String endpoint="/api/users";
#  String requestBody="{\"name\": \"morpheus\",\"job\": \"leader\"}";
#  HttpResponse response = HttpClientHelper.post(URL+endpoint,requestBody);
#  int statusCode = response.getStatusLine().getStatusCode();
#  Assert.assertEquals("Response status code should be 201", 201, statusCode);