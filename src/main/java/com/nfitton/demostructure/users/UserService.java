package com.nfitton.demostructure.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserService {

  @Autowired private UserDomain userDomain;

  public Mono<User> createUser(User user) {
    return userDomain.postUser(user);
  }

  public Mono<User> getUser(UUID userId) {
    return userDomain.getUser(userId);
  }

  public Flux<User> getAllUsers(int page, int size) {
    PageRequest request = PageRequest.of(page, size);
    return getAllUsers(request);
  }

  private Flux<User> getAllUsers(PageRequest request) {
    return userDomain.getAllUsers(request);
  }

  /**
   * Update a user with the given {@code User}.
   * @param user is the new user details for the id of the user.
   * @return A mono of the updated user.
   */
  public Mono<User> putUser(User user) {
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

  public Mono<User> putUser(UUID userId, User user) {
    return putUser(user.copy().withId(userId).build());
  }

  public void deleteUser(UUID userId) {
    userDomain.deleteUser(userId);
  }

  public void deleteUser(User user) {
    deleteUser(user.getId());
  }
}
