package com.project.atiperatask.controllers;

import com.project.atiperatask.services.GitApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class GitApiController {
    private final GitApiService gitApiService;

    public GitApiController(GitApiService gitApiService) {
        this.gitApiService = gitApiService;
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getUserRepos(@PathVariable String userName) {
        return ResponseEntity.ok(gitApiService.userRepos(userName));
    }
}
