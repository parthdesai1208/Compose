package com.parthdesai1208.compose.view.uicomponents

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.*
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.view.theme.ComposeTheme


/*all text related compose things is here in this file*/

class FakeStringProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String>
        get() = sequenceOf("Parth")
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TextComponents(name: String) { //@PreviewParameter(FakeStringProvider::class)
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        Text(
            text = "single click text",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .padding(16.dp) //to make clickable area bigger
                .clickable {
                    Toast
                        .makeText(context, "Single Click", Toast.LENGTH_SHORT)
                        .show()
                }
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "disable click text",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .padding(16.dp)
                .clickable(enabled = false) {
                    Toast
                        .makeText(context, "Single Click", Toast.LENGTH_SHORT)
                        .show()
                }
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "text with background using brush",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Magenta,
                        Color.Yellow,
                        Color.DarkGray
                    )
                )
            )
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "text with background color",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.background(color = colorResource(id = R.color.teal_700))
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Hello $name, this text is in center",
            color = MaterialTheme.colors.onSurface
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "circle shape text",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.teal_700),
                    shape = CircleShape
                )
                .padding(horizontal = 5.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Rounded corner shape text",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.teal_700),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 5.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Text with alpha .45f(0f to 1f)",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.alpha(.45f) //used to dim the color of text
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "accessibility text with onClickLabel",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .padding(16.dp)
                .clickable(onClickLabel = "you are Clicking on accessibility text") {}
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "onLongClick text with accessibility",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .padding(16.dp)
                .combinedClickable(onLongClickLabel = "you are Long Clicking on accessibility text",
                    onLongClick = {
                        Toast
                            .makeText(context, "Long Click", Toast.LENGTH_SHORT)
                            .show()
                    }) {}
        )
    }
}

@Preview(name = "Light", showSystemUi = true)
@Preview(name = "Dark", showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(
    name = "landscape",
    showSystemUi = true,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 720,
    heightDp = 360
)
@Composable
fun PreTextInCenter() {
    ComposeTheme {
        TextComponents("World")
    }
}

