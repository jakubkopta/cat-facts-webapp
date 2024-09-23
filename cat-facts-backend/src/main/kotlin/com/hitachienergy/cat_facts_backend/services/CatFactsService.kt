package com.hitachienergy.cat_facts_backend.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.hitachienergy.cat_facts_backend.models.Fact
import com.hitachienergy.cat_facts_backend.models.User
import com.hitachienergy.cat_facts_backend.models.UserAndFact
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class CatFactsService(private val objectMapper: ObjectMapper) {
    private val userUrl = "https://randomuser.me/api"
    private val catFactsUrl = "https://cat-fact.herokuapp.com/facts/random"

    private val webClient: WebClient = WebClient.create()

    suspend fun fetchUserCatFact(): UserAndFact? {
        return fetchUser()
            .zipWith(fetchCatFact()) { user, fact ->
                UserAndFact(user = user.name, fact = fact.text)
            }.awaitSingleOrNull()
    }

    fun fetchUser(): Mono<User> {
        return webClient.get()
            .uri(userUrl)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { response ->
                val userNode = objectMapper
                    .readTree(response)
                    .path("results")
                    .get(0)

                User(name = "${userNode.path("name").path("first").asText()} " +
                        userNode.path("name").path("last").asText()
                )
            }
    }

    fun fetchCatFact(): Mono<Fact> {
        return webClient.get()
            .uri(catFactsUrl)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { response ->
                val rootNode = objectMapper
                    .readTree(response)

                Fact(text = rootNode["text"].asText())
            }
    }

}
