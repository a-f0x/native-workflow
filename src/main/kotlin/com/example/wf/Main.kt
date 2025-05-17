package com.example.wf

import com.example.wf.app.Application
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("Main")
fun main() {
    Application().start()
    logger.info("app started")
}