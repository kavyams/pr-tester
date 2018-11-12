package groovy.github.helpers

import groovy.github.constants.Constants
import groovy.github.utils.PullRequestResponseParserUtils
import groovy.github.utils.RestUtils
import groovy.github.variables.EnvironmentVariables
import groovyx.net.http.ContentType

class UpdatePRHelper {

    public
    static makeUpdatePRCall(owner, repo, jsonObject, number, query = [:]) {
        def baseUrl = Constants.GITHUB_API_URL;
        def path = "/repos/" + owner + "/" + repo + "/pulls/" + number;
        def headers = ["Authorization": "token " + EnvironmentVariables.GITHUB_TOKEN]
        return RestUtils.patchRequest(baseUrl, path, query, jsonObject,
                ContentType.JSON, headers)
    }

    public static closePR(owner, repo, jsonObject, number) {
        makeUpdatePRCall(owner, repo, jsonObject, number)
    }

    public static updatePR(owner, repo, jsonObject, number) {
        def response = makeUpdatePRCall(owner, repo, jsonObject, number)
        def keysToExtract = ["title", "user", "base", "head", "body", "number"]
        return PullRequestResponseParserUtils.parseGitHubServerResponse(response, keysToExtract)
    }
}
