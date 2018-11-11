package groovy.github

import groovy.github.helpers.SinglePRHelper
import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
class SinglePRTests extends Specification {

    def setupSpec() {
        //do any set up necessary here
    }

    @Unroll("('#owner', '#repo', '#prNumber')")
    def "Assert on PR specific info such as author name, created date, and title"() {

        log.info("Asserting PR specific info for owner(%s), repo(%s), prNumber(%d)" + "\n ",
                owner, repo, prNumber);

        def pullRequestObject = SinglePRHelper.getSinglePR(owner, repo,
                prNumber);
        def expectedAuthor = "JoelEinbinder"
        def expectedTitle = "fix(types): fix return types where JSDoc and api.md disagree"
        def expectedLinks = new HashSet<String>(["self", "html", "issue",
                                                 "comments", "review_comments",
                                                 "review_comment", "commits", "statuses"])

        expect:
        assert pullRequestObject.getProperty("number") == prNumber
        assert pullRequestObject.getProperty("user").get("login") == expectedAuthor
        assert pullRequestObject.getProperty("title") == expectedTitle
        assert pullRequestObject.getProperty("_links").keySet() == expectedLinks
        where:
        [owner, repo, prNumber] << [
                ["GoogleChrome"],
                ["puppeteer"],
                [3512]
        ].combinations()
    }
}
