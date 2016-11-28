import TodoReqHandler.handler
import org.rapidoid.http.Req
import org.rapidoid.setup.Setup

fun main(args: Array<String>) {
    val store = TodoStore.get()

    val getTodo: (Req, String) -> Todo = { req, uuid -> store.get("${baseUrl(req)}/$uuid") }

    val app = Setup.create("app").port(Integer.parseInt(System.getProperty("server.port", "8888")))

    app.get("/").json(handler { store.all() })

    app.post("/").json(handler { r ->
        store.save(Todo.build(r.data(), baseUrl(r)))
    })

    app.get("/{uuid}").json(handler { r ->
        getTodo(r, r.data("uuid"))
    })

    app.delete("/{uuid}").json(handler { r ->
        store.delete(getTodo(r, r.data("uuid")))
    })

    app.patch("/{uuid}").json(handler { r ->
        val todo = getTodo(r, r.data("uuid"))
        store.save(todo.update(r.data()))
    })

    app.delete("/").json(handler { store.deleteAll() })

    app.options("/*").json(handler { })
}

private fun baseUrl(request: Req): String {
    return "http://${request.header("host")}"
}