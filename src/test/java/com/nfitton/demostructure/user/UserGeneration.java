package com.nfitton.demostructure.user;

import com.nfitton.demostructure.users.User;
import com.nfitton.demostructure.users.api.v1.bean.GetUser;
import com.nfitton.demostructure.users.api.v1.bean.UpdateUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static com.nfitton.demostructure.util.generator.StringGenerator.generateEmail;
import static com.nfitton.demostructure.util.generator.StringGenerator.generateName;
import static com.nfitton.demostructure.util.generator.StringGenerator.generatePassword;

class UserGeneration {

  private static User generateUser() {
    return User.newBuilder()
        .withFirstName(generateName())
        .withLastName(generateName())
        .withEmail(generateEmail())
        .withPassword(generatePassword())
        .build();
  }

  static UpdateUser generateUpdateUser() {
    return UpdateUser.newBuilder()
        .withFirstName(generateName())
        .withLastName(generateName())
        .withEmail(generateEmail())
        .build();
  }

  static GetUser createValidUser(WebTestClient client) {
    User newUser = generateUser();

    return client
        .post()
        .uri("/users")
        .body(BodyInserters.fromObject(newUser))
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(GetUser.class)
        .returnResult()
        .getResponseBody();
  }
}
