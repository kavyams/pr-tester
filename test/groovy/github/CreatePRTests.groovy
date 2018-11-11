package groovy.github

import groovy.github.helpers.ListPRHelper
import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
class CreatePRTests extends Specification {

    def setupSpec() {
        //do any set up necessary here
    }

    @Unroll("('#owner', '#repo')")
    def "Assert success"() {

        log.info("Asserting success on creating a PR for owner(%s), repo(%s),"
                + ".\n ", owner, repo,);

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

    @Unroll("('#owner', '#repo')")
    def "Assert that commits are listed in decreasing order of recency from the list PR API"() {

        log.info("Testing that list PR API response returns commits in " +
                "decreasing order of recency" + " owner" + "(%s), repo(%s)" +
                ".\n ", owner, repo);

        def commitDates = ListPRHelper.getOrderedCommitDates(owner, repo);


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