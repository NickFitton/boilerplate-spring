package com.nfitton.demostructure.users.api.v1.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.nfitton.demostructure.users.User;

import java.time.ZonedDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = GetUser.Builder.class)
public class GetUser {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;

  private GetUser(Builder builder) {
    id = builder.id;
    firstName = builder.firstName;
    lastName = builder.lastName;
    email = builder.email;
    createdAt = builder.createdAt;
    updatedAt = builder.updatedAt;
  }

  /**
   * Create a {@code GetUser} from the give {@code User}.
   * @param user is the user to create a {@code GetUser} with.
   */
  public GetUser(User user) {
    id = user.getId();
    firstName = user.getFirstName();
    lastName = user.getLastName();
    email = user.getEmail();
    createdAt = user.getCreatedAt();
    updatedAt = user.getUpdatedAt();
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

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public ZonedDateTime getUpdatedAt() {
    return updatedAt;
  }

  @JsonPOJOBuilder
  public static final class Builder {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime deletedAt;

    private Builder() {}

    public Builder withId(UUID val) {
      id = val;
      return this;
    }

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

    public Builder withCreatedAt(ZonedDateTime val) {
      createdAt = val;
      return this;
    }

    public Builder withUpdatedAt(ZonedDateTime val) {
      updatedAt = val;
      return this;
    }

    public GetUser build() {
      return new GetUser(this);
    }
  }
}
