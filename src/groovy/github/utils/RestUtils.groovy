package groovy.github.utils

import groovy.github.constants.Constants
import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Slf4j()
class RestUtils {

    static
    def httpCall(baseUrl, path, query, method = Method.POST, contentType =
            ContentType.JSON,
                 Map requestHeaders = [:], String bodyString = "") {
        try {
            def ret = null
            def http = new HTTPBuilder(baseUrl)

            http.request(method, contentType) {
                uri.path = path
                uri.query = query
                if (!bodyString.isEmpty()) {
                    body = bodyString
                }

                headers."User-Agent" = Constants.USER_AGENT

                // add custom headers
                requestHeaders.each { key, value ->
                    headers."${key}" = "${value}"
                }

                // response handler for a success response code
                response.success = { resp, reader ->
                    ret = reader
                }
                response.failure = { resp, reader ->
                    def failureMsg = String.format(
                            "HTTP request failed with status: %s. " +
                                    "Reason: %s, BaseUrl: %s, Path: %s, Query: %s",
                            resp.getStatusLine(),
                            reader,
                            baseUrl, path)
                    throw new GroovyRuntimeException(failureMsg)
                }
            }
            return ret
        } catch (groovyx.net.http.HttpResponseException ex) {
            throw new Exception("Could not make HTTP request " +
                    ex.printStackTrace())
        } catch (java.net.ConnectException ex) {
            log.error("Could not make HTTP request. Stack trace  is below " +
                    ex.printStackTrace())
            throw new Exception("Could not make HTTP request " +
                    ex.printStackTrace())
        }
    }


    static
    def getResponse(baseUrl, path, query, contentType = ContentType.JSON) {
        return httpCall(baseUrl, path, query, Method.GET, contentType)
    }

    static def toUrl(baseUrl, path) {
        def urlPath = baseUrl + path
        return urlPath
    }

}
