package com.edhtools.edhmatch

import com.edhtools.edhmatch.catalog.CatalogImportService
import com.edhtools.edhmatch.collection.Collection
import com.edhtools.edhmatch.collection.CollectionImportService
import com.edhtools.edhmatch.decklist.DeckListService
import com.edhtools.edhmatch.decklist.MatchService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/commander")
class CommanderScanController(
        val importer : CatalogImportService,
        val deckListService: DeckListService,
        val collectionImportService: CollectionImportService,
        val collectionService : Collection,
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
        val cardCollection = collectionImportService.getCollection()
        val cardNames : Set<String> = collectionService.getCardNamesFromCardCollection(cardCollection)
        val owned = mutableSetOf<String>()
        for (commander in catalogImportService.getCommanders()) {
            if (cardNames.contains(commander.name)) {
                owned.add(commander.name)
            }
        }
        return owned
    }

    @GetMapping("/listByCommander")
    fun listCardsOwnedForCommander(@RequestParam name : String) {
        logger.trace { "Request all owned cards for commander  $name" }
        val decklist : Set<String> = deckListService.loadDeckListFromDBFor(name)
        val cardCollection = collectionImportService.getCollection()
        val cardNames : Set<String> = collectionService.getCardNamesFromCardCollection(cardCollection)
        matchService.evaluate(decklist, cardNames)
    }

    @GetMapping("/persistForCommander")
    fun persistForCommander(@RequestParam name : String) {
        logger.trace { "Trying to persist commander decklist for $name" }
        deckListService.persistDeckListFor(name)
    }

    @GetMapping("/persistForOwned")
    fun persistForOwnedCommanders() {
        for (commander in identifyOwned()) {
            logger.info { "Persisting for $commander.." }
            deckListService.persistDeckListFor(commander)
            Thread.sleep(50)
        }
    }

    @GetMapping("/listOwned")
    fun listCardsForOwnedCommanders() {
        logger.trace { "Request all owned cards for owned commanders" }
        val tierMap = mutableMapOf<Int, MutableSet<String>>()
        var count = 0
        for (commander in identifyOwned()) {
            logger.info { "Evaluating $commander .." }
            val decklist: Set<String> = deckListService.loadDeckListFromDBFor(commander)
            val cardCollection = collectionImportService.getCollection()
            val cardNames : Set<String> = collectionService.getCardNamesFromCardCollection(cardCollection)
            val score = matchService.evaluate(decklist, cardNames)
            count++

            val commandersInTier = tierMap.getOrDefault(score, mutableSetOf())
            commandersInTier.add(commander)
            tierMap[score] = commandersInTier

            Thread.sleep(50)
        }

        logger.info { "Checked a total of $count owned commanders." }
        logger.info { "Ranked list of commanders:" }

        for (i in 100 downTo  0) {
            val commandersInTier = tierMap[i]
            if (commandersInTier != null) {
                logger.info { "Commanders ranked with $i: ${commandersInTier.joinToString(" ++ ")}." }
            }
        }
    }
}