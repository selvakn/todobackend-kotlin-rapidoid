import TodoReqHandler.handler
import org.rapidoid.http.Req
import org.rapidoid.setup.On

fun main(args: Array<String>) {
    val store = TodoStore.get()

    val getTodo: (Req, String) -> Todo = { req, uuid -> store.get("${baseUrl(req)}/$uuid") }

    On.get("/").json(handler { store.all() })

    On.post("/").json(handler { r ->
        store.save(Todo.build(r.data(), baseUrl(r)))
    })

    On.get("/{uuid}").json(handler { r ->
        getTodo(r, r.data("uuid"))
    })

    On.delete("/{uuid}").json(handler { r ->
        store.delete(getTodo(r, r.data("uuid")))
    })

    On.patch("/{uuid}").json(handler { r ->
        val todo = getTodo(r, r.data("uuid"))
        store.save(todo.update(r.data()))
    })

    On.delete("/").json(handler { store.deleteAll() })

    On.options("/*").json(handler { })

}

private fun baseUrl(request: Req): String {
    return "http://${request.header("host")}"
}