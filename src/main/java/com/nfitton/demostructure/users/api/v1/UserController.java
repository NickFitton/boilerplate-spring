package com.nfitton.demostructure.users.api.v1;

import com.nfitton.demostructure.users.UserService;
import com.nfitton.demostructure.users.api.v1.bean.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Create a user with the given {@code UserDto}.
   *
   * @param createUserMono contains all the information needed to create a new user.
   * @return A {@code GetUser} with the response status of {@code CREATED}.
   */
  @PostMapping
  public Mono<ResponseEntity<UserDto>> createUser(@RequestBody Mono<UserDto> createUserMono) {
    return createUserMono
        .map(UserDto::toUser)
        .flatMap(user -> userService.createUser(user))
        .map(UserDto::fromEntity)
        .map(userDto -> ResponseEntity.status(201).body(userDto));
  }

  @GetMapping(path = "/{userId}")
  public Mono<UserDto> getUser(@PathVariable UUID userId) {
    return userService.getUser(userId).map(UserDto::fromEntity);
  }

  /**
   * Update a {@code UserEntity} of the given UUID with the given {@code UpdateUser} class.
   *
   * @param userId is the userId to update by.
   * @param updateUserMono is the new information to update the user with.
   * @return A {@code GetUser} with the updated user information.
   */
  @PutMapping(path = "/{userId}")
  public Mono<UserDto> putUser(
      @PathVariable UUID userId, @RequestBody Mono<UserDto> updateUserMono) {
    return updateUserMono
        .map(UserDto::toUser)
        .flatMap(user -> userService.putUser(userId, user))
        .map(UserDto::fromEntity);
  }

  @DeleteMapping(path = "/{userId}")
  public ResponseEntity<UserDto> deleteUser(@PathVariable UUID userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping()
  public Flux<UserDto> getAllUsers(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    return userService.getAllUsers(page, size).map(UserDto::fromEntity);
  }
}
