package com.example.qrcodeapp.feature_qr_code.presentation.qr_code_generator

import androidx.compose.ui.unit.Dp

sealed class GenerateEvents {
    class ChangeText(val text: String) : GenerateEvents()
    class CreateBitmapQrCode(val width: Dp) : GenerateEvents()
}