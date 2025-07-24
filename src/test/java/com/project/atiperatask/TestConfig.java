package com.project.atiperatask;

import com.project.atiperatask.api.GithubApiClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@TestConfiguration
public class TestConfig {
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8089")
                .build();
    }

    @Bean
    public GithubApiClient githubApiClient(RestClient restClient) {
        return new GithubApiClient(restClient);
    }
}
