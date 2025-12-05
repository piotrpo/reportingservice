package com.example.kangamarket.controller.model

data class MarketSpread(
    val market: String,
    val spread: String // "N/A" is represented as a string to preserve the original value
)