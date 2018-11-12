GitHub PR API Automation Framework
====================

This is a purpose built framework designed to test Github PR APIs. It does the following -
* This extensible framework allows users to add tests against Github APIs easily by separating test setup logic from business logic. 
* It provides an implementation of Groovy Object that the user can use to populate any data fields of interest.
* It has the ability to run tests in parallel. 
* It also provides meaningful error messages and allows for configurable retries in case of failures.

The framework does not do the following -
* Define extensive data objects that include all the information present in an API response. Instead, it gives users the flexibility to configure important parts of the response in the form of an extensible data object interface.
* It only handles pull requests to GitHub repositories that do not require authentication for the purposes of this demo.
* It makes live network calls and asserts on real API responses instead of using mock data for the purposes of this demo
* It is outside the scope of this test setup to run these tests on every new PR to the specified repo.

Critical use cases
-------------

Below are the most important use cases that I have identified to test Github's PR APIs
* 1 List PRs
  * a) Tests to verify that the API returns a list of open PRs by default, and closed or all PRs if state is specified as a parameter.
  * b) Tests to verify that PRs are listed in descending order of PR creation date by default.
  * c) Tests to verify that PRs are sorted by query parameters such as sort and direction. For e.g. if sort = popularity and direction = asc, the API returns PRs in increasing order of comment count
* 2 Single PRs
  * a) Tests to verify various attributes on the PR such as number, author,title, _ links etc.
  * b) Tests to verify the value of the merge commit attribute based on whether the PR is mergeable, was merged, rebased or squashed.
* 3 Create PRs
    * a) Tests to verify that a PR could be successfully created if all the required information was specified correctly.
    * b) Tests to verify that a PR could not be merged due to the expected error when all required information was not specified correctly.
    * c) Tests to verify that non-required input attributes work as expected while creating a PR
* 4 Updating PRs
    * a) Tests to verify that updated attributes are reflected as expected when a PR is updated with specified attributes.
    * b) Tests to verify that head ref can't be updated.
    * c) Tests to verify that base ref can't be updated to a branch in a different repository.
    * d) Tests to close a PR via the update API.
* 5 List commits on a PR
    * a) Tests to verify various commit attributes such as author, message, comment count etc.
    * b) Tests to verify that a maximum of 250 commits are returned by default.
* 6 List PR files
    * a) Tests to verify various file attributes such as filename, status, additions, deletions, changes etc.
    * b) Tests to verify that a maximum of 300 files are returned by default.
* 7 Verify if a PR has been merged
    * a) Tests to verify that the API returns HTTP 204 if a PR has been merged.
    * b) Tests to verify that the API returns HTTP 404 if a PR has not been merged.
* 8 Merge a PR
    * a) Tests to verify that a PR can be successfully merged if all the necessary information is provided correctly.
    * b) Tests to verify that a PR could not be merged either due to wrong usage of the API (405 Method not found) or a merge conflict (409 Conflict).
    
Implemented use cases
-------------
I have implemented only a subset of the above usecases. I picked 1a, 1b, 1c, 2a, 3a, 3b, 4a and 4d with the following criteria in mind. 
* I wanted to showcase the framework's ability to handle different HTTP Methods (GET, PUT and PATCH). So, I picked out read APIs such as List PR, Single PR, and write APIs such as create PR and update PR.
* I wanted to demonstrate the framework's ability to multiplex test parameter combinations easily. 1a and 1b are good examples of how this framework's users can specify multiple query parameters to an API with ease.
* I wanted to throw light on the framework's ability to allow users to flexibly parse only the pieces of data that they want to assert on. All the testcases use this feature extensively. It is heavily demonstrated in 3a and 4a.
* I wanted to demonstrate the framework's ability to use modular cleanup blocks where necessary. This is demonstrated by 3a and 4a as well.

Running the tests
-------------
* Install gradle
>>     brew install gradle
* In order for create PR and update PR tests to work, you will need to set up a GITHUB_TOKEN to get write access to the repo. You can use my access token for the purposes of this demo by running the following command in your shell. You will find <token> in your Slack message.
>>     export GITHUB_TOKEN=<token>
* You can now run all tests using the following command
>>     ./gradlew clean test
* You can run a single test using the following command
>>    ./gradlew test --tests <class name>.<test name>

for e.g.
>>  ./gradlew test --tests "groovy.github.ListPRTests.Assert that listed PRs match expected state"
