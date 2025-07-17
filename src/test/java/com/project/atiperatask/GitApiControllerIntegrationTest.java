package com.project.atiperatask;

import com.project.atiperatask.models.RepositoryResp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnRepositoriesWithBranchesForExistingUser() {
        String username = "octocat";

        ResponseEntity<RepositoryResp[]> response = restTemplate.getForEntity("/api/v1/{username}", RepositoryResp[].class, username);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        RepositoryResp[] repositories = response.getBody();
        assertThat(repositories).hasSizeGreaterThan(0);

        boolean hasHelloWorld = false;
        for (RepositoryResp repo : repositories) {
            if ("Hello-World".equals(repo.name())) {
                hasHelloWorld = true;
                assertThat(repo.ownerName()).isEqualTo("octocat");
                assertThat(repo.branches()).isNotEmpty();
                assertThat(repo.branches().get(0).name()).isNotBlank();
                assertThat(repo.branches().get(0).lastCommitSha()).isNotBlank();
                break;
            }
        }
        assertThat(hasHelloWorld).isTrue();
    }
}
