package com.edhtools.edhmatch

import com.edhtools.edhmatch.catalog.CatalogImportService
import com.edhtools.edhmatch.collection.Collection
import com.edhtools.edhmatch.collection.CollectionImportService
import com.edhtools.edhmatch.decklist.DeckListService
import com.edhtools.edhmatch.decklist.MatchService
import mu.KotlinLogging
import org.springframework.core.convert.TypeDescriptor
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/commander")
class CommanderScanController(
        val importer : CatalogImportService,
        val deckListService: DeckListService,
        val collectionImportService: CollectionImportService,
        val matchService: MatchService,
        val catalogImportService: CatalogImportService) {

    private val logger = KotlinLogging.logger {}

    @PostMapping("/import")
    fun scanCommander() {
        logger.trace { "Import the list of all available commanders" }
        importer.importCommanders()
    }

    @PostMapping("/add")
    fun addOwned(@RequestParam name : String) {
        logger.trace { "Add $name to owned collection of commanders" }
    }

    @GetMapping("/owned")
    fun identifyOwned() : Set<String> {
        logger.trace { "Request all owned commanders" }
        val collection = collectionImportService.getCollection()
        val owned = mutableSetOf<String>()
        for (commander in catalogImportService.getCommanders()) {
            if (collection.contains(commander)) {
                owned.add(commander)
            }
        }
        return owned
    }

    @GetMapping("/listByCommander")
    fun listCardsOwnedForCommander(@RequestParam name : String) {
        logger.trace { "Request all owned cards for commander  $name" }
        val decklist : Set<String> = deckListService.loadDeckListFor(name)
        val collection : Set<String> = collectionImportService.getCollection()
        matchService.evaluate(decklist, collection)
    }

    @GetMapping("/listOwned")
    fun listCardsForOwnedCommanders() {
        logger.trace { "Request all owned cards for owned commanders" }
        for (commander in identifyOwned()) {
            logger.info { "Evaluating $commander .." }
            val decklist: Set<String> = deckListService.loadDeckListFor(commander)
            val collection: Set<String> = collectionImportService.getCollection()
            matchService.evaluate(decklist, collection)
        }
    }
}