package com.nfitton.demostructure.user;

import com.nfitton.demostructure.users.User;
import com.nfitton.demostructure.users.api.v1.UserController;
import com.nfitton.demostructure.users.api.v1.bean.GetUser;
import com.nfitton.demostructure.users.api.v1.bean.UpdateUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.server.WebFilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

import static com.nfitton.demostructure.user.UserAssertion.assertGetUserAttributesEqual;
import static com.nfitton.demostructure.user.UserAssertion.assertUserAttributesEqual;
import static com.nfitton.demostructure.user.UserGeneration.createValidUser;
import static com.nfitton.demostructure.user.UserGeneration.generateUpdateUser;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserDomainTest {

  private static WebTestClient client;
  @Autowired UserController userController;
  @Autowired private WebFilterChainProxy webFilterChainProxy;

  @Before
  public void setup() {
    client = WebTestClient.bindToController(userController).webFilter(webFilterChainProxy).build();
  }

  @Test
  public void
      givenAValidUserIsCreated_WhenTheUserIsCreated_ThenSuccessfulResponseAndMatchingReturnUser() {
    // GIVEN - A valid user is created
    User newUser =
        User.newBuilder()
            .withFirstName("John")
            .withLastName("Doe")
            .withEmail("jd@nfitton.com")
            .withPassword("1234")
            .build();

    // WHEN - The user is created
    WebTestClient.BodySpec<GetUser, ?> createdUserResult =
        client
            .post()
            .uri("/users")
            .body(BodyInserters.fromObject(newUser))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(GetUser.class);

    // THEN - Successful response and matching return user.
    createdUserResult.consumeWith(
        getUserEntityExchangeResult -> {
          assertThat(getUserEntityExchangeResult.getResponseBody()).isNotNull();
          assertUserAttributesEqual(getUserEntityExchangeResult.getResponseBody(), newUser);
        });
  }

  @Test
  public void
      givenAValidUserIsCreated_WhenTheUserIsReturned_ThenSuccessfulResponseAndMatchingReturnUser() {

    // GIVEN - A valid user is created
    GetUser newUser = createValidUser(client);

    // WHEN - The user is retrieved
    WebTestClient.BodySpec<GetUser, ?> createdUserResult =
        client
            .get()
            .uri("/users/" + newUser.getId())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(GetUser.class);

    // THEN - Successful response and matching return user.
    createdUserResult.consumeWith(
        getUserEntityExchangeResult -> {
          assertThat(getUserEntityExchangeResult.getResponseBody()).isNotNull();
          assertGetUserAttributesEqual(getUserEntityExchangeResult.getResponseBody(), newUser);
        });
  }

  @Test
  public void
      givenAValidUserIsCreated_WhenTheUserIsUpdated_ThenSuccessfulResponseAndMatchingReturnUser() {

    // GIVEN - A valid user is created
    GetUser newUser = createValidUser(client);

    UpdateUser updateUser = generateUpdateUser();

    // WHEN - The user is retrieved
    WebTestClient.BodySpec<GetUser, ?> createdUserResult =
        client
            .put()
            .uri("/users/" + newUser.getId())
            .body(BodyInserters.fromObject(updateUser))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(GetUser.class);

    // THEN - Successful response and matching return user.
    createdUserResult.consumeWith(
        getUserEntityExchangeResult -> {
          assertThat(getUserEntityExchangeResult.getResponseBody()).isNotNull();
          assertUserAttributesEqual(
              getUserEntityExchangeResult.getResponseBody(), updateUser.toUser());
        });
  }
}
