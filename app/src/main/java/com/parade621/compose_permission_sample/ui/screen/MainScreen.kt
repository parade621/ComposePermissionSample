package com.parade621.compose_permission_sample.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.parade621.compose_permission_sample.ui.theme.Compose_Permission_SampleTheme

@Composable
fun MainScreen(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Compose_Permission_SampleTheme {
        MainScreen("Android")
    }
}