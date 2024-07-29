package com.example.qrcodeapp.feature_qr_code.presentation.qr_code_generator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.ViewModel

class QrCodeGeneratorViewModel : ViewModel() {
    private val _generateState = mutableStateOf(GenerateState())
    val generateState: State<GenerateState> = _generateState
    private val qrCodeGenerator = QrCodeGenerator()

    fun onEvent(event: GenerateEvents) {
        when(event) {
            is GenerateEvents.ChangeText -> {
                changeText(event.text)
            }
            is GenerateEvents.CreateBitmapQrCode -> {
                generateQrCode(event.width)
            }
        }
    }

    private fun generateQrCode(width: Dp) {
        val bmp = qrCodeGenerator.generateQrCode(_generateState.value.text, width.value.toInt())
        _generateState.value = generateState.value.copy(
            bmp = bmp
        )
    }

    private fun changeText(text: String) {
        _generateState.value = generateState.value.copy(
            text = text
        )
    }
}