package com.parthdesai1208.compose.view.draw

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.view.navigation.DrawListingScreenPath
import com.parthdesai1208.compose.view.theme.ComposeTheme


enum class DrawListingEnumType(
    val buttonTitle: Int,
    val func: @Composable (NavHostController) -> Unit
) {
    DrawLines(R.string.drawline, { DrawLine(it) }),
    DrawRect(
        R.string.drawrect,
        { DrawRect(it) }),
    DrawLineFromTopRightToBottomLeft(
        R.string.drawline_from_topright_to_bottomleft,
        { DrawLineFromTopRightToBottomLeft(it) }),
    DrawDashLineFromTopRightToBottomLeft(
        R.string.draw_dash_line_from_top_right_to_bottom_left,
        { DrawDashLineFromTopRightToBottomLeft(it) }),
    DrawText(R.string.drawtext, { DrawText(it) }),
    MeasureText(R.string.measure_text, { MeasureText(it) }),
    MeasureTextWithNarrowWidth(
        R.string.measure_text_with_narrow_size,
        { MeasureTextWithNarrowWidth(it) }),
    DrawImage(R.string.draw_image, { DrawImage(it) }),
    DrawCircle(R.string.draw_circle, { DrawCircle(it) }),
    DrawRoundedRect(R.string.draw_roundedrect, { DrawRoundedRect(it) }),
    DrawOval(R.string.drawoval, { DrawOval(it) }),
    DrawArc(R.string.drawarc, { DrawArc(it) }),
    DrawPoint(R.string.drawpoint, { DrawPoint(it) }),
    DrawPath(R.string.drawpath, { DrawPath(it) }),
    AccessingCanvasObject(R.string.accessing_canvas_object, { AccessingCanvasObject(it) }),
    IconsUsingCanvasDraw(R.string.icons_using_canvas_draw, { IconsUsingCanvasDraw(it) }),
    DrawTriangleWithCornerPathEffects(
        R.string.draw_triangle_with_cornerpatheffects,
        { DrawTriangleWaterDropletWithCornerPathEffects(it) }),
    ChainPathEffectSimpleExample(
        R.string.chainpatheffect_simple_example,
        { ChainPathEffectSimpleExample(it) }),
    ChainPathEffectExample(
        R.string.gooey_effect_using_chainpatheffect_example,
        { GooeyEffectUsingChainPathEffect(it) }),
    StampedPathEffectExample(R.string.stampedpatheffect_example, { StampedPathEffectExample(it) }),
    ScalingTransformation(R.string.scalingtransformation, { ScalingTransformation(it) }),
    TranslateTransformation(R.string.translate_transformation, { TranslateTransformation(it) }),
    RotateTransformation(R.string.rotate_transformation, { RotateTransformation(it) }),
    InsetTransformation(R.string.inset_transformation, { InsetTransformation(it) }),
    MultipleTransformation(R.string.multiple_transformation, { MultipleTransformation(it) }),
    TruckArtCompose(R.string.truckart_pattern, { TruckArtCompose(it) }),
    RightShadow3DLayout(R.string._3d_layout_right_shadow, { RightShadow3DLayout(it) }),
    LeftShadow3DLayout(R.string._3d_layout_left_shadow, { LeftShadow3DLayout(it) }),
    BottomShadow3DLayout(R.string._3d_layout_bottom_shadow, { BottomShadow3DLayout(it) }),
    Dialog3D(R.string._3d_dialog, { Dialog3D(it) }),
    DrawCricketGround(R.string.drawCricketGround, { DrawCricketGround(it) }),
    DrawFootballGround(R.string.drawFootballGround, { DrawFootballGround(it) }),
}

@Composable
fun DrawListingScreen(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: DrawListingEnumType,
    ) {
        Button(
            onClick = { navController.navigate(DrawListingScreenPath(title.buttonTitle)) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(stringResource(id = title.buttonTitle), textAlign = TextAlign.Center)
        }
    }
    BuildTopBarWithScreen(
        title = stringResource(id = R.string.drawsamples),
        screen = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                enumValues<DrawListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        },
        onBackIconClick = {
            navController.popBackStack()
        })
}

@Composable
fun ChildDrawScreen(onClickButtonTitle: Int?, navController: NavHostController) {
    enumValues<DrawListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke(
        navController
    )
}

@Phone
@Composable
fun PreviewDrawNavGraph() {
    ComposeTheme {
        DrawListingScreen(rememberNavController())
    }
}