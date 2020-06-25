package com.example.composetest

import kotlinx.coroutines.delay

class TestDataSource {

    val items = mutableListOf(
            "Line1",
            "Line2",
            "Line3",
            "Line4"
    )

    suspend fun retrieveItems(): List<String> {
        delay(3000)
        return items
    }
}