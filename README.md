# Custom GitHub API

This is a Spring Boot application that provides a REST API to list all non-fork repositories for a given GitHub user, including repository names, owner logins, and branch details (branch name and last commit SHA). It uses the GitHub API (v3) at https://api.github.com as the backing service. For non-existing users, it returns a 404 response with a structured error message.

## Features
- **Endpoint**: `GET /api/github/users/{username}/repos`
- **Response**: Returns a JSON array of repositories (excluding forks) with:
    - Repository name
    - Owner login
    - List of branches with their names and last commit SHAs
- **Error Handling**: Returns a 404 response for non-existing users in the format:
  ```json
  {
      "status": 404,
      "message": "User {username} not found"
  }
  ```
- **Integration Test**: Includes a single integration test verifying the happy path for a valid user (`octocat`), checking repository details and branch information without mocks.
- 
## Prerequisites
- Java 21
- Maven 3.8+
- Internet access to connect to the GitHub API (`https://api.github.com`)

## Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/github-repos.git
   ```
2. Navigate to the project directory:
   ```bash
   cd github-repos
   ```
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Usage
- **Endpoint**: Make a GET request to:
  ```
  http://localhost:8080/api/github/users/{username}/repos
  ```
  Replace `{username}` with a valid GitHub username (e.g., `octocat`).
- **Example Response** (for `octocat`):
  ```json
  [
      {
          "repositoryName": "hello-world",
          "ownerLogin": "octocat",
          "branches": [
              {
                  "name": "main",
                  "lastCommitSha": "7fd1a60b01f91b314f59955a4e4d4e80c135d252"
              }
          ]
      }
  ]
  ```
- **Error Response** (for non-existing user):
  ```json
  {
      "status": 404,
      "message": "User nonexistinguser not found"
  }
  ```

## Testing
The project includes a single integration test (`GithubControllerIntegrationTest`) that verifies the happy path:
- **Test Case**: Makes a GET request to `/api/github/users/octocat/repos`.
- **Assertions**:
    - Response status is `200 OK`.
    - Returns at least one repository (e.g., `hello-world`).
    - Verifies repository name, owner login (`octocat`), and at least one branch with a valid name and commit SHA.
- Run tests with:
  ```
  mvn test
  ```
## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)