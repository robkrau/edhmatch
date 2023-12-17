package com.edhtools.edhmatch.collection

import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.lang.IllegalArgumentException

@Service
class CollectionImportService(val repo : CollectionRepository) {

    val logger = KotlinLogging.logger {}

    val collection = Collection()

    fun import(file : MultipartFile, skipFirstLine : Boolean) {
        if (file.isEmpty) {
            throw IllegalArgumentException("Cannot import empty file")
        }

        file.inputStream.bufferedReader()
                . use { parseFile(it, skipFirstLine) }
    }

    fun parseFile(br : BufferedReader, skipFirstLine: Boolean) {
        if (skipFirstLine) {
            br.readLine()
        }

        // TODO: collection is redundant
        val collectionCards = mutableListOf<CollectionCard>()
        br.forEachLine {
            l -> collection.add(l)
        }

        collection.logAllCards()

        collection.get().forEach { card -> collectionCards.add(CollectionCard(card)) }


        val cardCollection = UserCardCollection(userName = "me", collectionCards)

        repo.insert(cardCollection)
    }

    fun getCollection() : List<CollectionCard> {
        val cardCollection = repo.findByUserName("me")
        logger.info { "Requested collection (size: ${cardCollection.collection.size}" }
        return cardCollection.collection
    }

}