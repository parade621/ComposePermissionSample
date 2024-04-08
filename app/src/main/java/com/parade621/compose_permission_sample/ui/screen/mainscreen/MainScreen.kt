package com.parade621.compose_permission_sample.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.parade621.compose_permission_sample.ui.theme.Compose_Permission_SampleTheme

@Composable
fun MainScreen(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn{
            //itemsIndexed()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Compose_Permission_SampleTheme {
        MainScreen("Android")
    }
}