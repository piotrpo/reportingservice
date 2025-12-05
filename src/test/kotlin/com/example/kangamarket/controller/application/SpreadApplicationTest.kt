package com.example.kangamarket.controller.application

import com.example.kangamarket.controller.application.client.KangaRestClient
import com.example.kangamarket.controller.model.MarketSpread
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class SpreadApplicationTest {
    @Test
    fun getSpreads() {
        val cut = SpreadApplication(DataHarvester(KangaRestClient()))
        val spreads = cut.getSpreads()
        assertNotNull(spreads)
    }

    @Test
    fun checkMarketGroupClassificationG1() {
        val marketCategoryNaN = marketCategory(MarketSpread("G1 market", "1.9999"))
        assertEquals(MarketGroup.GROUP_1, marketCategoryNaN, "Wrong group assignment")
    }

    @Test
    fun checkMarketGroupClassificationG2() {
        val marketCategoryNaN = marketCategory(MarketSpread("G2 market", "2.0"))
        assertEquals(MarketGroup.GROUP_2, marketCategoryNaN, "Wrong group assignment")
    }

    @Test
    fun checkMarketGroupClassificationG3() {
        val marketCategoryNaN = marketCategory(MarketSpread("Not a number", "N/A"))
        assertEquals(MarketGroup.GROUP_3, marketCategoryNaN, "Wrong group assignment")
    }

    @Test
    fun checkResponseCreation() {
        val spreadApplication = SpreadApplication(DataHarvester(KangaRestClient()))

        val prepareSpreadsResponse = spreadApplication.prepareSpreadsResponse(
            listOf(
                MarketSpread("G1 market", "1.9999"),
                MarketSpread("G2 market", "2.0"),
                MarketSpread("G3 market", "N/A"),
            )
        )
        assertNotNull(prepareSpreadsResponse)
        assertNotNull(prepareSpreadsResponse.timestamp)
        assertNotNull(prepareSpreadsResponse.ranking)
        assertEquals(1, prepareSpreadsResponse.ranking.group1.size)
        assertEquals(1, prepareSpreadsResponse.ranking.group2.size)
        assertEquals(1, prepareSpreadsResponse.ranking.group3.size)
    }
}