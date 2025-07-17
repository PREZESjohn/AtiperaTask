package com.project.atiperatask.models;

public class Branch {
    private String branchName;
    private String branchLastCommitSsh;

    public Branch(String branchName, String branchLastCommitSsh) {
        this.branchName = branchName;
        this.branchLastCommitSsh = branchLastCommitSsh;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getBranchLastCommitSsh() {
        return branchLastCommitSsh;
    }
}
