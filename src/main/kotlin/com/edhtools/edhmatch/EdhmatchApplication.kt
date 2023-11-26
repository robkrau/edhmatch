package com.edhtools.edhmatch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class EdhmatchApplication

fun main(args: Array<String>) {
	runApplication<EdhmatchApplication>(*args)
}

@RestController
class HelloController{
	@GetMapping("/")
	fun index(@RequestParam("name") name: String) = "Hallo $name!"
}
