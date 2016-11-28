import java.util.*

data class Todo(val title: String, val completed: Boolean, val url: String) {
    fun update(data: Map<String, Any>): Todo {
        val updatedTitle = if (data.containsKey("title")) data["title"].toString() else this.title
        val updatedCompleteness = if (data.containsKey("completed")) data["completed"].toString().equals("true") else this.completed
        return copy(title = updatedTitle, completed = updatedCompleteness)
    }

    companion object Factory {
        fun build(params: Map<String, Any>, baseUrl: String): Todo {

            return Todo(params["title"].toString(), false, "$baseUrl/${UUID.randomUUID()}")
        }

    }
}