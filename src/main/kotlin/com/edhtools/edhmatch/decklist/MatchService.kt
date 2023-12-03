package com.edhtools.edhmatch.decklist

import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class MatchService {

    val logger = KotlinLogging.logger {}

    fun evaluate(decklist : Set<String>, collection : Set<String>) {
        val decklistSize : Int = decklist.size
        var matches = 0

        logger.info { "Unique cards in collection: ${collection.size}" }

        for (card in decklist) {
            if (collection.contains(card)) {
                matches++
            }
        }
        logger.info { "$matches / $decklistSize" }
        val ratio = (100 * matches) / decklistSize
        logger.info { "Ratio: $ratio" }
    }
}