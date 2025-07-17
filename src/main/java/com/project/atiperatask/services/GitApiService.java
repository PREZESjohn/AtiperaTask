package com.project.atiperatask.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.atiperatask.api.GithubApiClient;
import com.project.atiperatask.models.RepositoryResp;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GitApiService {
    private final GithubApiClient githubApiClient;

    public GitApiService(GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient;
    }

    public List<RepositoryResp> userRepositories(String username) {
        List<JsonNode> repos = githubApiClient.getUserRepos(username);
        List<RepositoryResp> reposResp = new ArrayList<>();
        for (JsonNode repo : repos) {
            if(!repo.get("fork").asBoolean()){
                String repoName = repo.get("name").asText();
                reposResp.add(new RepositoryResp(repoName,username, githubApiClient.getRepoBranches(username, repoName)));
            }
        }
        return reposResp;
    }
}
