package com.project.atiperatask.models;

import java.util.List;

public record RepositoryResp(String name, String ownerName, List<BranchResp> branches){}
