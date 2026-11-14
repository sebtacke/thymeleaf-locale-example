package com.example.thymeleaflocaleexample

import java.util.Locale
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class LocaleService {
    fun pretendToGetLocaleFromBackend(locale: String?) =
        locale?.let { Mono.just(Locale.forLanguageTag(it)) } ?: Mono.just(Locale.ENGLISH)
}