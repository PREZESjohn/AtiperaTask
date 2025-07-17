package com.project.atiperatask.controllers;

import com.project.atiperatask.models.RepositoryResp;
import com.project.atiperatask.services.GitApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class GitApiController {
    private final GitApiService gitApiService;

    public GitApiController(GitApiService gitApiService) {
        this.gitApiService = gitApiService;
    }

    @GetMapping("/{userName}")
    public List<RepositoryResp> getUserRepos(@PathVariable String userName) {
        return gitApiService.userRepositories(userName);
    }
}
