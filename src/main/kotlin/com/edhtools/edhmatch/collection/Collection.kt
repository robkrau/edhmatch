package com.edhtools.edhmatch.collection

import mu.KotlinLogging

class Collection {

    val logger = KotlinLogging.logger {}

    val collectedUniqueCards = mutableSetOf<String>()

    fun add(cardName : String) {
        collectedUniqueCards.add(cardName)
    }

    fun logAllCards() {
        collectedUniqueCards.forEach { logger.info(it) }
    }
}