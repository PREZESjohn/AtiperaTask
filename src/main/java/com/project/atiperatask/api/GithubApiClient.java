package com.project.atiperatask.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.atiperatask.exceptions.UserNotFoundException;
import com.project.atiperatask.models.BranchResp;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class GithubApiClient {
    private final RestClient restClient;

    public GithubApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<JsonNode> getUserRepos(String username){
        try{
            JsonNode[] jsonNodes = restClient.get()
                    .uri("/users/{username}/repos", username)
                    .retrieve()
                    .body(JsonNode[].class);
            return jsonNodes != null ? List.of(jsonNodes) : List.of();
        }catch (HttpClientErrorException e){
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new UserNotFoundException("User " + username + " not found");
            }
            throw e;
        }
    }

    public List<BranchResp> getRepoBranches(String username, String repo) {
        JsonNode[] jsonNodes = restClient.get()
                .uri("/repos/{username}/{repository}/branches",username,repo)
                .retrieve()
                .body(JsonNode[].class);
        List<BranchResp> branches = new ArrayList<>();
        if(jsonNodes != null){
            for(JsonNode branch : jsonNodes){
                String branchName = branch.get("name").asText();
                String branchLastCommitSha = branch.get("commit").get("sha").asText();
                branches.add(new BranchResp(branchName, branchLastCommitSha));
            }
        }
        return branches;
    }
}
