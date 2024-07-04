package com.parthdesai1208.compose.view.uicomponents

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardCompose(navHostController: NavHostController) {
    val context = LocalContext.current
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    Surface {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        navHostController.popBackStack()
                    }, imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Card Samples",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Surface(color = Color.LightGray.copy(alpha = .5f)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(state = rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(all = 8.dp)
                    Card(modifier = modifier) { CardContent("Card with modifier") }
                    Card(
                        modifier = modifier,
                        shape = RoundedCornerShape(size = 32.dp)
                    ) {
                        CardContent(title = "RoundedCornerShape Card")
                    }
                    Card(
                        modifier = modifier,
                        shape = RoundedCornerShape(
                            topStart = 8.dp, topEnd = 32.dp,
                            bottomStart = 32.dp, bottomEnd = 8.dp
                        )
                    ) {
                        CardContent(title = "RoundedCornerShape with different corner size Card")
                    }
                    Card(
                        modifier = modifier,
                        backgroundColor = Color.Cyan
                    ) {
                        CardContent(title = "Background Color Card")
                    }
                    Card(
                        modifier = modifier,
                        contentColor = Color.Green
                    ) {
                        CardContent(title = "content Color Card")
                    }
                    Card(
                        modifier = modifier,
                        border = BorderStroke(width = 5.dp, color = MaterialTheme.colors.onSurface)
                    ) {
                        CardContent(title = "Card with Border")
                    }
                    Card(
                        modifier = modifier,
                        elevation = 16.dp
                    ) {
                        CardContent(title = "Card with 16.dp elevation")
                    }
                    Card(
                        modifier = modifier,
                        enabled = false,
                        onClick = {}
                    ) {
                        CardContent(title = "Disabled Card")
                    }
                    Card(
                        modifier = modifier,
                        onClick = {
                            Toast.makeText(context, "Card Clicked", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        CardContent(title = "Clickable Card")
                    }

                    Card(
                        modifier = modifier
                            .scale(scale.value)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = {
                                    scope.launch {
                                        scale.animateTo(
                                            targetValue = 0.9f,
                                            animationSpec = tween(100)
                                        )   //down animation
                                        scale.animateTo(
                                            targetValue = 1f,
                                            animationSpec = tween(100)
                                        )     //up animation
                                    }
                                }
                            )
                    ) {
                        CardContent(title = "Card with click animation")
                    }

                }
            }
        }
    }
}

@Composable
private fun CardContent(title: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = title, style = MaterialTheme.typography.body1)
        Text(text = "Text 2", style = MaterialTheme.typography.body1)
        Text(text = "Text 3", style = MaterialTheme.typography.body1)
        Button(
            onClick = { },
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(end = 8.dp, bottom = 8.dp)
        ) {
            Text(text = "Submit")
        }
    }
}

@Preview(name = "light", showSystemUi = true)
@Preview(name = "Dark", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CardComposePrev() {
    CardCompose(rememberNavController())
}