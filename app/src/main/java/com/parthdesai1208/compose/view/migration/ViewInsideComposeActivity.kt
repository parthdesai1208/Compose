package com.parthdesai1208.compose.view.migration

import android.content.res.Configuration
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.NumberPicker
import android.widget.TextView
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
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
        val onBackPressedDispatcher =
            LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

        ComposeTheme {
            BuildTopBarWithScreen(
                title = stringResource(id = R.string.go_to_view_inside_compose_activity),
                screen = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(8.dp)
                    ) {
                        val verticalPaddingBetweenText = Modifier.padding(vertical = 10.dp)
                        Text(
                            text = stringResource(R.string.hello_world),
                            modifier = verticalPaddingBetweenText
                        )
                        Text(
                            text = stringResource(R.string.here_we_learn_android_view_system_inside_compose),
                            modifier = verticalPaddingBetweenText
                        )
                        Text(
                            text = stringResource(R.string.below_textview_is_rendering_html_content),
                            textAlign = TextAlign.Center,
                            modifier = verticalPaddingBetweenText
                        )

                        val description =
                            stringResource(R.string.the_avocado_lorem_ipsum)
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
                        var value by remember { mutableIntStateOf(0) }
                        val numberPickerMaxValue by remember { mutableIntStateOf(20) }
                        Text(text = stringResource(R.string.valueEqualTo, value))
                        Button(onClick = { value++ }, enabled = value != numberPickerMaxValue) {
                            Text(
                                text = stringResource(R.string.click_to_add_1_update_value_from_compose_to_view),
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
                },
                onBackIconClick = {
                    onBackPressedDispatcher?.onBackPressed()
                })
        }
    }
}