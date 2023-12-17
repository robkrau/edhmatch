package com.edhtools.edhmatch.catalog

import org.springframework.data.mongodb.repository.MongoRepository

interface CommanderRepository : MongoRepository<Commander, String> {



}