package com.edhtools.edhmatch

import com.edhtools.edhmatch.catalog.ImportService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/commander")
class CommanderScanController(val importer : ImportService) {

    private val logger = KotlinLogging.logger {}

    @PostMapping("/import")
    fun scanCommander(@RequestParam name : String) {
        logger.trace { "Import the list of all available commanders" }
        importer.importCommanders()
    }

    @GetMapping("/owned")
    fun identifyOwned() {
        logger.trace { "Request all owned commanders" }
    }
}