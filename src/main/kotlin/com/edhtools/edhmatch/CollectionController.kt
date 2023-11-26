package com.edhtools.edhmatch

import mu.KotlinLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/collection")
class CollectionController {

    val logger = KotlinLogging.logger {}

    @PostMapping("/update", consumes = ["multipart/form-data"])
    fun importCollection(@RequestParam("file") file : MultipartFile) {
        logger.info { "Uploading card collection" }
    }

}