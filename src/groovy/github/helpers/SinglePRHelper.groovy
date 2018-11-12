package groovy.github.helpers

import groovy.github.constants.Constants
import groovy.github.utils.PullRequestResponseParserUtils
import groovy.github.utils.RestUtils
import groovyx.net.http.ContentType

class SinglePRHelper {
    def private static makeSinglePRCall(owner, repo, prNumber, query = [:]) {
        def baseUrl = Constants.GITHUB_API_URL;
        def path = "/repos/" + owner + "/" + repo + "/pulls/" + prNumber;
        return RestUtils.getResponse(baseUrl, path, query, ContentType.JSON)
    }

    def static getSinglePR(owner, repo, prNumber) {
        def singlePRResponse = makeSinglePRCall(owner, repo, prNumber)
        def keysToExtract = ["number", "title", "user", "_links"]
        return PullRequestResponseParserUtils.parseGitHubServerResponse(singlePRResponse, keysToExtract)

    }

}
