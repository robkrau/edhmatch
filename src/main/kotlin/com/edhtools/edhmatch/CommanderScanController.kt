package com.edhtools.edhmatch

import mu.KotlinLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController



@RestController
class CommanderScanController {

    private val logger = KotlinLogging.logger {}

    @PostMapping("/commander/")
    fun scanCommander(@RequestParam name : String) {
        logger.info { "Posting an owned Commander" }
    }
}