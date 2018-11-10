GitHub PR API Automation Framework
====================

This is a purpose built framework designed to test GitHub PR APIs. It does the following -
* This extensible framework allows users to add tests 
against GitHub APIs easily by separating test setup logic from business logic. 
* It provides hooks for extending data objects. Users can flesh out data objects with as much information as they deem
necessary
* It runs tests in parallel. 
* It also provides meaningful error messages and allows for configurable retries in case of failures.

The framework does not do the following -
* Define extensive data objects that include all the information present in an API response. Instead, it gives users the
flexibility to configure important parts of the response in the form of extensible data object interfaces.
* It only handles pull requests to GitHub repositories that do not require authentication for the purposes of this demo.

no mock data
over the network calls
not set up to run on each new PR to repo