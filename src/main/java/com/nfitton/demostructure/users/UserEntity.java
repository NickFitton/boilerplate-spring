package com.nfitton.demostructure.users;

import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;

  private UserEntity() {}

  private UserEntity(Builder builder) {
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
   * Creates a {@code UserEntity.Builder} from the current {@code user}.
   * @return A {@code UserEntity.builder} with all the variables of the current {@code UserEntity}.
   */
  public Builder copy() {
    return UserEntity.newBuilder()
        .withId(this.getId())
        .withFirstName(this.getFirstName())
        .withLastName(this.getLastName())
        .withEmail(this.getEmail())
        .withPassword(this.getPassword())
        .withCreatedAt(this.getCreatedAt())
        .withUpdatedAt(this.getUpdatedAt());
  }

  public static final class Builder {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
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

    public Builder withDeletedAt(ZonedDateTime val) {
      deletedAt = val;
      return this;
    }

    public UserEntity build() {
      return new UserEntity(this);
    }
  }
}
