package com.parthdesai1208.compose.view.uicomponents

import android.content.res.Configuration
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.view.theme.ComposeTheme

@Composable
fun ButtonCompose() {
    val context = LocalContext.current
    val colorList = listOf(
        Color.Cyan,
        Color.Magenta,
        Color.Yellow,
        Color.DarkGray
    )
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //button for primary action-use
        Button(onClick = { Toast.makeText(context, "Button Click", Toast.LENGTH_SHORT).show() }) {
            Text("Button")
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
            elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp)
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
            border = BorderStroke(width = 3.dp, color = colorResource(id = R.color.colorAccent)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.pink_700)
            )
        ) {
            Text("Button with custom border")
        }
        Button(onClick = {}, contentPadding = PaddingValues(all = 16.dp)) {
            Text("With ContentPadding Button")
        }
        Button(onClick = {}) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
                Text("Button with icon/text", modifier = Modifier.padding(horizontal = 5.dp))
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
            brush = Brush.horizontalGradient(colors = colorList, tileMode = TileMode.Repeated)
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

        GradientButton(text = "sweep Gradient", brush = Brush.sweepGradient(colors = colorList))

        Row(modifier = Modifier.padding(vertical = 10.dp)) {
            CircularIconButton(icon = Icons.Filled.PlayArrow)
            Spacer(modifier = Modifier.width(8.dp))
            CircularIconButton(icon = Icons.Filled.Pause)
            Spacer(modifier = Modifier.width(8.dp))
            CircularIconButton(icon = Icons.Filled.AttachEmail)
            Spacer(modifier = Modifier.width(8.dp))
            CircularIconButton(icon = Icons.Filled.Settings)
        }

    }
}

@Composable
fun CornerShapeButton() {
    val myShape = RoundedCornerShape(topStart = 50.dp, bottomEnd = 50.dp)
    Spacer(modifier = Modifier.height(8.dp))
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.primary, shape = myShape
            )
            .clip(shape = myShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
                onClick = {}
            )
    ) {
        Text(
            "Different corner shape button\nwith ripple effect",
            modifier = Modifier.padding(horizontal = 50.dp, vertical = 5.dp),
            color = MaterialTheme.colors.onPrimary
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun NoRippleEffect() {
    // This is used to disable the ripple effect
    val interactionSource = remember { MutableInteractionSource() }
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
                indication = null, // Assign null to disable the ripple effect
                interactionSource = interactionSource
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
fun PreviewButtonCompose() {
    ComposeTheme {
        ButtonCompose()
    }
}