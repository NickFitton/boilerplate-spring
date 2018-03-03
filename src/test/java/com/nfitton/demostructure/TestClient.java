package com.nfitton.demostructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.server.WebFilterChainProxy;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestClient {

  protected WebTestClient client;
  @Autowired private WebFilterChainProxy webFilterChainProxy;
  private TestClient(Builder builder) {
    client = builder.webTestClient.webFilter(webFilterChainProxy).build();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public WebTestClient getClient() {
    return client;
  }

  public static final class Builder {
    WebTestClient.ControllerSpec webTestClient;
    private List<Object> controllers;

    private Builder() {}

    public Builder withController(Object object) {
      if (Objects.isNull(controllers)) {
        controllers = new ArrayList<>();
      }
      controllers.add(object);
      return this;
    }

    public TestClient build() {
      webTestClient = WebTestClient.bindToController(controllers);
      return new TestClient(this);
    }
  }
}
