package com.parade621.compose_permission_sample.ui.screen.mainscreen

sealed class MainEvent {
    data object OnCheckAllClicked : MainEvent()
    data class OnCheckPermissionClicked(val index: Int) : MainEvent()

    data object RequestMultiplePermission : MainEvent()
    data class RequestSinglePermission(val index: Int) : MainEvent()
}