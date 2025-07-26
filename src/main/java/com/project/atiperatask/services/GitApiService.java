package com.project.atiperatask.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.atiperatask.api.GithubApiClient;
import com.project.atiperatask.models.RepositoryReq;
import com.project.atiperatask.models.RepositoryResp;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GitApiService {
    private final GithubApiClient githubApiClient;

    public GitApiService(GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient;
    }

    public List<RepositoryResp> userRepositories(String username) {
        var repos = githubApiClient.getUserRepos(username);
        List<RepositoryResp> reposResp = repos.stream()
                .filter(repositoryReq -> !repositoryReq.fork())
                .map(repositoryReq ->
                        new RepositoryResp(
                                repositoryReq.name(),
                                repositoryReq.ownerName().login(),
                                githubApiClient.getRepoBranches(repositoryReq.ownerName().login(), repositoryReq.name())))
                .toList();
        return reposResp;
    }
}
