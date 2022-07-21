package com.am.genreclassifier.ui.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.am.genreclassifier.model.ScanButton
import com.am.genreclassifier.ui.color.AddButtonColor
import com.am.genreclassifier.ui.color.GradientColorEnd
import com.am.genreclassifier.ui.color.ScanButtonColor


private val buttonColor = object : ButtonColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return if (enabled) derivedStateOf { AddButtonColor }
        else derivedStateOf { GradientColorEnd }
    }

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return derivedStateOf { Color.White }
    }

}


@Composable
fun AddButton(onAddTrackClicked: () -> Unit) {
        Button(onClick = onAddTrackClicked,
            modifier = Modifier.size(50.dp),
            colors = buttonColor,
            shape = CircleShape
        ) {
            Text(text = "+")
        }
}



@Preview(showSystemUi = true)
@Composable
fun PreviewAdd(){
    AddButton {

    }
}