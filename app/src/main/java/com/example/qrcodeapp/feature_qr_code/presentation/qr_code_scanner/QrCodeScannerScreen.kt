package com.example.qrcodeapp.feature_qr_code.presentation.qr_code_scanner

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.lifecycleScope
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun QrCodeScannerScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: QrCodeScannerViewModel
) {
    val lifecycle = LocalLifecycleOwner.current
    val context = LocalContext.current
    var isCopied by remember { mutableStateOf(false) }
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    val cameraProviderFeature = remember {
        ProcessCameraProvider.getInstance(context)
    }
    val text = viewModel.text.value

    val isGranted = viewModel.isGranted.value

    val cameraPermissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { result ->
        viewModel.setCameraPermissionGranted(result)
    }

    val sharedPreferences = remember {
        context.getSharedPreferences("permissions", MODE_PRIVATE)
    }

    val isFirstTry = remember {
        sharedPreferences.getBoolean("isThisFirstTry", true)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LaunchedEffect(key1 = true) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                viewModel.setCameraPermissionGranted(true)
            } else {
                if (isFirstTry) {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    sharedPreferences.edit().putBoolean("isThisFirstTry", false).apply()
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.CAMERA)) {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    } else {
                        context.startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.packageName, null)
                            )
                        )
                        println("dsflşdsflş")
                    }
                }
            }
        }
        if (isGranted) {
            AndroidView(factory = { context ->
                val previewView = PreviewView(context)
                val preview = Preview.Builder().build()
                val selector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
                preview.setSurfaceProvider(previewView.surfaceProvider)
                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(Size(previewView.width, previewView.height))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                imageAnalysis.setAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    QrCodeAnalyzer { result, _ ->
                        viewModel.updateText(result)
                    }
                )
                try {
                    cameraProviderFeature.get().bindToLifecycle(
                        lifecycle,
                        selector,
                        preview,
                        imageAnalysis
                    )

                } catch (e: Exception) {
                    println(e.message)
                }
                previewView
            }, modifier = Modifier.weight(1f))


            Text(
                text = text,
                fontStyle = FontStyle.Normal,
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .clickable {
                        val clip = ClipData.newPlainText("Copied Text", text)
                        clipboardManager.setPrimaryClip(clip)
                        isCopied = true
                    },
                maxLines = 3,
                minLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
    }


}