package com.project.atiperatask.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.atiperatask.exceptions.UserNotFoundException;
import com.project.atiperatask.models.BranchReq;
import com.project.atiperatask.models.BranchResp;
import com.project.atiperatask.models.RepositoryReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class GithubApiClient {
    private final RestClient restClient;
    private static final Logger logger = LoggerFactory.getLogger(GithubApiClient.class);

    public GithubApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<RepositoryReq> getUserRepos(String username){
        logger.info("Fetch repositories for {}", username);
        RepositoryReq[] repositoryArray = restClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .onStatus(status -> status.value() == 404, ((request, response) -> {
                    logger.info("Received 404 response for user: {}", username);
                    throw new UserNotFoundException("User " + username + " not found");
                }))
                .onStatus(status -> status.value() == 204, ((request, response) -> {
                    logger.info("Received 204 response for user: {}", username);
                    throw new IllegalStateException("GitHub API returned null for repositories");
                }))
                .body(RepositoryReq[].class);
        logger.info("Fetched {} repositories for user: {}", repositoryArray.length, username);
        return List.of(repositoryArray);
    }

    public List<BranchResp> getRepoBranches(String username, String repo) {
        logger.info("Fetch branches of {} for {}", repo, username);
        BranchReq[] branchArray = restClient.get()
                .uri("/repos/{username}/{repository}/branches",username,repo)
                .retrieve()
                .onStatus(status -> status.value() == 204, ((request, response) -> {
                    logger.info("Received 204 response for branches of repository: {}", repo);
                    throw new IllegalStateException("GitHub API returned null for branch");
                }))
                .body(BranchReq[].class);
        List<BranchResp> branches = List.of(branchArray).stream()
                .map(branch -> new BranchResp(branch.name(),branch.commit().sha()))
                .toList();
        logger.info("Fetched {} branches for repository: {}/{}", branches.size(), username, repo);
        return branches;
    }
}
