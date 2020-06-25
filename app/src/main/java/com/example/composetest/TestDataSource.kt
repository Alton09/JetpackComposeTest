package com.example.composetest

import kotlinx.coroutines.delay

class TestDataSource {

    val items = mutableListOf(
            "Bread",
            "Cherries",
            "Milk",
            "Tofu"
    )

    suspend fun retrieveItems(): List<String> {
        delay(3000)
        return items
    }
}