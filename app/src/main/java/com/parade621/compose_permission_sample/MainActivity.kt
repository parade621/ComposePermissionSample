package com.parade621.compose_permission_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parade621.compose_permission_sample.ui.screen.mainscreen.MainScreen
import com.parade621.compose_permission_sample.ui.screen.mainscreen.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            MainScreen(
                state = state,
                onEvent = viewModel::onEvent
            )

        }
    }
}