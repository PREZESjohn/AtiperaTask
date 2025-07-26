package com.project.atiperatask.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RepositoryReq(
   @JsonProperty("name") String name,
   @JsonProperty("owner") Owner ownerName,
   @JsonProperty("fork") boolean fork
) {
   public record Owner(@JsonProperty("login") String login) {}
}
