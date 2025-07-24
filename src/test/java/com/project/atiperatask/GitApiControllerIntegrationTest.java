package com.project.atiperatask;


import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
properties = {"spring.main.allow-bean-definition-overriding=true"},
classes = {TestConfig.class})
@EnableWireMock(@ConfigureWireMock(port = 8089))
@AutoConfigureWebTestClient
public class GitApiControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Value("${wiremock.server.baseUrl}")
    private String wireMockUrl;

    @Test
    void shouldReturnRepositoriesWithBranchesForExistingUser() {
        String username = "octocat";

        String reposResponse = """
                [
                    {
                        "name": "hello-world",
                        "owner": {"login": "octocat"},
                        "fork": false
                    },
                    {
                        "name": "forked-repo",
                        "owner": {"login": "octocat"},
                        "fork": true
                    }
                ]
                """;
        String branchesResponse = """
                [
                    {
                        "name": "main",
                        "commit": {"sha": "7fd1a60b01f91b314f59955a4e4d4e80c135d252"}
                    }
                ]
                """;
        String expectedResponse = """
                [
                    {
                        "name": "hello-world",
                        "ownerName": "octocat",
                        "branches": [
                            {
                                "name": "main",
                                "lastCommitSha": "7fd1a60b01f91b314f59955a4e4d4e80c135d252"
                            }
                        ]
                    }
                ]
                """;
        WireMock.stubFor(get("/users/"+username+"/repos")
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(reposResponse)
                    .withStatus(HttpStatus.OK.value())));

        WireMock.stubFor(get("/repos/"+username+"/hello-world/branches")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(branchesResponse)
                        .withStatus(200)));

        webTestClient.get().uri("/api/v1/{username}", username)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String actualResponse = new String(response.getResponseBody());
                    JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
                });
    }

}
