package groovy.github.valueObjects

class PullRequestObject implements GroovyObject {

    def keyValues = new HashMap<String, Object>()

    def void setProperty(String key, Object value) {
        keyValues.put(key, value)
    }

    def Object getProperty(String key) {
        return keyValues.get(key)
    }
}
