// RUN_PIPELINE_TILL: BACKEND
class My {
    init {
        var y: Int?
        y = 42
        y.hashCode()
    }
}