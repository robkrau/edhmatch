package com.edhtools.edhmatch.collection

import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.lang.IllegalArgumentException

@Service
class CollectionImportService {

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


        br.forEachLine { l -> collection.add(l) }

        collection.logAllCards()
    }

    fun getCollection() : Set<String> {
        logger.info { "Requested collection (size: ${collection.get().size}" }
        return collection.get()
    }

}