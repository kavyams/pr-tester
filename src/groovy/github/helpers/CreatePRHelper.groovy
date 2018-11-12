package groovy.github.helpers

import groovy.github.constants.Constants
import groovy.github.utils.PullRequestResponseParserUtils
import groovy.github.utils.RestUtils
import groovy.github.variables.EnvironmentVariables
import groovyx.net.http.ContentType

class CreatePRHelper {

    def public static makeCreatePRCall(owner, repo, jsonObject, query = [:]) {
        def baseUrl = Constants.GITHUB_API_URL;
        def path = "/repos/" + owner + "/" + repo + "/pulls";
        def headers = ["Authorization": "token " + EnvironmentVariables.GITHUB_TOKEN]
        return RestUtils.postRequest(baseUrl, path, query, jsonObject,
                ContentType.JSON, headers)
    }

    public static createPR(owner, repo, jsonObject, response = null) {
        if (response == null) {
            response = makeCreatePRCall(owner, repo, jsonObject)
        }
        def keysToExtract = ["title", "user", "base", "head", "body", "number"]
        return PullRequestResponseParserUtils.parseGitHubServerResponse(response, keysToExtract)

    }
}
