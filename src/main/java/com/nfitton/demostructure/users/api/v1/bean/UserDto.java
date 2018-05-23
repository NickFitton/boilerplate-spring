package com.nfitton.demostructure.users.api.v1.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.nfitton.demostructure.users.UserEntity;

import java.time.ZonedDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = UserDto.Builder.class)
public class UserDto {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;

  private UserDto(Builder builder) {
    id = builder.id;
    firstName = builder.firstName;
    lastName = builder.lastName;
    email = builder.email;
    password = builder.password;
    createdAt = builder.createdAt;
    updatedAt = builder.updatedAt;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public UUID getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public ZonedDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Creates a {@code UserEntity} from the current {@code UserDto} class.
   *
   * @return a {@code UserEntity} with the variables from this {@code UserDto}.
   */
  public UserEntity toUser() {
    return UserEntity.newBuilder()
        .withFirstName(firstName)
        .withLastName(lastName)
        .withEmail(email)
        .withPassword(password)
        .build();
  }

  public static UserDto fromEntity(UserEntity entity) {
    return UserDto.newBuilder()
        .withId(entity.getId())
        .withFirstName(entity.getFirstName())
        .withLastName(entity.getLastName())
        .withEmail(entity.getEmail())
        .withPassword(entity.getPassword())
        .withCreatedAt(entity.getCreatedAt())
        .withUpdatedAt(entity.getUpdatedAt())
        .build();
  }

  @JsonPOJOBuilder
  public static final class Builder {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private UUID id;

    private Builder() {}

    public Builder withFirstName(String val) {
      firstName = val;
      return this;
    }

    public Builder withLastName(String val) {
      lastName = val;
      return this;
    }

    public Builder withEmail(String val) {
      email = val;
      return this;
    }

    public Builder withPassword(String val) {
      password = val;
      return this;
    }

    public Builder withCreatedAt(ZonedDateTime val) {
      createdAt = val;
      return this;
    }

    public Builder withUpdatedAt(ZonedDateTime val) {
      updatedAt = val;
      return this;
    }

    public UserDto build() {
      return new UserDto(this);
    }

    public Builder withId(UUID val) {
      id = val;
      return this;
    }
  }
}
