package com.edhtools.edhmatch.decklist

import khttp.responses.Response
import mu.KotlinLogging
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class DeckListService(val repo : CardViewRepository) {

    private val logger = KotlinLogging.logger {}

    private val ALPHANUM_REGEX = Regex("[^A-Za-z0-9\\- ]")

    fun loadDeckListFor(commanderName : String) : Set<String> {
        //https://json.edhrec.com/pages/commanders/anje-maid-of-dishonor.json
        val encodedCommanderName = encodeCommanderName(commanderName)
        logger.info { "Loading card list for ´$encodedCommanderName´(´$commanderName´)" }

        val response : Response = khttp.get(url = "https://json.edhrec.com/pages/commanders/$encodedCommanderName.json")
        return loadDeckList(response.jsonObject)
    }

    fun loadDeckListFromDBFor(commanderName : String) : Set<String> {
        logger.info { "Trying to load card list for commander $commanderName" }
        val commanderCardList = repo.findByCommanderName(commanderName)
        val cardNames = mutableSetOf<String>()
        for (card in 0 until commanderCardList.cardList.size) {
            cardNames.add(commanderCardList.cardList.get(card).name)
        }
        return cardNames
    }

    fun persistDeckListFor(commanderName: String) {
        val encodedCommanderName = encodeCommanderName(commanderName)
        logger.info { "Persisting card list for ´$encodedCommanderName´(´$commanderName´)" }

        val response : Response = khttp.get(url = "https://json.edhrec.com/pages/commanders/$encodedCommanderName.json")
        persistTopDeckList(commanderName, response.jsonObject)
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

    private fun persistTopDeckList(commanderName: String, jsonObject: JSONObject) {
        val topCardList: JSONArray = jsonObject.getJSONArray("cardlist")
        val cardList = mutableListOf<CardListEntry>()
        for (i in 0 until topCardList.length()) {
            val jsonObj = topCardList.getJSONObject(i)
            val cardListEntry = CardListEntry(name = jsonObj.getString("name"),
                    sanitized = jsonObj.getString("sanitized"),
                    sanitized_wo = jsonObj.getString("sanitized_wo"),
                    url = jsonObj.getString("url"),
                    // cards intentionally left out .. no bock
                    synergy = jsonObj.getDouble("synergy"),
                    num_decks = jsonObj.getInt("num_decks"),
                    potential_decks = jsonObj.getInt("potential_decks")
                    )
            cardList.add(cardListEntry)
            // TODO: log for commanderName ..
        }
        logger.info { "Logging $cardList" }

        val commanderCardList = CommanderCardList(commanderName, "", cardList)
        repo.insert(commanderCardList)

    }

    private fun loadCardList(cardList : JSONArray) : Set<String> {
        val cards = mutableSetOf<String>()
        for (i in 0 until cardList.length()) {
            cards.add(cardList.getJSONObject(i).getString("name"))
        }
        return cards
    }

    fun encodeCommanderName(commanderName : String) : String {
        val preSanitizedCommanderName = commanderName.substringBefore(" //").lowercase()
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u")
        return ALPHANUM_REGEX.replace(preSanitizedCommanderName, "").replace(" ", "-")
    }
}