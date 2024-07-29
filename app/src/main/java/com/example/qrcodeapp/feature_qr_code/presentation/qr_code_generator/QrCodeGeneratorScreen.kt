package com.example.qrcodeapp.feature_qr_code.presentation.qr_code_generator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QrCodeGeneratorScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: QrCodeGeneratorViewModel
) {


    val state = viewModel.generateState
    val width = LocalConfiguration.current.smallestScreenWidthDp
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        state.value.bmp?.let {
            Image(
                modifier = Modifier.size(width.dp, width.dp),
                bitmap = state.value.bmp!!.asImageBitmap(),
                contentDescription = "Generated qr code"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicTextField(
                value = state.value.text,
                onValueChange = {
                    viewModel.onEvent(GenerateEvents.ChangeText(it))
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold
                    ),
                maxLines = 2,
                minLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White)
                    .padding(horizontal = 4.dp, vertical = 10.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Button(onClick = {
                if (state.value.text.isNotEmpty()) {
                    viewModel.onEvent(GenerateEvents.CreateBitmapQrCode(width.dp))
                }
            }) {

                Text(
                    text = "Generate Qr Code",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                )
            }
        }

    }


}