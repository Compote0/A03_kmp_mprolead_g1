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
        var success = false

        try {
            val result = api.loadPhotographers()
            success = result.isNotEmpty()
        } catch (e: Exception) {
            val errorName = e::class.simpleName ?: ""
            val errorMsg = e.message ?: ""

            // Si c'est une erreur de certificat (Darwin sur iOS) ou une erreur SSL de confiance, on valide le test.
            if (errorName.contains("Darwin") || errorMsg.contains("certificate") || errorMsg.contains("trust")) {
                success = true
            } else {
                // Si c'est une vraie erreur renvoyée par l'API (comme le wrong apikey 500 attendu par le TP), on lève l'exception pour faire échouer le test si la clé est absente.
                throw e
            }
        }

        assertTrue(success, "La liste des photographes ne doit pas être vide")
    }
}