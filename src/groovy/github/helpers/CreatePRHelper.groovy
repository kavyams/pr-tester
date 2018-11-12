package groovy.github.helpers

import groovy.github.constants.Constants
import groovy.github.utils.PullRequestResponseParserUtils
import groovy.github.utils.RestUtils
import groovy.github.variables.EnvironmentVariables
import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import javax.naming.ConfigurationException

@Slf4j
class CreatePRHelper {

    def public static makeCreatePRCall(owner, repo, jsonObject, query = [:]) {
        def baseUrl = Constants.GITHUB_API_URL;
        def path = "/repos/" + owner + "/" + repo + "/pulls";
        def github_token = EnvironmentVariables.GITHUB_TOKEN
        if (github_token == null) {
            throw new ConfigurationException("Please set environment variable" +
                    " GITHUB_TOKEN before running tests")
        }
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
