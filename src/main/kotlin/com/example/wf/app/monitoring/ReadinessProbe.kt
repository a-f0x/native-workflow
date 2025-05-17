package com.example.wf.app.monitoring

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

internal class ReadinessProbe() : HttpHandler {
    private val upResponse = HealthResponse(
        HTTP_OK,
        """
            {
                "status":"UP", 
                "components":{
                    "temporal":"UP"
                }
            }
            """
            .trimIndent()
            .toByteArray()
    )

    private val downResponse = HealthResponse(
        HTTP_SERVICE_UNAVAILABLE,
        """
            {
                "status":"DOWN", 
                "components":{
                    "temporal":"DOWN"
                }
            }
            """
            .trimIndent()
            .toByteArray()
    )

    override fun handle(exchange: HttpExchange) {
        val response = resolveResponse()
        exchange.sendResponseHeaders(response.httpCode, response.body.size.toLong())
        exchange.responseBody.use {
            it.write(response.body)
        }
    }

    private fun resolveResponse(): HealthResponse = upResponse

}
