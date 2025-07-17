package com.project.atiperatask;

import com.project.atiperatask.models.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnRepositoriesWithBranchesForExistingUser() {
        String username = "octocat";

        ResponseEntity<?> response = restTemplate.getForEntity("api/v1/{username}", Repository[].class, username);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        List<Repository> repositories = (List<Repository>) response.getBody();
        assertThat(repositories).hasSizeGreaterThan(0);

        boolean hasHelloWorld = false;
        for (Repository repo : repositories) {
            if ("hello-world".equals(repo.getRepoName())) {
                hasHelloWorld = true;
                assertThat(repo.getOwnerName()).isEqualTo("octocat");
                assertThat(repo.getBranches()).isNotEmpty();
                assertThat(repo.getBranches().get(0).getBranchName()).isNotBlank();
                assertThat(repo.getBranches().get(0).getBranchLastCommitSsh()).isNotBlank();
                break;
            }
        }
        assertThat(hasHelloWorld).isTrue();
    }
}
