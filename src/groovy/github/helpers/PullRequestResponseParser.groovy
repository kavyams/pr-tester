package groovy.github.helpers

import groovy.util.logging.Slf4j

@Slf4j
class PullRequestResponseParser {

    /**
     * Extract a field from the server response and map it by commit hash
     * @param responseFromServer
     * @param keys to extract
     * @return
     */
    public
    static LinkedHashMap<String, ?> parseGitHubServerResponseInOrder(responseFromServer, keys) {
        try {
            LinkedHashMap<String, ?> prInfoPerCommitHash = new LinkedHashMap<String, ?>()
            for (int i = 0; i < responseFromServer.size(); i++) {
                HashMap<String, String> prInfo = new HashMap<String, String>()
                for (String key in keys) {
                    prInfo.put(key, responseFromServer[i].get(key))
                }
                prInfoPerCommitHash.put(responseFromServer[i].get("merge_commit_sha"), prInfo)
            }
            return prInfoPerCommitHash
        } catch (Exception ex) {
            log.error("Could not extract keys from response " + ex
                    .printStackTrace());
            throw new Exception("Could not extract keys from response " + ex
                    .printStackTrace());
        }

    }

    public
    static List<String> parseSpecificKey(valuesPerCommitHash, keyToExtract) {
        List<String> values = new ArrayList<String>()
        for (Map map in valuesPerCommitHash.values()) {
            values.add(map.get(keyToExtract));
        }
        return values
    }
}

