import java.util.*

data class Todo(val title: String, val completed: Boolean, val order: Int, val url: String) {
    fun update(data: Map<String, Any>): Todo {
        return Factory.mergeFrom(this, data)
    }

    companion object Factory {
        fun build(params: Map<String, Any>, baseUrl: String): Todo {
            return Todo(
                    params["title"].toString(),
                    false,
                    updatedOrder(params) { 0 },
                    "$baseUrl/${UUID.randomUUID()}"
            )
        }

        private fun mergeFrom(todo: Todo, data: Map<String, Any>): Todo {
            return todo.copy(
                    title = updatedTitle(data, { todo.title }),
                    completed = updatedCompletedness(data, { todo.completed }),
                    order = updatedOrder(data) { todo.order }
            )
        }

        private fun updatedCompletedness(data: Map<String, Any>, defaultValue: () -> Boolean) = if (data.containsKey("completed")) data["completed"].toString().equals("true") else defaultValue()

        private fun updatedTitle(data: Map<String, Any>, defaultValue: () -> String) = if (data.containsKey("title")) data["title"].toString() else defaultValue()

        private fun updatedOrder(data: Map<String, Any>, defaultValue: () -> Int) = Integer.parseInt(data.getOrElse("order", defaultValue).toString())

    }
}