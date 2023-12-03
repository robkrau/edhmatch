package com.edhtools.edhmatch.catalog

import khttp.responses.Response
import mu.KotlinLogging
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class CatalogImportService(val commanders : Commanders) {

    val logger = KotlinLogging.logger {}

    fun importCommanders() {
        logger.info { "Importing all commanders" }
        val response : Response = khttp.get(
            url = "https://api.scryfall.com/cards/search",
            params = mapOf("order" to "rarity", "dir" to "desc", "q" to "type:creature+type:legendary"))
            // TODO: add support for legendary planeswalkers

        val obj : JSONObject = response.jsonObject
        logger.info { "Received: ${obj["object"]}" }

        this.importPage(obj)
    }

    fun importPage(obj : JSONObject) {
        logger.info { "Importing page" }

        val cardsJSONArray : JSONArray = obj.getJSONArray("data")
        for (i in 0 until cardsJSONArray.length()) {
            val oracleId = cardsJSONArray.getJSONObject(i).get("oracle_id")
            val cardName : String = cardsJSONArray.getJSONObject(i).getString("name")
            commanders.add(cardName)

            logger.info { "OracleId: $oracleId <-> Name: $cardName" }
        }

        if ("true".compareTo(obj["has_more"].toString()) == 0) {
            // scryfall demands a 100ms delay between calls
            Thread.sleep(100)

            logger.info { "Loading next page: ${obj["next_page"]}" }
            val response : Response = khttp.get(obj["next_page"].toString())
            importPage(response.jsonObject)
        }
    }

    fun getCommanders() : Set<String> {
        return commanders.get()
    }


}