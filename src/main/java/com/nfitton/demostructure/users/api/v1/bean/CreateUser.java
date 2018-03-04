package com.nfitton.demostructure.users.api.v1.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.nfitton.demostructure.users.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = CreateUser.Builder.class)
public class CreateUser {
  private String firstName;
  private String lastName;
  private String email;
  private String password;

  private CreateUser(Builder builder) {
    firstName = builder.firstName;
    lastName = builder.lastName;
    email = builder.email;
    password = builder.password;
  }

  public static Builder newBuilder() {
    return new Builder();
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

  /**
   * Creates a {@code User} from the current {@code CreateUser} class.
   * @return a {@code User} with the variables from this {@code CreateUser}.
   */
  public User toUser() {
    return User.newBuilder()
        .withFirstName(firstName)
        .withLastName(lastName)
        .withEmail(email)
        .withPassword(password)
        .build();
  }

  @JsonPOJOBuilder
  public static final class Builder {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

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

    public CreateUser build() {
      return new CreateUser(this);
    }
  }
}
