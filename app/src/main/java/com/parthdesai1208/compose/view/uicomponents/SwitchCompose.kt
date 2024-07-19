package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.adwi.neumorph.android.MorphSwitch
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.view.theme.RainbowIndigo
import com.parthdesai1208.compose.view.theme.hotel_gmap
import com.parthdesai1208.compose.view.theme.more_gmap
import com.parthdesai1208.compose.view.theme.petrol_gmap

@Composable
fun SwitchCompose(navHostController: NavHostController) {
    var mCheckedState by remember { mutableStateOf(false) }
    var mCheckedState1 by remember { mutableStateOf(false) }
    val context = LocalContext.current
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
                    text = context.getString(R.string.switchsample),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Default Switch")
                Spacer(modifier = Modifier.height(8.dp))
                Switch(checked = mCheckedState, onCheckedChange = { mCheckedState = it })
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Switch State: $mCheckedState")

                DividerTextCompose()

                Text(text = "Disable Switch")
                Spacer(modifier = Modifier.height(8.dp))
                Switch(
                    checked = false, onCheckedChange = {},
                    enabled = false
                )

                DividerTextCompose()

                Text(text = "Switch with custom color")
                Spacer(modifier = Modifier.height(8.dp))
                Switch(
                    checked = mCheckedState1, onCheckedChange = { mCheckedState1 = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = petrol_gmap,
                        checkedTrackColor = hotel_gmap,
                        uncheckedThumbColor = more_gmap,
                        uncheckedTrackColor = RainbowIndigo
                    )
                )

                DividerTextCompose()
                MorphSwitchCompose()
                DividerTextCompose()
            }
        }
    }
}

@Composable
fun MorphSwitchCompose() {
    var value by remember { mutableStateOf(false) }

    MorphSwitch(
        elevation = 10.dp,
        cornerRadius = 10.dp,
        switchColor = MaterialTheme.colors.secondary,
        //lightShadowColor = right-bottom color inside switch
        lightShadowColor = Color.LightGray,
        //darkShadowColor = left-top color inside switch
        darkShadowColor = Color.DarkGray,
        //backgroundColor = color of switch tracker
        backgroundColor = MaterialTheme.colors.surface,
        value = value,
        onValueChange = { value = !value },
        modifier = Modifier
            .width(80.dp)
            .height(40.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = "Morph Switch State: $value")
}

@Preview
@Composable
fun SwitchComposePrev() {
    ComposeTheme {
        SwitchCompose(rememberNavController())
    }
}