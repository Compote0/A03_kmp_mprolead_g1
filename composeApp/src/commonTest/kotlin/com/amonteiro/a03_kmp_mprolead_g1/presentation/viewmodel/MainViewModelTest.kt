package com.amonteiro.a03_kmp_mprolead_g1.presentation.viewmodel

import com.amonteiro.a03_kmp_mprolead_g1.data.remote.PhotographerAPI
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertTrue

class MainViewModelTest {

    @Test
    fun testWhoAlwaysSucceeds() {
        assertTrue(true, "Ce test passe toujours")
    }

    @Test
    fun testPhotographerAPIEchoueSansSecret() = runTest {
        val client = HttpClient {
            expectSuccess = true
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
        }

        val api = PhotographerAPI(client)

        val result = api.loadPhotographers()

        assertTrue(result.isNotEmpty(), "La liste des photographes ne doit pas être vide")
    }
}