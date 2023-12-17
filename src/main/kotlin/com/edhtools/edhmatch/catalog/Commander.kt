package com.edhtools.edhmatch.catalog

import org.springframework.data.mongodb.core.mapping.Document

@Document("commanders")
data class Commander (
        val name: String = "",
        val manaCost: String = "",
        val colors: List<String> = emptyList(),
        val rarity: String = ""
)