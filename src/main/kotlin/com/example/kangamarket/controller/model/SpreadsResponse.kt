package com.example.kangamarket.controller.model

import java.time.Instant

data class SpreadsResponse (
    val timestamp: Instant,
    val ranking: Ranking
)
