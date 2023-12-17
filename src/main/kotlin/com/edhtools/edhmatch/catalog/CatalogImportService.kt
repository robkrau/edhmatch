package com.edhtools.edhmatch.catalog

import khttp.responses.Response
import mu.KotlinLogging
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class CatalogImportService(
        val commanders : Commanders,
        val repo : CommanderRepository
) {

    val logger = KotlinLogging.logger {}

    val bannedCards = setOf("Faceless One", "Throne of the Grim Captain")

    val scannedCommanders = mutableListOf<Commander>()

    fun importCommanders() {
        logger.info { "Importing all commanders" }
        val response : Response = khttp.get(
            url = "https://api.scryfall.com/cards/search",
            params = mapOf("order" to "rarity", "dir" to "desc", "q" to "is:commander+-t:background"))

        val obj : JSONObject = response.jsonObject
        logger.info { "Received: ${obj["object"]}" }

        scannedCommanders.clear()

        this.importPage(obj)

        if (repo.findAll().size != scannedCommanders.size) {
            logger.info { "Reimporting Commanders.." }
            repo.deleteAll()
            repo.insert(scannedCommanders)
        }
    }

    fun importPage(obj : JSONObject) {
        logger.info { "Scanning commander page" }

        val cardsJSONArray : JSONArray = obj.getJSONArray("data")
        for (i in 0 until cardsJSONArray.length()) {
            val jsonCard = cardsJSONArray.getJSONObject(i);
            //val oracleId = cardsJSONArray.getJSONObject(i).get("oracle_id")
            val cardName : String = jsonCard.getString("name")




            if (!bannedCards.contains(cardName)) {   // not cool - unknown to EDHrec
                scannedCommanders.add(Commander(name =  cardName))
            }

            //logger.info { "OracleId: $oracleId <-> Name: $cardName" }
            logger.info { "Name: $cardName" }
        }

        if ("true".compareTo(obj["has_more"].toString()) == 0) {
            // scryfall demands a 100ms delay between calls
            Thread.sleep(100)

            logger.info { "Loading next page: ${obj["next_page"]}" }
            val response : Response = khttp.get(obj["next_page"].toString())
            importPage(response.jsonObject)
        }
    }

    fun getCommanders() : List<Commander> {
        return repo.findAll()
    }


}