package com.edhtools.edhmatch.catalog

import mu.KotlinLogging
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@EnableScheduling
class ImportScheduler {

    val logger = KotlinLogging.logger {}

    @Scheduled(cron = "\${catalog.import.cron}")
    fun importCommanders() {
        logger.info { "Importing catalog" }

    }
}