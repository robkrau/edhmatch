package com.edhtools.edhmatch.catalog

import mu.KotlinLogging
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@EnableScheduling
class CommanderImport {

    val logger = KotlinLogging.logger {}

    @Scheduled(cron = "\${catalog.import.cron}")
    fun import() {
        logger.info { "Importing catalog" }

        // TODO: implement call https://api.scryfall.com/cards/search?order=rarity&dir=desc&q=type%3Acreature+type%3Alegendary
    }
}