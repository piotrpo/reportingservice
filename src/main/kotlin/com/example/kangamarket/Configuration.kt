package com.example.kangamarket

import com.example.kangamarket.controller.application.DataHarvester
import com.example.kangamarket.controller.application.client.KangaRestClient
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {
    @Bean
    fun createHarvester(kangaClient: KangaRestClient): DataHarvester {
        return DataHarvester(kangaClient)
    }

    @Bean
    fun createKangaClient(): KangaRestClient {
        return KangaRestClient()
    }
    @Bean
    fun registerSimpleAuthentication(): FilterRegistrationBean<AuthFilter> {
        val registration = FilterRegistrationBean<AuthFilter>()
        registration.setFilter(AuthFilter())
        registration.addUrlPatterns("/api/spread/calculate")
        registration.order = 1
        registration.setName("bearerAuthFilter")
        return registration
    }
}