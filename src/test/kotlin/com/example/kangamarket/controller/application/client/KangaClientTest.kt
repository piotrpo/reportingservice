package com.example.kangamarket.controller.application.client

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class KangaClientTest {
    @Test
    fun getMarkets() {
        val cut = KangaRestClient()
        val markets = cut.getMarkets()
        
        assertNotNull(markets)
    }

}