package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Water
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.model.uicomponents.WeatherItem
import com.parthdesai1208.compose.model.uicomponents.forecastMockState
import com.parthdesai1208.compose.view.theme.*
import com.parthdesai1208.compose.viewmodel.uicomponents.CustomSliderVM

@Composable
fun SliderCompose(customSliderVM: CustomSliderVM) {
    var sliderPosition by remember { mutableStateOf(0f) }
    var sliderPosition1 by remember { mutableStateOf(0f) }
    var sliderPosition2 by remember { mutableStateOf(0f) }
    var sliderPosition3 by remember { mutableStateOf(0f) }
    val selectedValue: Int by customSliderVM.selected.observeAsState(initial = 0)

    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(horizontal = 32.dp)
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = sliderPosition.toString())
            Slider(value = sliderPosition, onValueChange = { sliderPosition = it })
            DividerTextCompose()
            Text(text = "Disabled slider")
            Slider(value = 0f, onValueChange = { }, enabled = false)
            DividerTextCompose()
            Text(text = "custom Value range(0-15): $sliderPosition1")
            Slider(
                value = sliderPosition1,
                onValueChange = { sliderPosition1 = it },
                valueRange = 0f..15f
            )
            DividerTextCompose()
            Text(text = "Value range(0-15) with step 2(2+1=3)(0to15 every parts divided equally)\npart1:0to5\npart2:5to10\npart3:10to15")
            Text(text = "$sliderPosition2")
            Slider(
                value = sliderPosition2,
                onValueChange = { sliderPosition2 = it },
                valueRange = 0f..15f,
                steps = 2
            )
            DividerTextCompose()
            Text(text = "custom color")
            Slider(
                value = sliderPosition3,
                onValueChange = { sliderPosition3 = it },
                valueRange = 0f..20f,
                steps = 3,
                colors = SliderDefaults.colors(
                    thumbColor = red1000,
                    activeTrackColor = hotel_gmap,
                    inactiveTrackColor = more_gmap,
                    activeTickColor = coffee_gmap,
                    inactiveTickColor = attractions_gmap
                )
            )
            DividerTextCompose()
            Text(text = "Custom Slider")
            WeatherCard(list = forecastMockState, selectedValue = selectedValue, onValueChange = {
                customSliderVM.onValueChanged(it)
            })

            DividerTextCompose()
        }
    }
}

@Composable
fun WeatherCard(list: List<WeatherItem>, selectedValue: Int, onValueChange: (Int) -> Unit) {
    val item by remember(selectedValue, list) {
        derivedStateOf { list[selectedValue] }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            MeasurementView(item)
            ForecastSlider(
                list.map { it.date.split(",")[1] },
                onValueChange = { onValueChange(it) },
                value = selectedValue.toFloat()
            )
        }
    }
}

@Composable
private fun MeasurementView(data: WeatherItem) {
    Text(text = "Hong Kong", style = MaterialTheme.typography.h5)
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = data.date,
        style = MaterialTheme.typography.body2.copy(color = Color.Gray)
    )
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text(
                text = data.temperature.toString(),
                style = MaterialTheme.typography.h1
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "°C",
                style = MaterialTheme.typography.h3
            )
        }
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = data.icon),
            contentDescription = "Weather icon"
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Water, contentDescription = "Water icon")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${data.precipitation}% Precipitation",
                style = MaterialTheme.typography.body2.copy(color = Color.Gray)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Air, contentDescription = "Air icon")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${data.windSpeed} km/h Winds",
                style = MaterialTheme.typography.body2.copy(color = Color.Gray)
            )
        }
    }
}

@Composable
fun ForecastSlider(dates: List<String>, value: Float, onValueChange: (Int) -> Unit) {
    val (sliderValue, setSliderValue) = remember { mutableStateOf(value) }
    val drawPadding = with(LocalDensity.current) { 10.dp.toPx() }
    val textSize = with(LocalDensity.current) { 10.dp.toPx() }
    val lineHeightDp = 10.dp
    val lineHeightPx = with(LocalDensity.current) { lineHeightDp.toPx() }
    val canvasHeight = 50.dp
    val textPaint = android.graphics.Paint().apply {
        color = if (isSystemInDarkTheme()) 0xffffffff.toInt() else 0xffff47586B.toInt()
        textAlign = android.graphics.Paint.Align.CENTER
        this.textSize = textSize
    }
    Box(contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .height(canvasHeight)
                .fillMaxWidth()
                .padding(
                    top = canvasHeight
                        .div(2)
                        .minus(lineHeightDp.div(2))
                )
        ) {
            val yStart = 0f
            val distance = (size.width.minus(2 * drawPadding)).div(dates.size.minus(1))
            dates.forEachIndexed { index, date ->
                drawLine(
                    color = Color.DarkGray,
                    start = Offset(x = drawPadding + index.times(distance), y = yStart),
                    end = Offset(x = drawPadding + index.times(distance), y = lineHeightPx)
                )
                if (index.rem(2) == 1) {
                    this.drawContext.canvas.nativeCanvas.drawText(
                        date,
                        drawPadding + index.times(distance),
                        size.height,
                        textPaint
                    )
                }
            }
        }
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = sliderValue,
            valueRange = 0f..dates.size.minus(1).toFloat(),
            steps = dates.size.minus(2),
            colors = customSliderColors(),
            onValueChange = {
                setSliderValue(it)
                onValueChange(it.toInt())
            })
    }
}

@Composable
private fun customSliderColors(): SliderColors = SliderDefaults.colors(
    activeTickColor = Color.Transparent,
    inactiveTickColor = Color.Transparent
)