package com.project.atiperatask.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BranchReq(
        @JsonProperty("name") String name,
        @JsonProperty("commit") Commit commit
) {
   public record Commit(@JsonProperty("sha") String sha){}
}
