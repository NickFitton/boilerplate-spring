package com.nfitton.demostructure.user;

import com.nfitton.demostructure.TestClient;
import com.nfitton.demostructure.users.User;
import com.nfitton.demostructure.users.api.v1.UserController;
import com.nfitton.demostructure.users.api.v1.bean.GetUser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.ZonedDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserDomainTest {

  WebTestClient client;
  @Autowired UserController userController;

  @BeforeClass
  public void setup() {
    client = TestClient.newBuilder().withController(userController).build().getClient();
  }

  @Test
  public void given_When_Then() {
    // GIVEN - A valid user is created
    User newUser =
        User.newBuilder()
            .withCreatedAt(ZonedDateTime.now())
            .withUpdatedAt(ZonedDateTime.now())
            .withFirstName("John")
            .withLastName("Doe")
            .withEmail("jd@nfitton.com")
            .withPassword("1234")
            .build();

    // WHEN - The user is created
    WebTestClient.ResponseSpec responseSpec =
        client.post().uri("/users").body(BodyInserters.fromObject(newUser)).exchange();

    // THEN - Successful response and matching return user.
    responseSpec.expectStatus().isCreated().expectBody(GetUser.class);
  }
}
