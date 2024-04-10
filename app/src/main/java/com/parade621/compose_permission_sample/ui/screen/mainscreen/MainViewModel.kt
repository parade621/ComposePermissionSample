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
            is MainEvent.OnCheckPermissionClicked -> {
                val permissionList = _state.value.permissionList.toMutableList()
                permissionList[event.index] =
                    permissionList[event.index].copy(isChecked = !permissionList[event.index].isChecked)
                Timber.e("permission: ${permissionList[event.index].permissionText} isChecked: ${permissionList[event.index].isChecked}")
                _state.update { it.copy(permissionList = permissionList) }
            }

            is MainEvent.RequestSinglePermission -> requestSinglePermission(event.index)

            MainEvent.RequestMultiplePermission -> requestMultiplePermission()
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

    private fun checkAllPermission() {
        val newList = _state.value.permissionList.map {
            it.copy(isChecked = !_state.value.checkAll)
        }
        _state.update { it.copy(permissionList = newList, checkAll = !_state.value.checkAll) }
    }

    private fun requestMultiplePermission() {
        val permissionList =
            _state.value.permissionList.filter { it.isChecked }.map { it.permissionText }
        Timber.e("멀티 퍼미션: ${permissionList.joinToString(", ")}")
    }

    private fun requestSinglePermission(index: Int) {
        val permission = _state.value.permissionList[index].permissionText
        Timber.e("싱글 퍼미션: $permission")
    }
}