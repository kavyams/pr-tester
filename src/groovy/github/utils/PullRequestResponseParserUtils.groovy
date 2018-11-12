package groovy.github.utils

import groovy.github.valueObjects.PullRequestObject
import groovy.util.logging.Slf4j

@Slf4j
class PullRequestResponseParserUtils {

    /**
     * Extract fields from the server response and populate PullRequestObject
     * @param responseFromServer
     * @param keys to extract
     * @return
     */
    public
    static List<PullRequestObject> parseGitHubServerResponseArray(responseFromServer, keys) {
        List<PullRequestObject> pullRequestObjects = new ArrayList<PullRequestObject>()
        for (int i = 0; i < responseFromServer.size(); i++) {
            PullRequestObject pullRequestObject = parseGitHubServerResponse(responseFromServer[i], keys)
            pullRequestObjects.add(pullRequestObject)
        }
        return pullRequestObjects
    }

    public
    static PullRequestObject parseGitHubServerResponse(responseFromServer, keys) {
        PullRequestObject pullRequestObject = new PullRequestObject()
        for (String key in keys) {
            pullRequestObject.setProperty(key, responseFromServer.get(key))
        }
        return pullRequestObject
    }

    public
    static parseSpecificKey(pullRequestObjects, keyToExtract) {
        List<Object> values = new ArrayList<Object>()
        for (PullRequestObject pullRequestObject in pullRequestObjects) {
            values.add(pullRequestObject.getProperty(keyToExtract));
        }
        return values
    }
}

