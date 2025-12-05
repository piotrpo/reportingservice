package com.example.kangamarket.controller.application

import com.example.kangamarket.controller.model.MarketSpread
import com.example.kangamarket.controller.model.Ranking
import com.example.kangamarket.controller.model.SpreadsResponse
import io.micrometer.core.instrument.MockClock
import io.micrometer.core.instrument.MockClock.clock
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Clock
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.AtomicReference
import kotlin.concurrent.atomics.ExperimentalAtomicApi


@OptIn(ExperimentalAtomicApi::class)
@Service
class SpreadApplication(val dataHarvester: DataHarvester) {
    private val logger = LoggerFactory.getLogger(SpreadApplication::class.java)
    private val clock: Clock = Clock.systemUTC()
    private val cachedResponse: AtomicReference<SpreadsResponse> =
        AtomicReference(SpreadsResponse(clock.instant(), Ranking(emptyList(), emptyList(), emptyList())))
    private val isRefreshOnGoing: AtomicBoolean = AtomicBoolean(false)


    fun getSpreads(): SpreadsResponse {

        return cachedResponse.load()
    }

    fun refreshSpreadData() {
        if (isRefreshOnGoing.load()) {
            return
        }

        try {
            isRefreshOnGoing.store(true)
            val harvestData = dataHarvester.harvestData()
            val spreadsResponse = prepareSpreadsResponse(harvestData)
            cachedResponse.exchange(spreadsResponse)
        } catch (e: Exception) {
            logger.error("Exception thrown while getting data", e)
        } finally {
            isRefreshOnGoing.store(false)
        }
    }
    fun prepareSpreadsResponse(harvestData: List<MarketSpread?>): SpreadsResponse {
        val marketGroups = harvestData.filterNotNull().groupBy { marketCategory(it) }
        val spreadsResponse = SpreadsResponse(
            clock.instant(),
            Ranking(
                (marketGroups[MarketGroup.GROUP_1] ?: emptyList()).sortedBy { it.market },
                (marketGroups[MarketGroup.GROUP_2] ?: emptyList()).sortedBy { it.market },
                (marketGroups[MarketGroup.GROUP_3] ?: emptyList()).sortedBy { it.market },
            )
        )
        return spreadsResponse
    }
}



fun marketCategory(marketSpread: MarketSpread): MarketGroup {
    try {
        val spread = marketSpread.spread.toDouble()
        return if (spread < 2.0) MarketGroup.GROUP_1 else MarketGroup.GROUP_2
    } catch (e: Exception) {
        return MarketGroup.GROUP_3
    }
}

enum class MarketGroup {
    GROUP_1,
    GROUP_2,
    GROUP_3
}


