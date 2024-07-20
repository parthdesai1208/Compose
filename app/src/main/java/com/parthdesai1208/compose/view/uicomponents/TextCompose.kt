@file:Suppress("OPT_IN_IS_NOT_ENABLED", "unused")

package com.parthdesai1208.compose.view.uicomponents

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.AllDevices
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.utils.RainbowColors
import com.parthdesai1208.compose.utils.delayedClick
import com.parthdesai1208.compose.utils.getBoundingBoxesForRange
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.view.theme.attractions_gmap
import com.parthdesai1208.compose.view.theme.shopping_gmap
import kotlinx.coroutines.delay
import me.saket.extendedspans.ExtendedSpans
import me.saket.extendedspans.RoundedCornerSpanPainter
import me.saket.extendedspans.SquigglyUnderlineSpanPainter
import me.saket.extendedspans.drawBehind
import me.saket.extendedspans.rememberSquigglyUnderlineAnimator


/*all text related compose things is here in this file*/

class FakeStringProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String>
        get() = sequenceOf("Parth")
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TextComponents(
    name: String,
    navHostController: NavHostController
) {
    val changingEndStrings = remember {
        listOf(
            "reach your goals.",
            "achieve your dreams.",
            "be happy.",
            "be healthy.",
            "get rid of depression."
        )
    }

    BuildTopBarWithScreen(
        title = stringResource(id = R.string.text), screen = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
                    .padding(start = 8.dp, end = 8.dp, bottom = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val context = LocalContext.current
                Text(text = stringResource(R.string.singleClickText),
                    modifier = Modifier
                        .padding(16.dp) //to make clickable area bigger
                        .clickable {
                            Toast
                                .makeText(context, "Single Click", Toast.LENGTH_SHORT)
                                .show()
                        })
                DividerTextCompose()
                Text(text = stringResource(R.string.delayedClickText),
                    modifier = Modifier
                        .padding(16.dp)
                        .delayedClick(2000L) {
                            Toast
                                .makeText(
                                    context,
                                    context.getString(R.string.delayedClickText),
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        })
                DividerTextCompose()
                Text(text = "disable click text",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable(enabled = false) {
                            Toast
                                .makeText(context, "Single Click", Toast.LENGTH_SHORT)
                                .show()
                        })
                DividerTextCompose()
                Text(
                    text = "text with gradient background", modifier = Modifier.background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Cyan, Color.Magenta, Color.Yellow, Color.DarkGray
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
                DrawTextAPI()
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
                    text = "circle shape text", modifier = Modifier
                        .background(
                            color = colorResource(id = R.color.teal_700), shape = CircleShape
                        )
                        .padding(horizontal = 5.dp)
                )
                DividerTextCompose()
                Text(
                    text = "Rounded corner shape text", modifier = Modifier
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
                Text(text = "accessibility text with onClickLabel",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable(onClickLabel = "you are Clicking on accessibility text") {})
                DividerTextCompose()
                Text(text = "onLongClick text with accessibility",
                    modifier = Modifier
                        .padding(16.dp)
                        .combinedClickable(onLongClickLabel = "you are Long Clicking on accessibility text",
                            onLongClick = {
                                Toast
                                    .makeText(context, "Long Click", Toast.LENGTH_SHORT)
                                    .show()
                            }) {})
                DividerTextCompose()
                Text(
                    "Center align text", textAlign = TextAlign.Center,
                    modifier = Modifier.width(150.dp),
                )
                DividerTextCompose()
                Text(
                    text = "text with shadow", style = TextStyle(
                        fontSize = 24.sp, shadow = Shadow(
                            color = Color.Blue, offset = Offset(5.0f, 10.0f), blurRadius = 3f
                        )
                    )
                )
                DividerTextCompose()
                Text(
                    "font family Serif", fontFamily = FontFamily.Serif
                )
                DividerTextCompose()
                val firaSansFamily =
                    FontFamily(Font(resId = R.font.fira_sans_light, FontWeight.Light))
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
                Text(text = buildAnnotatedString {
                    withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                        append("Paragraph annotated string:\n")
                        withStyle(style = SpanStyle(color = Color.Blue)) {
                            append("Hello\n")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold, color = Color.Red
                            )
                        ) {
                            append("World\n")
                        }
                        append("Compose")
                    }
                })
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
                ClickableText(text = AnnotatedString(
                    "text with which character clicked",
                    spanStyle = SpanStyle(color = MaterialTheme.colors.onSurface)
                ), onClick = { offset ->
                    Toast.makeText(
                        context,
                        "$offset -th character is clicked.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                })
                DividerTextCompose()
                AnnotatedClickableTextWithURL()
                DividerTextCompose()
                TextWithUnderLine()
                DividerTextCompose()
                TextWithMiddleLine()
                DividerTextCompose()
                TypeWriterAnimation(
                    text = "Everything you need to ",
                    highlightedText = "Everything",
                    changingEndStrings = changingEndStrings
                )
                DividerTextCompose()
                MultipleLineTextAutoMove()
                DividerTextCompose()
            }
        }, onBackIconClick = {
            navHostController.popBackStack()
        })
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun DrawTextAPI(modifier: Modifier = Modifier) {
    val textMeasurer = rememberTextMeasurer()
    val text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 22.sp,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colors.onSurface
            )
        ) {
            append("Hello,")
        }
        withStyle(
            style = SpanStyle(
                brush = Brush.horizontalGradient(colors = RainbowColors),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("\nText using drawTextAPI compose 1.3.0")
        }
    }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    Column {
        Canvas(modifier = modifier
            .fillMaxWidth()
            .height(100.dp), onDraw = {
            drawText(
                textMeasurer = textMeasurer,
                text = text,
                topLeft = Offset(x = 40.dp.toPx(), y = 0.dp.toPx()),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        })

        Canvas(modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)

                textLayoutResult = textMeasurer.measure(
                    AnnotatedString(
                        "Text on Canvas!", spanStyle = SpanStyle(
                            color = Color.Yellow,
                            fontSize = 28.sp,
                            fontFamily = FontFamily.SansSerif,
                        )
                    )
                )

                layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                }
            }) {
            textLayoutResult?.let {
                drawText(
                    textLayoutResult = it,
                    shadow = Shadow(color = Color.Red, offset = Offset(x = 5f, y = 5f)),
                    textDecoration = TextDecoration.Underline,
                    topLeft = Offset(40.dp.toPx(), 0.dp.toPx())
                )
            }
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
            animation = tween(1000, easing = LinearEasing), repeatMode = repeatMode
        )
    )
    Text(
        text = text.repeat(6), style = TextStyle(
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
            tag = "URL", annotation = "https://developer.android.com"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Cyan.copy(alpha = .5f), fontWeight = FontWeight.Bold
            )
        ) {
            append("here")
        }

        pop()
    }

    ClickableText(
        text = annotatedText, onClick = { offset ->
            // We check if there is an *URL* annotation attached to the text
            // at the clicked position
            annotatedText.getStringAnnotations(
                tag = "URL", start = offset, end = offset
            ).firstOrNull()?.let { annotation ->
                // If yes, we log its value
                Toast.makeText(context, "${annotation.item} clicked.", Toast.LENGTH_SHORT).show()
            }
        }, modifier = Modifier.padding(all = 8.dp)
    )
}

@Composable
fun TextWithUnderLine() {
    Text(
        text = "Text with underline", textDecoration = TextDecoration.Underline
    )
}

@Composable
fun TextWithMiddleLine() {
    Text(
        text = "Text with middle line", textDecoration = TextDecoration.LineThrough
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
                    textDecoration = TextDecoration.Underline, color = attractions_gmap
                )
            ) {
                append("la vie en rose")
            }
        })
    }
}

@Composable
fun ExtendedSpansText(
    text: AnnotatedString, modifier: Modifier = Modifier
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
            ), SquigglyUnderlineSpanPainter(
                width = 4.sp,
                wavelength = 20.sp,
                amplitude = 2.sp,
                bottomOffset = 2.sp,
                animator = underlineAnimator
            )
        )
    }

    Text(modifier = modifier.drawBehind(extendedSpans), text = remember(text) {
        extendedSpans.extend(text)
    }, onTextLayout = { result ->
        extendedSpans.onTextLayout(result)
    })
}

@Composable
fun TypeWriterAnimation(text: String, highlightedText: String, changingEndStrings: List<String>) {
    var endText by remember { mutableStateOf("") }
    val textToDisplay = "$text$endText"
    var index by remember { mutableStateOf(0) }
    var selectedPartRects by remember { mutableStateOf(listOf<Rect>()) }
    val highlightStart = text.indexOf(highlightedText)


    LaunchedEffect(key1 = changingEndStrings, block = {
        while (index <= changingEndStrings.size) {
            val part = changingEndStrings[index]

            part.forEachIndexed { index, _ ->
                endText = part.substring(
                    startIndex = 0, endIndex = index + 1
                ) //get substring starting from 0 index & gradually increase index
                delay(100)
            }

            delay(1000) //wait for 1 second & then reverse doing typeWriter animation

            part.forEachIndexed { index, _ ->
                endText = part.substring(
                    startIndex = 0, endIndex = part.length - (index + 1)
                ) //get substring from last index & gradually decrease index
                delay(30)
            }

            delay(500)

            index = (index + 1) % changingEndStrings.size
        }
    })

    Text(
        text = textToDisplay, style = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 40.sp,
            letterSpacing = -(1.6).sp,
            lineHeight = 52.sp
        ),
        modifier = Modifier.drawBehind {
            val borderSize = 20.sp.toPx()

            selectedPartRects.forEach { rect ->
                val selectedRect = rect.translate(0f, -borderSize / 1.5f)
                drawLine(  //85586F
                    color = Color(0XFF85586F).copy(1f),
                    start = Offset(selectedRect.left, selectedRect.bottom),
                    end = selectedRect.bottomRight,
                    strokeWidth = borderSize
                )
            }
        },
        //Callback that is executed when a new text layout is calculated
        onTextLayout = { layoutResult ->
            val start = text.length
            val end = textToDisplay.count()
            selectedPartRects = if (start < end) { //this calculates "changing end strings" bound
                layoutResult
                    .getBoundingBoxesForRange(
                        start = start,
                        end = end - 1
                    )
            } else {
                emptyList() //return emptyList if no "chaning end strings" displayed
            }

            //this calculates "highlightedText" bound from text
            if (highlightStart >= 0) {
                selectedPartRects = selectedPartRects + layoutResult
                    .getBoundingBoxesForRange(
                        start = highlightStart,
                        end = highlightStart + highlightedText.length
                    )
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MultipleLineTextAutoMove() {
    Text(
        text = stringResource(id = R.string.breaking_news_from_us),
        modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
        maxLines = 1,
    )
}

@AllDevices
@Composable
fun TextComposePreview() {
    ComposeTheme {
        TextComponents("World", rememberNavController())
    }
}

