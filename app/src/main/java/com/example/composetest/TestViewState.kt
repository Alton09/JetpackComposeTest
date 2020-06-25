package com.example.composetest

data class TestViewState(
    val retrievedItems: List<String> = emptyList(),
    val loading: Boolean = true
)