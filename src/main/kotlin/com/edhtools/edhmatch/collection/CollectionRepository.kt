package com.edhtools.edhmatch.collection

import org.springframework.data.mongodb.repository.MongoRepository

interface CollectionRepository : MongoRepository<UserCardCollection, String> {

    fun findByUserName(name : String) : UserCardCollection


}