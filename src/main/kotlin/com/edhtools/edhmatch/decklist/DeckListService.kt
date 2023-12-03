package com.edhtools.edhmatch.decklist

import khttp.responses.Response
import mu.KotlinLogging
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class DeckListService {

    private val logger = KotlinLogging.logger {}

    private val ALPHANUM_REGEX = Regex("[^A-Za-z0-9\\- ]")

    fun loadDeckListFor(commanderName : String) : Set<String> {
        //https://json.edhrec.com/pages/commanders/anje-maid-of-dishonor.json
        val encodedCommanderName = encodeCommanderName(commanderName)
        logger.info { "Loading card list for ´$encodedCommanderName´(´$commanderName´)" }

        val response : Response = khttp.get(url = "https://json.edhrec.com/pages/commanders/$encodedCommanderName.json")
        return loadDeckList(response.jsonObject)
    }

    private fun loadDeckList(jsonObject : JSONObject) : Set<String> {
        val jsonContainer : JSONObject = jsonObject.getJSONObject("container")
        val jsonDict : JSONObject = jsonContainer.getJSONObject("json_dict")
        val cardLists : JSONArray = jsonDict.getJSONArray("cardlists")

        val deckSet = mutableSetOf<String>()
        for (i in 0 until cardLists.length()) {
            deckSet.addAll(loadCardList(cardLists.getJSONObject(i).getJSONArray("cardviews")))
        }

        return deckSet
    }

    private fun loadCardList(cardList : JSONArray) : Set<String> {
        val cards = mutableSetOf<String>()
        for (i in 0 until cardList.length()) {
            cards.add(cardList.getJSONObject(i).getString("name"))
        }
        return cards
    }

    fun encodeCommanderName(commanderName : String) : String {
        return ALPHANUM_REGEX.replace(commanderName, "").replace(" ", "-").lowercase()
    }
}