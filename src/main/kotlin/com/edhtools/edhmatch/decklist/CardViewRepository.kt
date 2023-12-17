package com.edhtools.edhmatch.decklist

import org.springframework.data.mongodb.repository.MongoRepository

interface CardViewRepository : MongoRepository<CommanderCardList, String> {

    fun findByCommanderName(commanderName : String) : CommanderCardList


}