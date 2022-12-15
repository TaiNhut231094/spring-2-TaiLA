package com.codegym.book_store.security.jwt;

import com.codegym.book_store.dto.reponse.GooglePojo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GoogleUtils {
    @Autowired
    private Environment environment;

    public String getToken(final String code) throws ClientProtocolException, IOException {
        String link = environment.getProperty("google.link.get.token");
        String response = Request.Post(link)
                .bodyForm(Form.form().add("client_id", environment.getProperty("google.app.id"))
                        .add("client_secret", environment.getProperty("google.app.secret"))
                        .add("redirect_uri", environment.getProperty("google.redirect.uri")).add("code", code)
                        .add("grant_type", "authorization_code").build())
                .execute().returnContent().asString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(response).get("access_token");
        return node.textValue();
    }

    public GooglePojo getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = environment.getProperty("google.link.get.user_info") + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        ObjectMapper objectMapper = new ObjectMapper();
        GooglePojo googlePojo = objectMapper.readValue(response, GooglePojo.class);
        System.out.println(googlePojo);
        return googlePojo;
    }
}
