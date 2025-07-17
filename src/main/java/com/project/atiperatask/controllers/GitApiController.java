package com.project.atiperatask.controllers;

import com.project.atiperatask.services.GitApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class GitApiController {
    private final GitApiService gitApiService;

    public GitApiController(GitApiService gitApiService) {
        this.gitApiService = gitApiService;
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getUserRepos(@PathVariable String userName) {
        try{
            return ResponseEntity.ok(gitApiService.userRepos(userName));
        }catch (ResponseStatusException e){
            Optional<String> t = Optional.of("{'status':'404','message':'User was not found.'}");
            return ResponseEntity.of(t);
        }
    }
}
