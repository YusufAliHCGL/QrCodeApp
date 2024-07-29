package com.example.qrcodeapp.feature_qr_code.presentation.util

sealed class Screen(val route: String) {
    object QrCodeScannerScreen : Screen(route = "qr_code_scanner_screen")
    object QrCodeGeneratorScreen : Screen(route = "qr_code_generator_screen")
}