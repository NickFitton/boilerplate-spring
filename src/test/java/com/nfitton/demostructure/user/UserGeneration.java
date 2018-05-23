package com.nfitton.demostructure.user;

import static com.nfitton.demostructure.util.generator.StringGenerator.generateEmail;
import static com.nfitton.demostructure.util.generator.StringGenerator.generateName;
import static com.nfitton.demostructure.util.generator.StringGenerator.generatePassword;

import com.nfitton.demostructure.users.UserEntity;
import com.nfitton.demostructure.users.api.v1.bean.UserDto;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

class UserGeneration {

  private static UserEntity generateUserEntity() {
    return UserEntity.newBuilder()
        .withFirstName(generateName())
        .withLastName(generateName())
        .withEmail(generateEmail())
        .withPassword(generatePassword())
        .build();
  }

  static UserDto generateUserDto() {
    return UserDto.fromEntity(generateUserEntity());
  }

  static UserDto createValidUser(WebTestClient client) {
    UserEntity newUser = generateUserEntity();

    return client
        .post()
        .uri("/users")
        .body(BodyInserters.fromObject(newUser))
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(UserDto.class)
        .returnResult()
        .getResponseBody();
  }
}
