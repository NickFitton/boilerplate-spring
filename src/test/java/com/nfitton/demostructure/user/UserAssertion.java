package com.nfitton.demostructure.user;

import com.nfitton.demostructure.users.User;
import com.nfitton.demostructure.users.api.v1.bean.GetUser;

import static org.assertj.core.api.Assertions.assertThat;

class UserAssertion {

  static void assertGetUserAttributesEqual(GetUser getUser1, GetUser getUser2) {
    assertThat(getUser1.getEmail()).isEqualToIgnoringCase(getUser2.getEmail());
    assertThat(getUser1.getFirstName()).isEqualToIgnoringCase(getUser2.getFirstName());
    assertThat(getUser1.getLastName()).isEqualToIgnoringCase(getUser2.getLastName());
  }

  static void assertUserAttributesEqual(GetUser user1, User user2) {
    assertGetUserAttributesEqual(user1, new GetUser(user2));
  }
}
