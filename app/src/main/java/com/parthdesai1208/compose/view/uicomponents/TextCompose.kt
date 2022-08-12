@file:Suppress("OPT_IN_IS_NOT_ENABLED", "unused")

package com.parthdesai1208.compose.view.uicomponents

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.RainbowColors
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.view.theme.attractions_gmap
import com.parthdesai1208.compose.view.theme.shopping_gmap
import me.saket.extendedspans.*


/*all text related compose things is here in this file*/

class FakeStringProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String>
        get() = sequenceOf("Parth")
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalTextApi::class)
@Composable
fun TextComponents(name: String) { //@PreviewParameter(FakeStringProvider::class)
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val context = LocalContext.current
            Text(
                text = "single click text",
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
                text = "Text with textColor linearGradient".repeat(6),
                style = TextStyle(brush = Brush.linearGradient(colors = RainbowColors))
            )
            DividerTextCompose()
            TextColorGradientAnimation("Text with textColor linearGradient with animation")
            DividerTextCompose()
            TextColorGradientAnimation(
                "Text with textColor linearGradient with reverse animation",
                repeatMode = RepeatMode.Reverse
            )
            DividerTextCompose()
            Text(
                text = "Text with textColor horizontalGradient".repeat(6),
                style = TextStyle(brush = Brush.horizontalGradient(colors = RainbowColors))
            )
            DividerTextCompose()
            Text(
                text = "Text with textColor verticalGradient".repeat(6),
                style = TextStyle(brush = Brush.verticalGradient(colors = RainbowColors))
            )
            DividerTextCompose()
            Text(
                text = "Text with textColor radialGradient".repeat(6),
                style = TextStyle(brush = Brush.radialGradient(colors = RainbowColors))
            )
            DividerTextCompose()
            Text(
                text = "Text with textColor sweepGradient".repeat(6),
                style = TextStyle(brush = Brush.sweepGradient(colors = RainbowColors))
            )
            DividerTextCompose()
            Text(
                text = "text with background color",
                modifier = Modifier.background(color = colorResource(id = R.color.teal_700))
            )
            DividerTextCompose()
            Text(
                text = "Hello $name, this text is in center"
            )
            DividerTextCompose()
            Text(
                text = "circle shape text",
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
                modifier = Modifier.alpha(.45f) //used to dim the color of text
            )
            DividerTextCompose()
            Text(
                text = "accessibility text with onClickLabel",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(onClickLabel = "you are Clicking on accessibility text") {}
            )
            DividerTextCompose()
            Text(
                text = "onLongClick text with accessibility",
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
                modifier = Modifier.width(150.dp),
            )
            DividerTextCompose()
            Text(
                text = "text with shadow",
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
                fontFamily = FontFamily.Serif
            )
            DividerTextCompose()
            val firaSansFamily = FontFamily(Font(resId = R.font.fira_sans_light, FontWeight.Light))
            Text(
                text = "font family from resource", fontFamily = firaSansFamily
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
                }
            )
            DividerTextCompose()
            AnimatingUnderLineWithBackgroundColorText()
            DividerTextCompose()
            Text("max line 2".repeat(50), maxLines = 2)
            DividerTextCompose()
            Text(
                "Text overflow ".repeat(50), maxLines = 1, overflow = TextOverflow.Ellipsis
            )
            DividerTextCompose()
            SelectionContainer {
                Text(
                    "Selectable text,\n\nLorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
                    modifier = Modifier.padding(all = 8.dp)
                )
            }
            DividerTextCompose()
            SelectionContainer {
                Column {
                    Text("This text is selectable")
                    Text("This one too")
                    DisableSelection {
                        Text("But not this one")
                        Text("Disable selection text")
                        Text("don't touch me")
                    }
                    Text(
                        "But again, you can select this one"
                    )
                    Text("And this one too")
                }
            }
            DividerTextCompose()
            ClickableText(
                text = AnnotatedString(
                    "text with which character clicked",
                    spanStyle = SpanStyle(color = MaterialTheme.colors.onSurface)
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
            TextWithUnderLine()
            DividerTextCompose()
            TextWithMiddleLine()
            DividerTextCompose()
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun TextColorGradientAnimation(text: String, repeatMode: RepeatMode = RepeatMode.Restart) {
    val currentFontSizePx = with(LocalDensity.current) { 20.sp.toPx() }
    //20.sp.toPx() = length between the 2 gradient animation
    val currentFontSizeDoublePx = currentFontSizePx * 2

    val infiniteTransition = rememberInfiniteTransition()
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = currentFontSizeDoublePx,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = repeatMode
        )
    )
    Text(
        text = text.repeat(6),
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = RainbowColors,
                start = Offset(offset, offset),
                end = Offset(offset + currentFontSizePx, offset + currentFontSizePx),
                tileMode = TileMode.Mirror
            )
        )
    )
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
        withStyle(style = SpanStyle(color = MaterialTheme.colors.onSurface)) {
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
                color = Color.Cyan.copy(alpha = .5f),
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

@Composable
fun TextWithUnderLine() {
    Text(
        text = "Text with underline",
        textDecoration = TextDecoration.Underline
    )
}

@Composable
fun TextWithMiddleLine() {
    Text(
        text = "Text with middle line",
        textDecoration = TextDecoration.LineThrough
    )
}

@Composable
fun AnimatingUnderLineWithBackgroundColorText() {
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        ExtendedSpansText(text = buildAnnotatedString {
            append("Give your   ")
            withStyle(SpanStyle(background = shopping_gmap.copy(alpha = 0.5f))) {
                append("heart and soul")
            }
            append("   to me")
        })
        ExtendedSpansText(text = buildAnnotatedString {
            append("And life will always be ")
            withStyle(
                SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    color = attractions_gmap
                )
            ) {
                append("la vie en rose")
            }
        })
    }
}

@Composable
fun ExtendedSpansText(
    text: AnnotatedString,
    modifier: Modifier = Modifier
) {
    val underlineAnimator = rememberSquigglyUnderlineAnimator()
    val extendedSpans = remember {
        ExtendedSpans(
            RoundedCornerSpanPainter(
                cornerRadius = 8.sp,
                padding = RoundedCornerSpanPainter.TextPaddingValues(horizontal = 4.sp),
                topMargin = 2.sp,
                bottomMargin = 2.sp,
                stroke = RoundedCornerSpanPainter.Stroke(
                    color = Color(0xFFBF97FF).copy(alpha = 0.6f)
                ),
            ),
            SquigglyUnderlineSpanPainter(
                width = 4.sp,
                wavelength = 20.sp,
                amplitude = 2.sp,
                bottomOffset = 2.sp,
                animator = underlineAnimator
            )
        )
    }

    Text(
        modifier = modifier.drawBehind(extendedSpans),
        text = remember(text) {
            extendedSpans.extend(text)
        },
        onTextLayout = { result ->
            extendedSpans.onTextLayout(result)
        }
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

