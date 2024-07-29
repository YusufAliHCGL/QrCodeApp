package com.example.qrcodeapp.feature_qr_code.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.qrcodeapp.feature_qr_code.presentation.qr_code_generator.QrCodeGeneratorScreen
import com.example.qrcodeapp.feature_qr_code.presentation.qr_code_generator.QrCodeGeneratorViewModel
import com.example.qrcodeapp.feature_qr_code.presentation.qr_code_scanner.QrCodeScannerScreen
import com.example.qrcodeapp.R
import com.example.qrcodeapp.feature_qr_code.presentation.qr_code_scanner.QrCodeScannerViewModel
import com.example.qrcodeapp.feature_qr_code.presentation.util.Screen
import com.example.qrcodeapp.ui.theme.QrCodeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QrCodeAppTheme {
                val navController = rememberNavController()
                val backStackEntry = navController.currentBackStackEntryAsState()
                val qrCodeGeneratorViewModel = viewModel<QrCodeGeneratorViewModel>()
                val qrCodeScannerViewModel = viewModel<QrCodeScannerViewModel>()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            containerColor = Color.Yellow
                        ) {
                            NavigationBarItem(
                                selected = backStackEntry.value?.destination?.route == Screen.QrCodeScannerScreen.route,
                                onClick = {
                                    navController.navigate(route = Screen.QrCodeScannerScreen.route) {
                                        popUpTo(Screen.QrCodeScannerScreen.route) {
                                            inclusive = false
                                        }
                                        launchSingleTop = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_qr_code_scanner_24),
                                        contentDescription = "Qr code scanner",
                                        tint = Color.Cyan
                                    )
                                }
                            )
                            NavigationBarItem(
                                selected = backStackEntry.value?.destination?.route == Screen.QrCodeGeneratorScreen.route,
                                onClick = {
                                    navController.navigate(route = Screen.QrCodeGeneratorScreen.route) {
                                        popUpTo(Screen.QrCodeGeneratorScreen.route) {
                                            inclusive = false
                                        }
                                        launchSingleTop = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_qr_code_2_24),
                                        contentDescription = "Qr code generator",
                                        tint = Color.Cyan
                                    )
                                })
                        }
                    }
                ) { innerPadding ->
                    Surface(
                        color = Color.Green,
                        modifier = Modifier.fillMaxSize()
                    ) {

                        NavHost(navController = navController, startDestination = Screen.QrCodeScannerScreen.route) {
                            composable(route = Screen.QrCodeScannerScreen.route) {
                                QrCodeScannerScreen(paddingValues = innerPadding, viewModel = qrCodeScannerViewModel)
                            }
                            composable(route = Screen.QrCodeGeneratorScreen.route) {
                                QrCodeGeneratorScreen(paddingValues = innerPadding, viewModel = qrCodeGeneratorViewModel)
                            }
                        }


                    }
                }
            }
        }
    }
}
