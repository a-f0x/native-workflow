package com.example.wf.app.monitoring

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import java.net.InetSocketAddress

internal class ServiceMonitor(
    prometheusMeterRegistry: PrometheusMeterRegistry,
    private val port: Int
) {
    private val prometheusHttpHandler: HttpHandler = PrometheusHttpHandler(prometheusMeterRegistry)
    private val readinessProbe: HttpHandler = ReadinessProbe()
    private val livenessProbe: HttpHandler = LivenessProbe()

    fun start() {
        HttpServer.create(InetSocketAddress(port), 0)
            .apply {
                createContext("/metrics", prometheusHttpHandler)
                createContext("/readiness", readinessProbe)
                createContext("/liveness", livenessProbe)
            }.start()
    }
}
