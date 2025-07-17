package com.project.atiperatask.models;

public class Branch {
    private String branchName;
    private String branchLastCommitSha;

    public Branch(String branchName, String branchLastCommitSha) {
        this.branchName = branchName;
        this.branchLastCommitSha = branchLastCommitSha;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getBranchLastCommitSha() {
        return branchLastCommitSha;
    }
}
