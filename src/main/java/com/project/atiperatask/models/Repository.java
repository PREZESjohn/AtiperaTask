package com.project.atiperatask.models;

import java.util.List;

public class Repository {
    private String ownerName;
    private String repoName;
    private List<Branch> branches;

    public Repository(String ownerName, String repoName, List<Branch> branches) {
        this.ownerName = ownerName;
        this.repoName = repoName;
        this.branches = branches;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getRepoName() {
        return repoName;
    }

    public List<Branch> getBranches() {
        return branches;
    }
}
