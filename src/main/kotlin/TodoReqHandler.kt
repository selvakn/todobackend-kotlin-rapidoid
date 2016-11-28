import org.rapidoid.http.Req
import org.rapidoid.http.ReqHandler


object TodoReqHandler {
    fun handler(block: (req: Req) -> Any): ReqHandler {
        return ReqHandler { req ->
            enableCors(req)
            block(req)
        }
    }

    private fun enableCors(r: Req) {
        r.response().header("Access-Control-Allow-Origin", "*")
        r.response().header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH,OPTIONS")
        r.response().header("Access-Control-Allow-Headers", "Content-Type")
    }
}