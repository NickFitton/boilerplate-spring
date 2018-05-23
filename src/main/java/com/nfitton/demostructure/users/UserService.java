package com.nfitton.demostructure.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserService {

  private final UserDomain userDomain;

  @Autowired
  public UserService(UserDomain userDomain) {
    this.userDomain = userDomain;
  }

  public Mono<UserEntity> createUser(UserEntity user) {
    return userDomain.postUser(user);
  }

  public Mono<UserEntity> getUser(UUID userId) {
    return userDomain.getUser(userId);
  }

  public Flux<UserEntity> getAllUsers(int page, int size) {
    PageRequest request = PageRequest.of(page, size);
    return getAllUsers(request);
  }

  private Flux<UserEntity> getAllUsers(PageRequest request) {
    return userDomain.getAllUsers(request);
  }

  /**
   * Update a user with the given {@code UserEntity}.
   *
   * @param user is the new user details for the id of the user.
   * @return A mono of the updated user.
   */
  private Mono<UserEntity> putUser(UserEntity user) {
    return userDomain
        .getUser(user.getId())
        .map(
            returnedUser ->
                returnedUser
                    .copy()
                    .withFirstName(user.getFirstName())
                    .withLastName(user.getLastName())
                    .withEmail(user.getEmail())
                    .build())
        .flatMap(userDomain::putUser);
  }

  public Mono<UserEntity> putUser(UUID userId, UserEntity user) {
    return putUser(user.copy().withId(userId).build());
  }

  public void deleteUser(UUID userId) {
    userDomain.deleteUser(userId);
  }
}
