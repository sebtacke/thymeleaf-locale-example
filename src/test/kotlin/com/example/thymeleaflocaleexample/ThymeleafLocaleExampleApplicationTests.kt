package com.example.thymeleaflocaleexample

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.server.i18n.LocaleContextResolver

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ThymeleafLocaleExampleApplicationTests(
    @param:LocalServerPort private val port: Int,
    @param:Autowired private val localeContextResolver: LocaleContextResolver
) {

    private val webTestClient: WebTestClient
        get() = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

    @BeforeEach @AfterEach
    fun resetLocale() {
        (localeContextResolver as SimpleLocaleContextResolver).resetLocale()
    }

    @Test
    fun contextLoads() {}

    @Test
    fun `renders English by default`() {
        assertPage(heading = "Hello, World!", description = "Welcome to the Thymeleaf locale example.")
    }

    @Test
    fun `renders English for service locale`() {
        assertPage(
            acceptLanguage = "en",
            heading = "Hello, World!",
            description = "Welcome to the Thymeleaf locale example."
        )
    }

    @Test
    fun `renders German for service locale`() {
        assertPage(
            acceptLanguage = "de",
            heading = "Hallo, Welt!",
            description = "Willkommen beim Thymeleaf-Sprachbeispiel."
        )
    }


    private fun assertPage(
        acceptLanguage: String? = null,
        heading: String,
        description: String
    ) {
        val request = webTestClient.get().uri("/")
        if (acceptLanguage != null) {
            request.header(HttpHeaders.ACCEPT_LANGUAGE, acceptLanguage)
        }

        request.exchange()
            .expectStatus().isOk
            .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
            .expectBody()
            .xpath("/html/@lang").isEqualTo(acceptLanguage ?: "en")
            .xpath("//h1").isEqualTo(heading)
            .xpath("//p").isEqualTo(description)
    }

}
