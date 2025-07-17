package com.project.atiperatask.services;

import com.project.atiperatask.models.Branch;
import com.project.atiperatask.models.Repository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GitApiService {
    private final RestTemplate restTemplate;

    public GitApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Repository> userRepos(String userName){

        String url = "https://api.github.com/users/"+userName+"/repos";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        JSONArray jsonArray = new JSONArray(response.getBody());
        List<Repository> repos = new ArrayList<>();
        String branchesUrl;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject objects = jsonArray.getJSONObject(i);
            if(!objects.getBoolean("fork")){
                branchesUrl=objects.getString("branches_url");
                repos.add(new Repository(userName,objects.getString("name"),repoBranches(branchesUrl)));
            }

        }

        return repos;
    }

    public List<Branch> repoBranches(String branchesUrl){
        String branchesUrlStrip=branchesUrl.replaceAll("(?s)\\{.*?}", "");
        ResponseEntity<String> response = restTemplate.getForEntity(branchesUrlStrip, String.class);
        JSONArray jsonArray = new JSONArray(response.getBody());
        List<Branch> tempBranches = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject objects = jsonArray.getJSONObject(i);
            tempBranches.add(new Branch(objects.getString("name"),objects.getJSONObject("commit").getString("sha")));
        }
        return tempBranches;
    }
}
