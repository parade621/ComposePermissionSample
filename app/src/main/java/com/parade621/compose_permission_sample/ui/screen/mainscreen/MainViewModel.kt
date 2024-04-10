package com.parade621.compose_permission_sample.ui.screen.mainscreen

import androidx.lifecycle.ViewModel
import com.parade621.compose_permission_sample.data.PermissionData
import com.parade621.compose_permission_sample.permissions.PermissionCheckList
import com.parade621.compose_permission_sample.utils.checkPermissionGranted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnCheckAllClicked -> checkAllPermission()
            is MainEvent.OnCheckPermissionClicked -> onCheckPermissionClicked(event.index)

            is MainEvent.RequestSinglePermission -> requestSinglePermission(event.index)

            MainEvent.RequestMultiplePermission -> requestMultiplePermission()
            MainEvent.ResetPermissionList -> resetPermissionList()
            MainEvent.ResetToast -> {
                _state.update { it.copy(showToast = false) }
            }
        }
    }

    init {
        initPermissionList()
    }

    private fun initPermissionList() {
        val newList = PermissionCheckList.permissionToRequest.map {
            val isGrant = checkPermissionGranted(it)
            PermissionData(it, isGrant, isGrant)
        }
        _state.update { it.copy(permissionList = newList) }
    }

    private fun onCheckPermissionClicked(index: Int) {
        val permissionList = _state.value.permissionList.toMutableList()
        val isGrant = permissionList[index].isGranted
        permissionList[index] =
            permissionList[index].copy(isChecked = if (isGrant) true else !permissionList[index].isChecked)
        Timber.e("permission: ${permissionList[index].permissionText} isChecked: ${permissionList[index].isChecked}")
        _state.update { it.copy(permissionList = permissionList) }
        if (isGrant) {
            _state.update { it.copy(showToast = true) }
        }
    }

    private fun checkAllPermission() {
        val newList = _state.value.permissionList.map {
            it.copy(isChecked = if (it.isGranted) true else !_state.value.checkAll)
        }
        _state.update { it.copy(permissionList = newList, checkAll = !_state.value.checkAll) }
    }

    private fun requestMultiplePermission() {
        _state.update { state ->
            state.copy(requestList = _state.value.permissionList.filter { it.isChecked }
                .map { it.permissionText })
        }
    }

    private fun requestSinglePermission(index: Int) {
        _state.update { state ->
            state.copy(requestList = listOf(_state.value.permissionList[index].permissionText))
        }
    }

    private fun resetPermissionList() {
        _state.update { it.copy(permissionList = emptyList()) }
        initPermissionList()
    }
}