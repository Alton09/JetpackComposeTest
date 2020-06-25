package com.example.composetest

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.core.setContent
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.composetest.ui.typography

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = viewModels<TestViewModel> { TestViewModel.TestViewModelFactory() }

        setContent {
            NewsStory(viewStateLiveData = viewModel.value.viewState)
        }
    }
}

@Composable
fun NewsStory(viewStateLiveData: LiveData<TestViewState>) {
    val image = imageResource(R.drawable.header)
    val viewState = viewStateLiveData.observeAsState()

    MaterialTheme {
        Column(
                modifier = Modifier.padding(16.dp)
        ) {
            val imageModifier = Modifier
                .preferredHeightIn(maxHeight = 180.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(4.dp))
            Image(image, modifier = imageModifier, contentScale = ContentScale.Crop)
            Spacer(Modifier.preferredHeight(16.dp))
            viewState.value?.let { renderViewState(it) }
        }
    }
}

@Composable
fun renderViewState(viewState: TestViewState) {
    viewState.run {
        retrievedItems.forEach {
            Text(it, style = typography.body2)
        }

        if (loading) CircularProgressIndicator()
    }
}

@Preview
@Composable
fun DefaultPreview() {
    NewsStory(MutableLiveData(TestViewState(retrievedItems = listOf("Hello!"))))
}