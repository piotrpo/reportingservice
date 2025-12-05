package com.example.kangamarket.controller

import com.example.kangamarket.controller.application.SpreadApplication
import com.example.kangamarket.controller.model.SpreadsResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/spread")
class SpreadsController(private val spreadApplication: SpreadApplication) {

    @GetMapping("ranking")
    fun getAllSpreads(): SpreadsResponse {
        return spreadApplication.getSpreads()
    }

    @PostMapping("calculate")
    fun calculateSpreads() {
        spreadApplication.refreshSpreadData()
    }
}