# Java Rest Assured API Automation

## Technologies
- Java openjdk version "11.0.16.1" 2022-08-12
- Apache Maven 3.8.6
- Rest Assured 5.2.0
- JUnit 5.9.1
- Allure reports 2.19.0

## Running the tests
In order to run tests you need to:

- clone the project
- `run mvn clean install -DskipTests`
- `run mvn test`

## Run tests and build/serve test results
In order to have Allure reports runnable in local, please install it

`brew install allure`

To run tests and generate results in the same command:
- `mvn clean verify allure:serve`

## Run tests against specific environment
To specify test environment use `-Deut={env}`, i.e. `-Deut=local`

## Run Allure docker locally
- `docker-compose up allure allure-ui`
- (one time deal) `chmod +x create-allure-report.sh`
- run bash script from above (this will run all the integration tests, send the results to the allure server and then generate a report)
`./create-allure-report.sh`
- open `http://localhost:5252/allure-docker-service-ui/`
- open default project
- or you can directly visit the link for default project

  `http://localhost:5252/allure-docker-service-ui/projects/default`

## Pre-commit
To get started with pre-commit we need to install the pre-commit package.

`brew install pre-commit`

Now the pre-commit hooks have been installed, you can use your normal `git add` and `git commit` workflow. The checks will run automatically upon calling the git commit command.
