package com.example.thymeleaflocaleexample

import org.springframework.context.i18n.SimpleLocaleContext
import java.util.Locale
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.i18n.LocaleContextResolver
import reactor.core.publisher.Mono

@Controller
class HomeController(
    private val localeService: LocaleService,
    private val localeContextResolver: LocaleContextResolver
) {

    @GetMapping("/")
    fun index(exchange: ServerWebExchange, @RequestHeader("Accept-Language") acceptLanguage: String?): Mono<String> {
        return localeService.pretendToGetLocaleFromBackend(acceptLanguage).map { locale ->
            localeContextResolver.setLocaleContext(exchange, SimpleLocaleContext(locale))
            "index"
        }
    }
}