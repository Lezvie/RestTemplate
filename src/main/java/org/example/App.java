package org.example;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class App {

    private static final String REST_URL = "http://94.198.50.185:7081/api/users";

    public static void main( String[] args ) {


        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(REST_URL, String.class);
        String sessionId = responseEntity.getHeaders().get("set-cookie").get(0);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.COOKIE, sessionId);

        User user = new User(3L, "James", "Brown", 73);

        HttpEntity<User> request = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> responsePost = restTemplate.exchange(REST_URL, HttpMethod.POST, request, String.class);

        user.setName("Thomas");
        user.setLastName("Shelby");

        HttpEntity<User> update = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> responsePut = restTemplate.exchange(REST_URL, HttpMethod.PUT, update, String.class);

        ResponseEntity<String> responseDelete = restTemplate.exchange(REST_URL + "/3", HttpMethod.DELETE, request, String.class);

        String code = responsePost.getBody() + responsePut.getBody() + responseDelete.getBody();
        System.out.println("Итоговый код - " + code);
        System.out.println("Количество символов в коде = " + code.length());

    }
}