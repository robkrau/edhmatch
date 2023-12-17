package com.edhtools.edhmatch.collection

import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class Collection {

    private val logger = KotlinLogging.logger {}

    private val collectedUniqueCards = mutableSetOf<String>()

    fun add(cardName : String) {
        collectedUniqueCards.add(cardName)
    }

    fun logAllCards() {
        collectedUniqueCards.forEach { logger.info(it) }
    }

    fun getCardNamesFromCardCollection(cards : List<CollectionCard>) : Set<String> {
        val setOfNames = mutableSetOf<String>()
        cards.forEach { card -> setOfNames.add(card.name) }
        return setOfNames
    }

    fun get() : Set<String> {
        return collectedUniqueCards
    }
}