package groovy.github

import com.anotherchrisberry.spock.extensions.retry.RetryOnFailure
import groovy.github.helpers.ListPRHelper
import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
@RetryOnFailure(times = 2)
class ListPRTests extends Specification {


    def setupSpec() {
        //do any overall test set up necessary here
    }

    @Unroll("('#owner', '#repo', '#state')")
    def "Assert that listed PRs match expected state"() {

        log.info("Asserting state of listed PRs for owner(%s), repo(%s), " +
                "state" + "(%s)" + " " + ".\n ", owner, repo, state);

        def states = ListPRHelper.getPRStates(owner, repo, state);


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

    @Unroll("('#owner', '#repo', '#sortBy', '#direction)")
    def "Assert that commits are listed in decreasing order of recency from the list PR API"() {

        log.info("Testing that list PR API response returns commits in " +
                "(%s) order of recency of (%s)" + " owner" + "" +
                "(%s), repo(%s)" +
                ".\n ", direction, sortBy, owner, repo);

        def commitDates = ListPRHelper.getOrderedCommitDates(owner, repo, sortBy, direction);


        expect:
        assert (0..<commitDates.size() - 1).every {
            commitDates[it] >= commitDates[it + 1]
        }

        where:
        [owner, repo, sortBy, direction] << [
                ["GoogleChrome"],
                ["puppeteer"],
                ["created", "updated"],
                ["desc"]
        ].combinations()
    }
}
