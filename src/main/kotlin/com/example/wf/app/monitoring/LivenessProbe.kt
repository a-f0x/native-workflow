package com.example.wf.app.monitoring

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

internal class LivenessProbe : HttpHandler {
    private val upResponse = HealthResponse(
        HTTP_OK,
        """
            {
                "status":"UP" 
            }
            """
            .trimIndent()
            .toByteArray()
    )

    override fun handle(exchange: HttpExchange) {
        exchange.sendResponseHeaders(upResponse.httpCode, upResponse.body.size.toLong())
        exchange.responseBody.use {
            it.write(upResponse.body)
        }
    }

}
