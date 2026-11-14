package com.example.thymeleaflocaleexample

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.i18n.LocaleContext
import org.springframework.context.i18n.SimpleLocaleContext
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.i18n.LocaleContextResolver
import java.util.Locale

@Configuration
class LocaleConfiguration {
    @Bean
    fun localeContextResolver(): LocaleContextResolver = SimpleLocaleContextResolver()
}

class SimpleLocaleContextResolver : LocaleContextResolver {
    var localeContext: LocaleContext? = null
    override fun resolveLocaleContext(exchange: ServerWebExchange): LocaleContext =
        localeContext ?: SimpleLocaleContext(Locale.ENGLISH)

    override fun setLocaleContext(
        exchange: ServerWebExchange,
        localeContext: LocaleContext?
    ) {
        localeContext?.let { this.localeContext = it }
    }

    fun resetLocale() {
        localeContext = null
    }
}