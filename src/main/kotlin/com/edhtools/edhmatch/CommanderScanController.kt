package com.edhtools.edhmatch

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CommanderScanController {

    @PostMapping("/commander/")
    fun scanCommander(@RequestParam name : String) {

    }
}