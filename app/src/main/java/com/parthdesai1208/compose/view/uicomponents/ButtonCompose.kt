package com.parthdesai1208.compose.view.uicomponents

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachEmail
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Satellite
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.ToolBarWithIconAndTitle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ButtonCompose(navHostController: NavHostController) {
    val context = LocalContext.current
    val colorList = listOf(
        Color.Cyan,
        Color.Magenta,
        Color.Yellow,
        Color.DarkGray
    )
    var enableClick by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = enableClick, block = {
        if(enableClick) return@LaunchedEffect
        delay(5000L)
        enableClick = true
    })
    Surface {
        Column {
            ToolBarWithIconAndTitle(
                screenTitle = stringResource(id = R.string.button),
                onBackArrowClick = { navHostController.popBackStack() }
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = 8.dp) //space between child
            ) {
                //button for primary action-use
                Button(onClick = {
                    Toast.makeText(context, "Button Click", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Button")
                }
                Button(onClick = {
                    if (enableClick) {
                        enableClick = false
                        //onClick business logic should start from here
                        Toast.makeText(context, "Button Click", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Button with delayed click")
                }
                Button(
                    onClick = {}, colors = ButtonDefaults.buttonColors(
                        //contentColor for text
                        //backgroundColor for rest of the button area
                        contentColor = colorResource(id = R.color.colorPrimaryDark),
                        backgroundColor = colorResource(id = R.color.colorAccent)
                    )
                ) {
                    Text("with color Button")
                }
                Button(enabled = false, onClick = {}) {
                    Text("Disable Button")
                }
                Button(
                    onClick = {},
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 16.dp
                    )
                ) {
                    Text("Elevated Button")
                }
                Button(onClick = {}, shape = MaterialTheme.shapes.small) {
                    Text("Small Shape Button")
                }
                Button(onClick = {}, shape = MaterialTheme.shapes.medium) {
                    Text("Medium Shape Button")
                }
                Button(onClick = {}, shape = MaterialTheme.shapes.large) {
                    Text("Large Shape Button")
                }
                Button(
                    onClick = {},
                    border = BorderStroke(
                        width = 3.dp,
                        color = colorResource(id = R.color.colorAccent)
                    ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.pink_700)
                    )
                ) {
                    Text("Button with custom border color")
                }

                ButtonWithGradientBorder()

                Button(onClick = {}, contentPadding = PaddingValues(all = 16.dp)) {
                    Text("With ContentPadding Button")
                }
                Button(onClick = {}) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null)
                        Text(
                            "Button with icon/text",
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                        Icon(imageVector = Icons.Default.Satellite, contentDescription = null)
                    }
                }
                //OutlinedButton for not primary action, for secondary button
                OutlinedButton(onClick = {
                    Toast.makeText(
                        context,
                        "Outlined Button Click",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Text(text = "Outlined Button")
                }
                OutlinedButton(onClick = { }) {
                    Text(
                        text = "Outlined Button with text color",
                        color = colorResource(id = R.color.pink_700)
                    )
                }
                OutlinedButton(onClick = { }, enabled = false) {
                    Text(text = "Disable Outlined Button")
                }
                OutlinedButton(onClick = {}) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null)
                        Text(
                            "OutlinedButton with icon/text",
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                        Icon(imageVector = Icons.Default.Satellite, contentDescription = null)
                    }
                }
                //TextButton for provide action in Dialog,Cards,Surface,etc.
                TextButton(onClick = {
                    Toast.makeText(context, "Text Button Click", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "Text Button", color = MaterialTheme.colors.error)
                }
                TextButton(onClick = {}) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null)
                        Text(
                            text = "TextButton with icon",
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                        Icon(imageVector = Icons.Default.Satellite, contentDescription = null)
                    }
                }
                //IconButton for provide action in Toolbar,navBar,etc.
                IconButton(onClick = {
                    Toast.makeText(context, "Icon Button Click", Toast.LENGTH_SHORT).show()
                }, modifier = Modifier.padding(all = 8.dp)) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSurface
                    )
                }

                NoRippleEffect()

                CornerShapeButton()

                GradientButton(
                    text = "Linear Gradient with Clamp",
                    brush = Brush.linearGradient(colors = colorList, tileMode = TileMode.Clamp)
                )
                GradientButton(
                    text = "Linear Gradient with repeat",
                    brush = Brush.linearGradient(colors = colorList, tileMode = TileMode.Repeated)
                )
                GradientButton(
                    text = "Linear Gradient with Mirror",
                    brush = Brush.linearGradient(colors = colorList, tileMode = TileMode.Mirror)
                )
                GradientButton(
                    text = "Linear Gradient with Decal",
                    brush = Brush.linearGradient(colors = colorList, tileMode = TileMode.Decal)
                )

                GradientButton(
                    text = "radial Gradient with Clamp",
                    brush = Brush.radialGradient(colors = colorList, tileMode = TileMode.Clamp)
                )
                GradientButton(
                    text = "radial Gradient with Repeated",
                    brush = Brush.radialGradient(colors = colorList, tileMode = TileMode.Repeated)
                )
                GradientButton(
                    text = "radial Gradient with Mirror",
                    brush = Brush.radialGradient(colors = colorList, tileMode = TileMode.Mirror)
                )
                GradientButton(
                    text = "radial Gradient with Decal",
                    brush = Brush.radialGradient(colors = colorList, tileMode = TileMode.Decal)
                )

                GradientButton(
                    text = "horizontal Gradient with Clamp",
                    brush = Brush.horizontalGradient(colors = colorList, tileMode = TileMode.Clamp)
                )
                GradientButton(
                    text = "horizontal Gradient with Repeated",
                    brush = Brush.horizontalGradient(
                        colors = colorList,
                        tileMode = TileMode.Repeated
                    )
                )
                GradientButton(
                    text = "horizontal Gradient with Mirror",
                    brush = Brush.horizontalGradient(colors = colorList, tileMode = TileMode.Mirror)
                )
                GradientButton(
                    text = "horizontal Gradient with Decal",
                    brush = Brush.horizontalGradient(colors = colorList, tileMode = TileMode.Decal)
                )

                GradientButton(
                    text = "vertical Gradient with Clamp",
                    brush = Brush.verticalGradient(colors = colorList, tileMode = TileMode.Clamp)
                )
                GradientButton(
                    text = "vertical Gradient with Repeated",
                    brush = Brush.verticalGradient(colors = colorList, tileMode = TileMode.Repeated)
                )
                GradientButton(
                    text = "vertical Gradient with Mirror",
                    brush = Brush.verticalGradient(colors = colorList, tileMode = TileMode.Mirror)
                )
                GradientButton(
                    text = "vertical Gradient with Decal",
                    brush = Brush.verticalGradient(colors = colorList, tileMode = TileMode.Decal)
                )

                GradientButton(
                    text = "sweep Gradient",
                    brush = Brush.sweepGradient(colors = colorList)
                )

                Row(modifier = Modifier.padding(vertical = 10.dp)) {
                    CircularIconButton(icon = Icons.Filled.PlayArrow)
                    Spacer(modifier = Modifier.width(8.dp))
                    CircularIconButton(icon = Icons.Filled.Pause)
                    Spacer(modifier = Modifier.width(8.dp))
                    CircularIconButton(icon = Icons.Filled.AttachEmail)
                    Spacer(modifier = Modifier.width(8.dp))
                    CircularIconButton(icon = Icons.Filled.Settings)
                }

                Row(modifier = Modifier.padding(vertical = 10.dp)) {
                    ButtonWithClickAnimation()
                    Spacer(modifier = Modifier.width(8.dp))
                    HeartAnimation()
                }
                PressedButton()
            }
        }
    }
}

@Composable
fun ButtonWithGradientBorder() {
    OutlinedButton(
        onClick = { },
        border = BorderStroke(
            width = 3.dp,
            brush = Brush.linearGradient(colors = listOf(Color(0xFFffe53b), Color(0xFFff2525)))
        )
    ) {
        Text(text = "(Outlined) Button with gradient border")
    }
}

@Composable
fun HeartAnimation() {
    val interactionSource = remember { MutableInteractionSource() }

    val coroutineScope = rememberCoroutineScope()

    var enabled by remember { mutableStateOf(false) }

    val scale = remember { Animatable(1f) }

    Icon(
        imageVector = Icons.Outlined.Favorite,
        contentDescription = "Like the product",
        tint = if (enabled) Color.Red else Color.LightGray,
        modifier = Modifier
            .scale(scale = scale.value)
            .size(size = 36.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                enabled = !enabled
                coroutineScope.launch {
                    scale.animateTo(
                        0.8f,
                        animationSpec = tween(100),
                    )
                    scale.animateTo(
                        1f,
                        animationSpec = tween(100),
                    )
                }
            }
    )

}

@Composable
fun ButtonWithClickAnimation() {
    val interactionSource = remember { MutableInteractionSource() }

    val scale = remember { Animatable(1f) }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .scale(scale.value)
            .background(color = MaterialTheme.colors.primary, shape = RoundedCornerShape(10.dp))
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
                })
    ) {
        Text(
            text = "Button with click animation",
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(all = 8.dp)
        )
    }
}

@Composable
fun CornerShapeButton() {
    val myShape = RoundedCornerShape(topStart = 50.dp, bottomEnd = 50.dp)

    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.primary, shape = myShape
            )
            .clip(shape = myShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true),
                onClick = {}
            )
    ) {
        Text(
            "Different corner shape button\nwith ripple effect",
            modifier = Modifier.padding(horizontal = 50.dp, vertical = 5.dp),
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun NoRippleEffect() {
    // This is used to disable the ripple effect
    val interactionSource = remember { MutableInteractionSource() } //step-1
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp)
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(
                indication = null, // Assign null to disable the ripple effect //step-3
                interactionSource = interactionSource //step-2
            ) {

            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No Ripple",
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun GradientButton(text: String, brush: Brush) {
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues()
        //PaddingValues(width = 0,height = 0) for stretching box with Button
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .background(
                    brush = brush, shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = text, color = Color.White, fontSize = 15.sp)
        }
    }
}

@Composable
fun CircularIconButton(icon: ImageVector) {
    OutlinedButton(
        modifier = Modifier.size(50.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(12.dp),
        elevation = ButtonDefaults.elevation(4.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = MaterialTheme.colors.primary
        ),
        border = BorderStroke(0.dp, Color.Transparent), onClick = {}) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colors.onPrimary)
    }
}

@Composable
fun PressedButton() {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
//more at https://developer.android.com/jetpack/compose/handling-interaction?s=09&utm_source=pocket_mylist

    Button(onClick = { }, interactionSource = interactionSource) {
        AnimatedVisibility(visible = isPressed) {
            if (isPressed) {
                Row {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = null)
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                }
            }
        }
        Text("Add to cart(press & hold)")
    }
}