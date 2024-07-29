package com.example.qrcodeapp.feature_qr_code.presentation.qr_code_generator

import android.graphics.Bitmap

data class GenerateState(
    val text: String = "",
    val bmp: Bitmap? = null
)