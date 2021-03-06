package groovy.github

import groovy.github.helpers.CreatePRHelper
import groovy.github.helpers.UpdatePRHelper
import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
class CreatePRTests extends Specification {

    def setupSpec() {
        //any test specific set up goes here
    }

    @Unroll("('#owner', '#repo', '#jsonObject')")
    def "Assert success on create PR"() {

        log.info("Asserting success on creating a PR for $owner, $repo");

        def createPRResponse = CreatePRHelper.makeCreatePRCall(owner, repo, jsonObject);
        def pullRequestObject = CreatePRHelper.createPR(owner, repo, jsonObject, createPRResponse)
        expect:
        assert pullRequestObject.getProperty("title") == jsonObject.get("title")
        assert pullRequestObject.getProperty("head").get("ref") == jsonObject.get("head")
        assert pullRequestObject.getProperty("base").get("ref") == jsonObject.get("base")
        assert pullRequestObject.getProperty("body") == jsonObject.get("body")
        assert pullRequestObject.getProperty("user").get("login") == owner

        cleanup:
        if (pullRequestObject != null) {
            UpdatePRHelper.closePR(owner, repo, ["title": "new title",
                                                 "body" : "updated body",
                                                 "state": "closed",
                                                 "base" : "master"],
                    pullRequestObject.getProperty("number"))
        }

        where:
        [owner, repo, jsonObject] << [
                ["kavyams"],
                ["RandomPractice"],
                [[title: "Awesome new feature", head: "random",
                  base : "master", body: "This is a body"]]
        ].combinations()


    }

    @Unroll("('#owner', '#repo', '#jsonObject')")
    def "Assert failure on create PR when it is missing required information"() {

        log.info("Asserting failure on create PR when it is missing required information"
                + " $owner, $repo");
        def ex
        try {
            CreatePRHelper.createPR(owner, repo, jsonObject)
        } catch (GroovyRuntimeException exception) {
            ex = exception
        }


        expect:
        assert ex.getMessage().contains("422")
        assert ex.getMessage().contains(/"head" wasn't supplied/)
        where:
        [owner, repo, jsonObject] << [
                ["kavyams"],
                ["RandomPractice"],
                [[title: "Awesome new feature",
                  base : "master", body: "This is a body"]]
        ].combinations()
    }
}
