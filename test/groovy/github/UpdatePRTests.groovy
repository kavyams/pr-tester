package groovy.github

import com.anotherchrisberry.spock.extensions.retry.RetryOnFailure
import groovy.github.helpers.CreatePRHelper
import groovy.github.helpers.UpdatePRHelper
import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
@RetryOnFailure(times = 2, delaySeconds = 10)
class UpdatePRTests extends Specification {


    def setupSpec() {
        //do any overall test set up necessary here
    }

    @Unroll("('#owner', '#repo', '#jsonObject')")
    def "Assert changes to PR"() {
        def pullRequestObject = CreatePRHelper.createPR(owner, repo,
                [title: "Awesome new feature", head: "random",
                 base : "master", body: "This is a body"])

        log.info("Asserting success on updating a PR for owner(%s), repo(%s),"
                + ".\n ", owner, repo);

        def updatePullRequestObject = UpdatePRHelper.updatePR(owner, repo,
                jsonObject, pullRequestObject.getProperty("number"))
        expect:
        assert updatePullRequestObject.getProperty("title") == jsonObject.get("title")
        assert updatePullRequestObject.getProperty("head").get("ref") ==
                pullRequestObject.getProperty("head").get("ref")
        assert updatePullRequestObject.getProperty("base").get("ref") == jsonObject.get("base")
        assert updatePullRequestObject.getProperty("body") == jsonObject.get("body")
        assert updatePullRequestObject.getProperty("user").get("login") == owner
        assert updatePullRequestObject.getProperty("number") ==
                pullRequestObject.getProperty("number")


        cleanup:
        UpdatePRHelper.closePR(owner, repo, ["title": "new title",
                                             "body" : "updated body",
                                             "state": "closed",
                                             "base" : "master"],
                pullRequestObject.getProperty("number"))

        where:
        [owner, repo, jsonObject] << [
                ["kavyams"],
                ["RandomPractice"],
                [[title: "Awesome new updated feature",
                  base : "master", body: "This is an updated body",
                  state: "open"]]
        ].combinations()


    }
}
