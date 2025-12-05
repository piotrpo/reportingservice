package com.example.kangamarket.controller.application.client

import com.example.kangamarket.controller.application.client.model.MarketPair
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

private const val marketPairsApiUrl = "https://public.kanga.exchange/api/market/pairs"
private const val orderbookApiUrl = "https://public.kanga.exchange/api/market/orderbook/{tickerId}"

class KangaRestClient {
    private val httpClient = RestClient.create()

    @CircuitBreaker(name = "kanga", fallbackMethod = "fallbackGetMarkets")
    fun getMarkets(): List<MarketPair>? {
        return httpClient.get()
            .uri(marketPairsApiUrl)
            .retrieve()
            .body<List<MarketPair>>()
    }

    @CircuitBreaker(name = "kanga", fallbackMethod = "fallbackGetOrderBook")
    fun getOrderBook(tickerId: String): MarketData? {
        return httpClient.get()
            .uri(orderbookApiUrl, tickerId)
            .retrieve()
            .body<MarketData>()
    }

    private fun fallbackGetMarkets(): List<MarketPair> = emptyList()

    private fun fallbackGetOrderBook(tickerId: String): MarketData? = null

}

data class MarketData(
    val timestamp: Long,
    val bids: List<List<String>>,
    val asks: List<List<String>>,
    val ticker_id: String
)
