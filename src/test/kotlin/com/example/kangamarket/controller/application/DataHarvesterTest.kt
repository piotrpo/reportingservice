package com.example.kangamarket.controller.application

import com.example.kangamarket.controller.application.client.KangaRestClient
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DataHarvesterTest {
//    @Test
    fun harvestData() {
        val dataHarvester = DataHarvester(KangaRestClient())
        val harvestedData = dataHarvester.harvestData()

        assertNotNull(harvestedData)
    }
}