package com.example.composetest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.composetest.TestViewEffect.SyncComplete
import com.example.composetest.TestViewEvent.SwipeRefresh
import com.twilio.udf.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TestViewModel(
    private val dataSource: TestDataSource = TestDataSource(),
    private val ioCoroutineContext: CoroutineContext = Dispatchers.IO):
    BaseViewModel<TestViewEvent, TestViewState, TestViewEffect>(TestViewState()) {

    init {
        retrieveItems()
    }

    override fun processInput(viewEvent: TestViewEvent) {
        when (viewEvent) {
            SwipeRefresh -> {
                retrieveItems()
            }
        }
    }

    private fun retrieveItems() {
        updateState { it.copy(loading = true) }
        viewModelScope.launch {
            var items = emptyList<String>()
            withContext(ioCoroutineContext) {
                items = dataSource.retrieveItems()
            }
            updateState { it.copy(retrievedItems = items, loading = false) }
            viewEffect { SyncComplete }
        }
    }

    class TestViewModelFactory: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TestViewModel() as T
        }

    }
}