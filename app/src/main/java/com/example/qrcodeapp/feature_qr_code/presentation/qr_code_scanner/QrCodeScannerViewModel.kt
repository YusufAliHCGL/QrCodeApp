package com.example.qrcodeapp.feature_qr_code.presentation.qr_code_scanner

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel

class QrCodeScannerViewModel : ViewModel() {

    private val _text = mutableStateOf("")
    val text: State<String> = _text

    private val _isGranted = mutableStateOf(false)
    val isGranted: State<Boolean> = _isGranted

    fun setCameraPermissionGranted(isGranted: Boolean) {
        _isGranted.value = isGranted
    }

    fun updateText(text: String) {
        _text.value = text
    }


}