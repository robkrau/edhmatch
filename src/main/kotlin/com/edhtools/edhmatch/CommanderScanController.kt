package com.edhtools.edhmatch

import mu.KotlinLogging
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/commander")
class CommanderScanController {

    private val logger = KotlinLogging.logger {}

    @PostMapping("/import")
    fun scanCommander(@RequestParam name : String) {
        logger.trace { "Import the list of all available commanders" }
    }

    @GetMapping("/owned")
    fun identifyOwned() {
        logger.trace { "Request all owned commanders" }
    }
}