package com.edhtools.edhmatch

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/collection")
class CollectionController {

    @PostMapping("/update", consumes = ["multipart/form-data"])
    fun importCollection(@RequestParam("file") file : MultipartFile) {

    }

}