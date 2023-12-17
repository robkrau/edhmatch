package com.edhtools.edhmatch.decklist

import org.springframework.data.mongodb.core.mapping.Document

@Document("commandercardlist")
data class CommanderCardList (
    val commanderName : String = "",
    val theme : String = "",
    val cardList: List<CardListEntry> = emptyList()
)

data class CardListEntry (
    val name: String = "",
    val sanitized: String = "",
    val sanitized_wo: String = "",
    val url: String = "",
    val synergy: Double = 0.0,
    val num_decks: Int = 0,
    val potential_decks: Int = 0
    )

