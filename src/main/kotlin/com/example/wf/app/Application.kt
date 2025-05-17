package com.example.wf.app

import com.example.wf.app.monitoring.ServiceMonitor
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmInfoMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.core.instrument.binder.system.UptimeMetrics
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry

class Application {
    @Volatile
    private var isStarted = false

    fun start(){
        val meterRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
        initJVMMetrics(meterRegistry)
        ServiceMonitor(
            meterRegistry,
            AppEnvironments.getMetricsPort()
        ).start()
        isStarted = true
    }

    private fun initJVMMetrics(meterRegistry: PrometheusMeterRegistry) {
        ClassLoaderMetrics().bindTo(meterRegistry)
        JvmInfoMetrics().bindTo(meterRegistry)
        JvmMemoryMetrics().bindTo(meterRegistry)
        JvmGcMetrics().bindTo(meterRegistry)
        ProcessorMetrics().bindTo(meterRegistry)
        JvmThreadMetrics().bindTo(meterRegistry)
        UptimeMetrics().bindTo(meterRegistry)
    }
}


