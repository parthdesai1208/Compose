@file:Suppress("OPT_IN_IS_NOT_ENABLED", "unused")

package com.parthdesai1208.compose.view.uicomponents

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
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
        DividerTextCompose()
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
        DividerTextCompose()
        Text(
            text = "text with gradient background",
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
        DividerTextCompose()
        Text(
            text = "text with background color",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.background(color = colorResource(id = R.color.teal_700))
        )
        DividerTextCompose()
        Text(
            text = "Hello $name, this text is in center",
            color = MaterialTheme.colors.onSurface
        )
        DividerTextCompose()
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
        DividerTextCompose()
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
        DividerTextCompose()
        Text(
            text = "Text with alpha .45f(0f to 1f)",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.alpha(.45f) //used to dim the color of text
        )
        DividerTextCompose()
        Text(
            text = "accessibility text with onClickLabel",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .padding(16.dp)
                .clickable(onClickLabel = "you are Clicking on accessibility text") {}
        )
        DividerTextCompose()
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
        DividerTextCompose()
        Text(
            "Center align text", textAlign = TextAlign.Center,
            modifier = Modifier.width(150.dp), color = MaterialTheme.colors.onSurface,
        )
        DividerTextCompose()
        Text(
            text = "text with shadow",
            color = MaterialTheme.colors.onSurface,
            style = TextStyle(
                fontSize = 24.sp,
                shadow = Shadow(
                    color = Color.Blue,
                    offset = Offset(5.0f, 10.0f),
                    blurRadius = 3f
                )
            )
        )
        DividerTextCompose()
        Text(
            "font family Serif",
            fontFamily = FontFamily.Serif,
            color = MaterialTheme.colors.onSurface
        )
        DividerTextCompose()
        val firaSansFamily = FontFamily(Font(resId = R.font.fira_sans_light, FontWeight.Light))
        Text(
            text = "font family from resource", fontFamily = firaSansFamily,
            color = MaterialTheme.colors.onSurface
        )
        DividerTextCompose()
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("H")
            }
            append("ello ")

            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)) {
                append("W")
            }
            append("orld")
        }, color = MaterialTheme.colors.onSurface)
        DividerTextCompose()
        Text(
            text = buildAnnotatedString {
                withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                    append("Paragraph annotated string:\n")
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append("Hello\n")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    ) {
                        append("World\n")
                    }
                    append("Compose")
                }
            },
            color = MaterialTheme.colors.onSurface
        )
        DividerTextCompose()
        Text("max line 2".repeat(50), maxLines = 2, color = MaterialTheme.colors.onSurface)
        DividerTextCompose()
        Text(
            "Text overflow ".repeat(50), maxLines = 1, overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.onSurface
        )
        DividerTextCompose()
        SelectionContainer {
            Text(
                "Selectable text,\n\nLorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
                modifier = Modifier.padding(all = 8.dp),
                color = MaterialTheme.colors.onSurface
            )
        }
        DividerTextCompose()
        SelectionContainer {
            Column {
                Text("This text is selectable", color = MaterialTheme.colors.onSurface)
                Text("This one too", color = MaterialTheme.colors.onSurface)
                DisableSelection {
                    Text("But not this one", color = MaterialTheme.colors.onSurface)
                    Text("Disable selection text", color = MaterialTheme.colors.onSurface)
                    Text("don't touch me", color = MaterialTheme.colors.onSurface)
                }
                Text("But again, you can select this one", color = MaterialTheme.colors.onSurface)
                Text("And this one too", color = MaterialTheme.colors.onSurface)
            }
        }
        DividerTextCompose()
        ClickableText(
            text = AnnotatedString(
                "text with which character clicked", spanStyle = SpanStyle(color = MaterialTheme.colors.onSurface)
            ),
            onClick = { offset ->
                Toast
                    .makeText(context, "$offset -th character is clicked.", Toast.LENGTH_SHORT)
                    .show()
            }
        )
        DividerTextCompose()
        AnnotatedClickableTextWithURL()
        DividerTextCompose()
    }
}

@Composable
fun DividerTextCompose() {
    Spacer(modifier = Modifier.height(5.dp))
    Divider(
        modifier = Modifier.padding(horizontal = 32.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    )
    Spacer(modifier = Modifier.height(5.dp))
}


@Composable
fun AnnotatedClickableTextWithURL() {
    val context = LocalContext.current
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colors.onSurface)){
            append("Text with URL")
            append("\nClick ")
        }

        // We attach this *URL* annotation to the following content
        // until `pop()` is called
        //means whatever text we want to use as link we put between pushStringAnnotation() & pop()
        pushStringAnnotation(
            tag = "URL",
            annotation = "https://developer.android.com"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Blue.copy(alpha = .5f),
                fontWeight = FontWeight.Bold
            )
        ) {
            append("here")
        }

        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            // We check if there is an *URL* annotation attached to the text
            // at the clicked position
            annotatedText.getStringAnnotations(
                tag = "URL", start = offset,
                end = offset
            )
                .firstOrNull()?.let { annotation ->
                    // If yes, we log its value
                    Toast
                        .makeText(context, "${annotation.item} clicked.", Toast.LENGTH_SHORT)
                        .show()
                }
        },
        modifier = Modifier.padding(all = 8.dp)
    )
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

