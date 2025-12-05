package com.example.kangamarket

import com.example.kangamarket.controller.application.DataHarvester
import com.example.kangamarket.controller.application.client.KangaRestClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class KangamarketApplication

fun main(args: Array<String>) {
    runApplication<KangamarketApplication>(*args)
}


