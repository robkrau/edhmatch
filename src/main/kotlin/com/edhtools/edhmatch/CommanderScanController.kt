package com.edhtools.edhmatch

import com.edhtools.edhmatch.catalog.CatalogImportService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/commander")
class CommanderScanController(val importer : CatalogImportService) {

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
    fun identifyOwned() {
        logger.trace { "Request all owned commanders" }
    }
}