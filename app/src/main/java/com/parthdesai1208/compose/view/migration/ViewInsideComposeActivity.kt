package com.parthdesai1208.compose.view.migration

import android.content.res.Configuration
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.NumberPicker
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.view.uicomponents.DividerTextCompose

class ViewInsideComposeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PreviewView()
        }
    }

    @Preview(name = "light", showSystemUi = true)
    @Preview(name = "Dark", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun PreviewView() {
        ComposeTheme {
            Surface {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    val verticalPaddingBetweenText = Modifier.padding(vertical = 10.dp)
                    Text(
                        text = "Hello World",
                        modifier = verticalPaddingBetweenText
                    )
                    Text(
                        text = "Here we learn Android View system inside compose",
                        modifier = verticalPaddingBetweenText
                    )
                    Text(
                        text = "Below textview is rendering html content but with compose we can't do it, so for that limitation we have to use Android View inside composable function",
                        textAlign = TextAlign.Center,
                        modifier = verticalPaddingBetweenText
                    )

                    val description =
                        "The avocado (Persea americana) is a tree, long thought to have originated in South Central Mexico, classified as a member of the flowering plant family Lauraceae. The fruit of the plant, also called an avocado (or avocado pear or alligator pear), is botanically a large berry containing a single large seed.<br><br>Avocados are commercially valuable and are cultivated in tropical and Mediterranean climates throughout the world. They have a green-skinned, fleshy body that may be pear-shaped, egg-shaped, or spherical. Commercially, they ripen after harvesting. Avocado trees are partially self-pollinating and are often propagated through grafting to maintain a predictable quality and quantity of the fruit.<br><br>(From <a href=\"https://en.wikipedia.org/wiki/Avocado\">Wikipedia</a>)"
                    val htmlDescription =
                        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)

                    //below AndroidView block is responsible for android view inside compose
                    AndroidView(
                        factory = { context ->
                            //TextView = Android view's TextView
                            TextView(context).apply {
                                //for link to open browser
                                movementMethod = LinkMovementMethod.getInstance()
                                //Note that we have to use Android view's TextView properties, not compose TextView
                                this.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            }
                        },
                        update = { //execute after TextView is rendered
                            it.text = htmlDescription
                        }
                    )
                    DividerTextCompose()
                    var value by remember { mutableStateOf(0) }
                    val numberPickerMaxValue = 20
                    Text(text = "Value = $value")
                    Button(onClick = { value++ }, enabled = value != numberPickerMaxValue) {
                        Text(
                            text = "Click to Add 1\n(update value from compose to view)",
                            textAlign = TextAlign.Center
                        )
                    }
                    AndroidView(factory = { context ->
                        NumberPicker(context).apply {
                            setOnValueChangedListener { _, _, newValue ->
                                value = newValue
                                //update value from view to compose
                            }
                        }
                    }, update = {
                        it.apply {
                            this.value = value
                            maxValue = numberPickerMaxValue
                        }
                    })
                }
            }
        }
    }
}