package com.nfitton.demostructure.users.api.v1.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.nfitton.demostructure.users.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = UpdateUser.Builder.class)
public class UpdateUser {
  private String firstName;
  private String lastName;
  private String email;

  private UpdateUser(Builder builder) {
    firstName = builder.firstName;
    lastName = builder.lastName;
    email = builder.email;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public User toUser() {
    return User.newBuilder()
        .withFirstName(firstName)
        .withLastName(lastName)
        .withEmail(email)
        .build();
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

  @JsonPOJOBuilder
  public static final class Builder {
    private String firstName;
    private String lastName;
    private String email;

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

    public UpdateUser build() {
      return new UpdateUser(this);
    }
  }
}
