package com.am.genreclassifier.ui.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.am.genreclassifier.state.ScanButton
import com.am.genreclassifier.ui.theme.ScanButtonColor


private val buttonColor = object : ButtonColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return if (enabled) derivedStateOf { ScanButtonColor }
        else derivedStateOf { ScanButtonColor }
    }

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return derivedStateOf { Color.White }
    }

}


@Composable
fun ScanButtonItem(scanButton: ScanButton, onScanButtonClicked: () -> Unit) {
    Box(modifier = Modifier.height(50.dp)
        .width(124.dp)) {
        Button(
            onClick = onScanButtonClicked,
            shape = RoundedCornerShape(25.dp),
            colors = buttonColor,
            enabled = !scanButton.isLoading,
            modifier = Modifier.matchParentSize()
        ) {
            Text(text = scanButton.buttonText, fontSize = 18.sp)
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewScan(){
    ScanButtonItem(scanButton = ScanButton("Scan", false)) {

    }
}