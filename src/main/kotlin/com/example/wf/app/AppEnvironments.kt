package com.example.wf.app

import org.slf4j.LoggerFactory

object AppEnvironments {
    private val LOGGER = LoggerFactory.getLogger(AppEnvironments::class.java)
    const val METRICS_PORT_ENV = "METRICS_PORT"

    fun getMetricsPort(): Int = getEnv(METRICS_PORT_ENV).toInt()

    private fun getEnv(env: String): String = runCatching {
        System.getenv(env) ?: error("Fail to get env '$env'")
    }.onFailure {
        LOGGER.error(it.message, it)
    }.getOrThrow()

}