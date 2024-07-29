package com.example.qrcodeapp.feature_qr_code.presentation.qr_code_generator

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

class QrCodeGenerator {

    private val writer = QRCodeWriter()

    fun generateQrCode(text: String, width: Int): Bitmap? {
        return try {
            val bm = writer.encode(text, BarcodeFormat.QR_CODE, width, width)
            val width = bm.width
            val height = bm.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bm[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            bmp
        } catch (e: Exception) {
            null
        }

    }

}