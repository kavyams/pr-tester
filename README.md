GitHub PR API Automation Framework
==================================

This Groovy-based test framework is designed to test Github PR APIs. It does the following -
* It allows users to add tests against Github APIs easily by separating test setup logic from business logic. This includes below functionality: 
    * Separate out functionality for making HTTP API calls.
    * Allow using API keys/credentials for authenticated calls.
    * Fail loudly in case of errors with HTTP communication.
* It provides an implementation of Groovy Object that users can use to populate any data fields of interest.
    * Instead of returning bare JSON responses from the API, the framework makes it easier to visualize the data object being returned.
* It has the ability to run tests in parallel. 
* It also provides meaningful error messages and allows for configurable retries in case of failures.

The framework does not do the following -
* Define extensive data objects that include all the information present in an API response. Instead, it gives users the flexibility to configure important parts of the response in the form of an extensible data object interface.
* It makes live network calls and asserts on real API responses instead of using mock data for the purposes of this demo
* It is outside the scope of this test setup to run these tests on every new PR to the specified repo.

Technologies used
-----------------
* **Spock test framework**: Spock is a JUnit based test framework that helps with creating easy to write, readable tests.
It provides surrounding context around a test, thus resulting in easily understanding how to fix a failure. It also has built in 
mocking and stubbing capabilities.
* **Groovy programming language**: Spock framework supports both Java and Groovy. I picked Groovy because it 
has better readability and results in more compact code.
* **Gradle**: Gradle makes dependency management easier and allows for some portability. It also provides the ability to run 
tests via the command line.

Primary use cases of the framework
------------------------------------
* Write tests that verify functionality of read APIs and write APIs.
* Multiplex over query parameters easily.
* Run the same test over multiple combinations of test parameters without having to duplicate code.
* Allow for clean up in case of data modification.
* Allow for retries in case of failure.
* Allow for categorization of tests into different buckets.

Possible Test Cases
-------------------

Below are the most important use cases that I have identified to test Github's PR APIs

1. List PRs    
    1. Tests to verify that the API returns a list of open PRs by default, and closed or all PRs if state is specified as a parameter.
    2. Tests to verify that PRs are listed in descending order of PR creation date by default.
    3.  Tests to verify that PRs are sorted by query parameters such as sort and direction. For e.g. if `sort = popularity` and `direction = asc`, the API returns PRs in increasing order of comment count
2. Single PRs
    1. Tests to verify various attributes on the PR such as number, author, title, `_` links etc.
    2. Tests to verify the value of the merge commit attribute based on whether the PR is mergeable, was merged, rebased or squashed.
3. Create PRs
    1. Tests to verify that a PR could be successfully created if all the required information was specified correctly.
    2. Tests to verify that a PR could not be merged due to the expected error when all required information was not specified correctly.
    3. Tests to verify that non-required input attributes work as expected while creating a PR
4. Updating PRs
    1. Tests to verify that updated attributes are reflected as expected when a PR is updated with specified attributes.
    2. Tests to verify that head ref can't be updated.
    3. Tests to verify that base ref can't be updated to a branch in a different repository.
    4. Tests to close a PR via the update API.
5. List commits on a PR
    1. Tests to verify various commit attributes such as author, message, comment count etc.
    2. Tests to verify that a maximum of 250 commits are returned by default.
6. List PR files
    1. Tests to verify various file attributes such as filename, status, additions, deletions, changes etc.
    2. Tests to verify that a maximum of 300 files are returned by default.
7. Verify if a PR has been merged
    1. Tests to verify that the API returns `HTTP 204` if a PR has been merged.
    2. Tests to verify that the API returns `HTTP 404` if a PR has not been merged.
8. Merge a PR
    1. Tests to verify that a PR can be successfully merged if all the necessary information is provided correctly.
    2. Tests to verify that a PR could not be merged either due to wrong usage of the API (`405 Method not found`) or a merge conflict (`409 Conflict`).
    
    
Implementation Details
----------------------
I have implemented only a subset of the above test cases. I picked 1(i), 1(ii), 1(iii), 2(i), 3(i), 3(ii), 4(i) and 4(iv) with the following criteria in mind. 
1. I wanted to showcase the framework's ability to handle different HTTP Methods (`GET`, `PUT` and `PATCH`). So, I picked out read APIs such as List PR, Single PR, and write APIs such as create PR and update PR.
2. I wanted to demonstrate the framework's ability to multiplex test parameter
 combinations easily. 1(i) and 1(ii) are good examples of how this framework's users can specify multiple query parameters to an API with ease.
3. I wanted to throw light on the framework's ability to allow users to flexibly parse only the pieces of data that they want to assert on. All the testcases use this feature extensively. It 
is heavily demonstrated in 3(i) and 4(i).
4. I wanted to demonstrate the framework's ability to use modular cleanup blocks where necessary. This is demonstrated by 3(i) and 4(ii) as well.

Running the tests
-----------------
* In order for create PR and update PR tests to work, you will need to set up a GITHUB_TOKEN to get write access to the repo. You can use my access token for the purposes of this demo by running the following command in your shell. You will find <token> in your email.

        export GITHUB_TOKEN=<token>

* You can now run all tests using the following command
        
        ./gradlew clean test

* You can run a single test using the following command

        ./gradlew test --tests <class name>.<test name>

for e.g.

        ./gradlew test --tests "groovy.github.ListPRTests.Assert that listed PRs match expected state"
