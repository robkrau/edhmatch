package com.edhtools.edhmatch.catalog

import khttp.responses.Response
import mu.KotlinLogging
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class ImportService {

    val logger = KotlinLogging.logger {}

    fun importCommanders() {
        logger.info { "Importing all commanders" }
        val response : Response = khttp.get(
            url = "https://api.scryfall.com/cards/search",
            params = mapOf("order" to "rarity", "dir" to "desc", "q" to "type:creature+type:legendary"))

        val obj : JSONObject = response.jsonObject
        logger.info { "Received: ${obj["object"]}" }

    }
}