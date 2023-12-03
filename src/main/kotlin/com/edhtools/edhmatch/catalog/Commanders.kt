package com.edhtools.edhmatch.catalog

import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class Commanders {

    private val logger = KotlinLogging.logger {}

    private val collectedUniqueCards = mutableSetOf<String>()

    fun add(cardName : String) {
        collectedUniqueCards.add(cardName)
    }

    fun logAllCards() {
        collectedUniqueCards.forEach { logger.info(it) }
    }

    fun get() : Set<String> {
        return collectedUniqueCards
    }
}