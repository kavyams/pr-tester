package groovy.github.helpers

import groovy.github.constants.Constants
import groovy.github.utils.RestUtils
import groovyx.net.http.ContentType

class ListPRHelper {
    def private static makeListPRCall(owner, repo, query = [:]) {
        def baseUrl = Constants.GITHUB_API_URL;
        def path = "/repos/" + owner + "/" + repo + "/pulls";
        return RestUtils.getResponse(baseUrl, path, query, ContentType.JSON)
    }

    def private static getPrObjects(owner, repo, keysToExtract, query = [:]) {
        def response = makeListPRCall(owner, repo, query)
        return PullRequestResponseParser.parseGitHubServerResponseArray(response, keysToExtract)
    }

    def static getOrderedCommitDates(owner, repo) {
        String keyToExtract = "created_at"
        def prObjects = getPrObjects(owner, repo, Arrays.asList(keyToExtract))
        return PullRequestResponseParser.parseSpecificKey(prObjects, keyToExtract)
    }

    def static getPRStates(owner, repo, state) {
        def query = ['state': state]
        String keyToExtract = "state"
        def prObjects = getPrObjects(owner, repo, Arrays.asList(keyToExtract), query)
        return PullRequestResponseParser.parseSpecificKey(prObjects, keyToExtract)
    }

}
