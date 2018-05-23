package com.nfitton.demostructure.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
class UserDomain {
  @Autowired UserRepository userRepository;

  protected Mono<UserEntity> postUser(UserEntity user) {
    assertUserUnique(user);
    user =
        user.copy().withCreatedAt(ZonedDateTime.now()).withUpdatedAt(ZonedDateTime.now()).build();
    return Mono.just(userRepository.save(user));
  }

  protected Flux<UserEntity> getAllUsers(PageRequest request) {
    return Flux.fromIterable(userRepository.findAll(request));
  }

  protected Mono<Long> countUsers() {
    return Mono.just(userRepository.count());
  }

  protected Mono<UserEntity> getUser(UUID userId) {
    return Mono.just(userRepository.findById(userId))
        .flatMap(
            optionalUser ->
                optionalUser
                    .<Mono<? extends UserEntity>>map(Mono::just)
                    .orElseGet(
                        () ->
                            Mono.error(
                                new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "UserEntity with given id '" + userId.toString() + "' not found"))));
  }

  protected Mono<UserEntity> putUser(UserEntity updatedUser) {
    if (Objects.isNull(updatedUser.getId())) {
      return Mono.error(
          new ResponseStatusException(HttpStatus.BAD_REQUEST, "Put user must have a userId"));
    }
    assertUserUnique(updatedUser);
    return Mono.just(updatedUser)
        .filter(finUser -> userRepository.existsById(finUser.getId()))
        .switchIfEmpty(
            Mono.error(
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "UserEntity not found for userId: " + updatedUser.getId())))
        .map(user -> user.copy().withUpdatedAt(ZonedDateTime.now()).build())
        .map(userRepository::save);
  }

  protected Mono<UserEntity> patchUser(UUID userId, UserEntity user) {
    assertUserUnique(user);
    return getUser(userId)
        .map(
            returnedUser -> {
              UserEntity.Builder builder = returnedUser.copy();
              if (Objects.nonNull(user.getFirstName())) {
                builder.withFirstName(user.getFirstName());
              }
              if (Objects.nonNull(user.getLastName())) {
                builder.withLastName(user.getLastName());
              }
              if (Objects.nonNull(user.getEmail())) {
                builder.withEmail(user.getEmail());
              }
              return builder.build();
            })
        .flatMap(user1 -> Mono.just(userRepository.save(user1)));
  }

  protected void deleteUser(UUID userId) {
    if (userRepository.existsById(userId)) {
      userRepository.deleteById(userId);
    } else {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "UserEntity with given id '" + userId.toString() + "' not found");
    }
  }

  private void assertUserUnique(UserEntity user) {
    if (Objects.isNull(user.getId()) && userRepository.existsByEmail(user.getEmail())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "UserEntity with given email exists");
    } else {
      Optional<UserEntity> optionalUser = userRepository.findByEmail(user.getEmail());
      if (optionalUser.isPresent() && !optionalUser.get().getId().equals(user.getId())) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "UserEntity with given email exists");
      }
    }
  }
}
