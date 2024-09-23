package com.hitachienergy.cat_facts_backend.controllers

import com.hitachienergy.cat_facts_backend.models.UserAndFact
import com.hitachienergy.cat_facts_backend.services.CatFactsService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CatFactsController (private val catFactsService: CatFactsService) {

    @CrossOrigin(origins = ["http://localhost:5173"])
    @GetMapping("/cat-facts", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getFactStream(): Flow<ServerSentEvent<UserAndFact>> = flow {
        while (true) {
            val randomFact = catFactsService.fetchUserCatFact()
            randomFact?.let {
                emit(ServerSentEvent.builder(it).build())
            }
            delay(10_000)
        }
    }
}