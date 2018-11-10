package groovy.github.helpers

import groovy.github.constants.Constants
import groovy.github.utils.RestUtils
import groovyx.net.http.ContentType

class PullRequestHelper {
    def private static makeListPRCall(owner, repo, query = []) {
        def baseUrl = Constants.GITHUB_API_URL;
        def path = "/repos/" + owner + "/" + repo + "/pulls";
        return RestUtils.getResponse(baseUrl, path, query, ContentType.JSON)
    }

    def static getOrderedCommitDates(owner, repo) {
        def response = makeListPRCall(owner, repo)
        String keyToExtract = "created_at"
        def datesPerCommitHash = PullRequestResponseParser
                .parseGitHubServerResponseInOrder(response, Arrays.asList(keyToExtract));
        return PullRequestResponseParser.parseSpecificKey(datesPerCommitHash, keyToExtract)
    }

    def static getPRStates(owner, repo, state) {
        def query = ['state': state]
        def response = makeListPRCall(owner, repo, query)
        String keyToExtract = "state"
        def statePerCommitHash = PullRequestResponseParser
                .parseGitHubServerResponseInOrder(response, Arrays.asList(keyToExtract));
        return PullRequestResponseParser.parseSpecificKey(statePerCommitHash, keyToExtract)
    }

}
