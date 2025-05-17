package com.example.wf.app.monitoring

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry

internal class PrometheusHttpHandler(private val prometheusMeterRegistry: PrometheusMeterRegistry) : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        val metrics: ByteArray = prometheusMeterRegistry.scrape().toByteArray()
        exchange.sendResponseHeaders(HTTP_OK, metrics.size.toLong())
        exchange.responseBody.use {
            it.write(metrics)
        }
    }
}
