package com.edhtools.edhmatch.collection

import org.springframework.data.mongodb.core.mapping.Document


@Document("usercardcollection")
data class UserCardCollection (
        val userName : String = "",
        val collection : List<CollectionCard> = emptyList()
)

data class CollectionCard (
        val name : String = ""
)