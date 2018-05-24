package com.nfitton.demostructure.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.nfitton.demostructure.users.UserEntity;
import com.nfitton.demostructure.users.api.v1.bean.UserDto;

class UserAssertion {

  static void assertUserEntityAttributesEqual(UserEntity getUser1, UserEntity getUser2) {
    assertThat(getUser1.getEmail()).isEqualToIgnoringCase(getUser2.getEmail());
    assertThat(getUser1.getFirstName()).isEqualToIgnoringCase(getUser2.getFirstName());
    assertThat(getUser1.getLastName()).isEqualToIgnoringCase(getUser2.getLastName());
  }

  static void assertUserAttributesEqual(UserDto user1, UserEntity user2) {
    assertUserEntityAttributesEqual(user1.toUser(), user2);
  }
}
