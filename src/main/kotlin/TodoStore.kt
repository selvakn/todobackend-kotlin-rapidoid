import java.util.*

class TodoStore {
    val store = HashMap<String, Todo>()

    fun get(url: String): Todo = store[url]!!

    fun save(todo: Todo): Todo {
        store.put(todo.url, todo)
        return todo
    }

    fun all(): List<Todo> = java.util.ArrayList(store.values)

    fun delete(todo: Todo) {
        store.remove(todo.url)
    }

    fun deleteAll() = store.clear()

    companion object Factory {
        fun get(): TodoStore = TodoStore()
    }
}