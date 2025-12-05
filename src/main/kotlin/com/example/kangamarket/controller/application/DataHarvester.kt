package com.example.kangamarket.controller.application

import com.example.kangamarket.controller.application.client.KangaRestClient
import com.example.kangamarket.controller.model.MarketSpread
import java.io.Closeable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class DataHarvester(private val kangaRestClient: KangaRestClient) : Closeable {
    private val httpExecutor = Executors.newFixedThreadPool(10)
    fun harvestData(): List<MarketSpread?> {
        val markets = harvestMarketList()
        val futures = markets.map { ticker ->
            httpExecutor.submit<MarketSpread?> {
                MarketSpread(ticker, getMarketSpread(ticker))
            }
        }
        return futures.mapNotNull { it.get() } // wait for all tasks to finish
    }

    private fun getMarketSpread(tickerId: String): String {
        // Retry once after a short pause if we hit the stream limit
        val orderBook = runWithRetry { kangaRestClient.getOrderBook(tickerId) }

        val minAsk = orderBook?.asks
            ?.mapNotNull { it.firstOrNull()?.toDoubleOrNull() }
            ?.minOrNull()

        val maxBid = orderBook?.bids
            ?.mapNotNull { it.firstOrNull()?.toDoubleOrNull() }
            ?.maxOrNull()

        return if (minAsk == null || maxBid == null) "N/A"
        else calculateSpread(maxBid, minAsk).toString()
    }

    private fun harvestMarketList(): List<String> {
        return kangaRestClient.getMarkets()?.map { it.ticker_id } ?: emptyList()
    }

    override fun close() {
        httpExecutor.shutdown()
    }
}

private fun <T> runWithRetry(action: () -> T): T? {
    repeat(2) { attempt ->
        try {
            return action()
        } catch (e: org.springframework.web.client.ResourceAccessException) {
            if ("too many concurrent streams" in e.message.orEmpty() && attempt == 0) {
                // wait a bit before retrying
                TimeUnit.MILLISECONDS.sleep(200)
            } else {
                throw e
            }
        }
    }
    return null
}

fun calculateSpread(maxBid: Double, minAsk: Double): Double {
    //Spread = (kurs sprzedaży waluty – kurs kupna waluty) / [0,5*(kurs sprzedaży waluty +
    //kurs kupna waluty)] *100%
    return (minAsk - maxBid) * 200 / (minAsk + maxBid)
}