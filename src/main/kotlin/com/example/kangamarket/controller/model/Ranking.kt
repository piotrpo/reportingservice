package com.example.kangamarket.controller.model

data class Ranking(
    val group1: List<MarketSpread>,
    val group2: List<MarketSpread>,
    val group3: List<MarketSpread>
)