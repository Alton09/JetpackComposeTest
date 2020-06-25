package com.example.composetest

sealed class TestViewEvent {
    object SwipeRefresh : TestViewEvent()
}