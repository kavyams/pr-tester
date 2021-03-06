package groovy.github.variables

import groovy.github.constants.Constants

class EnvironmentVariables {
    public static GITHUB_TOKEN = getGithubToken()


    private static getGithubToken() {
        if (System.getenv(Constants.GITHUB_TOKEN) != null) {
            return System.getenv(Constants.GITHUB_TOKEN)
        } else {
            return null
        }
    }

}
