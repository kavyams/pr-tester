package groovy.github

import groovy.github.helpers.PullRequestHelper
import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
class ListPRTests extends Specification {


    def setupSpec() {
        //do any set up necessary here
    }

    @Unroll("('#owner', '#repo', '#state')")
    def "Assert that listed PRs match expected state"() {

        log.info("Asserting state of listed PRs for owner(%s), repo(%s), " +
                "state" + "(%s)" + " " + ".\n ", owner, repo, state);

        def states = PullRequestHelper.getPRStates(owner, repo, state);


        expect:
        assert states != null
        assert states.every { it == state }
        where:
        [owner, repo, state] << [
                ["GoogleChrome"],
                ["puppeteer"],
                ["open", "closed"]
        ].combinations()
    }

    @Unroll("('#owner', '#repo')")
    def "Assert that commits are listed in decreasing order of recency from the list PR API"() {

        log.info("Testing that list PR API response returns commits in " +
                "decreasing order of recency" + " owner" + "(%s), repo(%s)" +
                ".\n ", owner, repo);

        def commitDates = PullRequestHelper.getOrderedCommitDates(owner, repo);


        expect:
        assert (0..<commitDates.size() - 1).every {
            commitDates[it] >= commitDates[it + 1]
        }

        where:
        [owner, repo] << [
                ["GoogleChrome"],
                ["puppeteer"]
        ].combinations()
    }
}
