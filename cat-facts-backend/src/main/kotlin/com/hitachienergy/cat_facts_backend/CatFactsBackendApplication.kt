package com.hitachienergy.cat_facts_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CatFactsBackendApplication

fun main(args: Array<String>) {
	runApplication<CatFactsBackendApplication>(*args)
}
