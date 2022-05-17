package com.parthdesai1208.compose.view.uicomponents

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Satellite
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.view.theme.ComposeTheme

@Composable
fun ButtonCompose() {
    val context = LocalContext.current
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
        Button(onClick = {}, colors = ButtonDefaults.buttonColors(
                //contentColor for text
                //backgroundColor for rest of the button area
                contentColor = colorResource(id = R.color.colorPrimaryDark)
            , backgroundColor = colorResource(id = R.color.colorAccent))) {
            Text("with color Button")
        }
        Button(enabled = false, onClick = {}) {
            Text("Disable Button")
        }
        Button(onClick = {}, elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp)) {
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
        Button(onClick = {}, border = BorderStroke(width = 3.dp, color = colorResource(id = R.color.colorAccent))
        ,colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.pink_700))) {
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
        OutlinedButton(onClick = {  }) {
            Text(text = "Outlined Button with text color", color = colorResource(id = R.color.pink_700))
        }
        OutlinedButton(onClick = {  }, enabled = false) {
            Text(text = "Disable Outlined Button")
        }
        OutlinedButton(onClick = {}) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
                Text("OutlinedButton with icon/text", modifier = Modifier.padding(horizontal = 5.dp))
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
                Text(text = "TextButton with icon", color = MaterialTheme.colors.error
                ,modifier = Modifier.padding(horizontal = 5.dp))
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
    }
}

@Preview(name = "light", showSystemUi = true)
@Preview(name = "Dark", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewButtonCompose() {
    ComposeTheme {
        ButtonCompose()
    }
}