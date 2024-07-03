package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.AddBackIconToScreen
import com.parthdesai1208.compose.utils.setSizeByScreenPercentage
import com.parthdesai1208.compose.view.navigation.RowListingScreenPath

//region Row Listing Screen
enum class RowListingEnumType(
    val buttonTitle: Int,
    val func: @Composable (NavHostController) -> Unit
) {
    WrapRowTopStart(R.string.wraprowtopstart, { WrapRowTopStart(it) }),
    WrapRowTop(R.string.wraprowtop, { WrapRowTop(it) }),
    WrapRowTopEnd(R.string.wraprowtopend, { WrapRowTopEnd(it) }),
    WrapRowCenterStart(R.string.wraprowcenterstart, { WrapRowCenterStart(it) }),
    WrapRowCenter(R.string.wraprowcenter, { WrapRowCenter(it) }),
    WrapRowCenterEnd(R.string.wraprowcenterend, { WrapRowCenterEnd(it) }),
    WrapRowBottomStart(R.string.wraprowbottomstart, { WrapRowBottomStart(it) }),
    WrapRowBottom(R.string.wraprowbottom, { WrapRowBottom(it) }),
    WrapRowBottomEnd(R.string.wraprowbottomend, { WrapRowBottomEnd(it) }),

    FillMaxRowTopStart(R.string.fillmaxrowtopstart, { FillMaxRowTopStart(it) }),
    FillMaxRowTop(R.string.fillmaxrowtop, { FillMaxRowTop(it) }),
    FillMaxRowTopEnd(R.string.fillmaxrowtopend, { FillMaxRowTopEnd(it) }),
    FillMaxRowCenterStart(R.string.fillmaxrowcenterstart, { FillMaxRowCenterStart(it) }),
    FillMaxRowCenter(R.string.fillmaxrowcenter, { FillMaxRowCenter(it) }),
    FillMaxRowCenterEnd(R.string.fillmaxrowcenterend, { FillMaxRowCenterEnd(it) }),
    FillMaxRowBottomStart(R.string.fillmaxrowbottomstart, { FillMaxRowBottomStart(it) }),
    FillMaxRowBottom(R.string.fillmaxrowbottom, { FillMaxRowBottom(it) }),
    FillMaxRowBottomEnd(R.string.fillmaxrowbottomend, { FillMaxRowBottomEnd(it) }),
    FillMaxSizeRowChildSpaceEvenly(R.string.fillmaxsizerowchildspaceevenly,
        { FillMaxSizeRowChildSpaceEvenly(it) }),
    FillMaxSizeRowChildSpaceAround(R.string.fillmaxsizerowchildspacearound,
        { FillMaxSizeRowChildSpaceAround(it) }),
    FillMaxSizeRowChildSpaceBetween(R.string.fillmaxsizerowchildspacebetween,
        { FillMaxSizeRowChildSpaceBetween(it) }),

    IndividualChildAlignmentRow(
        R.string.individualchildalignment,
        { IndividualChildAlignmentRow(it) }),
    ChildWeightRow(R.string.childweight_25f_for_all, { ChildWeightRow(it) }),
    ScrollableRow(R.string.scrollablerow, { ScrollableRow(it) }),
    AlignAllChildRow(R.string.apply_same_space_between_all_child_1, { AlignAllChildRow(it) }),
}

@Composable
fun ChildRowScreen(onClickButtonTitle: Int?, navHostController: NavHostController) {
    enumValues<RowListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke(
        navHostController
    )
}

@Composable
fun RowListingScreen(navController: NavController) {
    @Composable
    fun MyButton(
        title: RowListingEnumType
    ) {
        val context = LocalContext.current
        Button(
            onClick = { navController.navigate(RowListingScreenPath(pathPostFix = title.buttonTitle)) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(context.getString(title.buttonTitle), textAlign = TextAlign.Center)
        }
    }
    Surface {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }, imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Row Samples",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                enumValues<RowListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}
//endregion

@Composable
fun CommonBoxForRow(
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier
            .setSizeByScreenPercentage(widthPercentage = 20f, heightPercentage = 5f)
            .background(color = color)
    )
}

@Composable
fun WrapRowTopStart(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    AddBackIconToScreen(screen = {
        Row(
            modifier = modifier.commonBorder(width = 5.dp, color = MaterialTheme.colors.onSurface)
        ) {
            CommonBoxForRow(color = Color.Red)
            CommonBoxForRow(color = Color.Yellow)
            CommonBoxForRow(color = Color.Green)
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

@Composable
fun WrapRowTop(navHostController: NavHostController) {
    WrapRowTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Top)
    )
}

@Composable
fun WrapRowTopEnd(navHostController: NavHostController) {
    WrapRowTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.End)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Top)
    )
}

@Composable
fun WrapRowCenterStart(navHostController: NavHostController) {
    WrapRowTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.Start)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Composable
fun WrapRowCenter(navHostController: NavHostController) {
    WrapRowTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Composable
fun WrapRowCenterEnd(navHostController: NavHostController) {
    WrapRowTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.End)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Composable
fun WrapRowBottomStart(navHostController: NavHostController) {
    WrapRowTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.Start)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Bottom)
    )
}

@Composable
fun WrapRowBottom(navHostController: NavHostController) {
    WrapRowTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Bottom)
    )
}

@Composable
fun WrapRowBottomEnd(navHostController: NavHostController) {
    WrapRowTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.End)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Bottom)
    )
}

@Composable
fun FillMaxRowTopStart(
    navHostController: NavHostController,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
) {
    AddBackIconToScreen(screen = {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .commonBorder(color = MaterialTheme.colors.onSurface),
            verticalAlignment = verticalAlignment,
            horizontalArrangement = horizontalArrangement
        ) {
            CommonBoxForRow(color = Color.Red)
            CommonBoxForRow(color = Color.Yellow)
            CommonBoxForRow(color = Color.Green)
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

@Composable
fun FillMaxRowTop(navHostController: NavHostController) {
    FillMaxRowTopStart(
        navHostController,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    )
}

@Composable
fun FillMaxRowTopEnd(navHostController: NavHostController) {
    FillMaxRowTopStart(
        navHostController,
        verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.End
    )
}

@Composable
fun FillMaxRowCenterStart(navHostController: NavHostController) {
    FillMaxRowTopStart(
        navHostController,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    )
}

@Composable
fun FillMaxRowCenter(navHostController: NavHostController) {
    FillMaxRowTopStart(
        navHostController,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
}

@Composable
fun FillMaxRowCenterEnd(navHostController: NavHostController) {
    FillMaxRowTopStart(
        navHostController,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    )
}

@Composable
fun FillMaxRowBottomStart(navHostController: NavHostController) {
    FillMaxRowTopStart(
        navHostController,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Start
    )
}

@Composable
fun FillMaxRowBottom(navHostController: NavHostController) {
    FillMaxRowTopStart(
        navHostController,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    )
}

@Composable
fun FillMaxRowBottomEnd(navHostController: NavHostController) {
    FillMaxRowTopStart(
        navHostController,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End
    )
}

@Composable
fun FillMaxSizeRowChildSpaceEvenly(navHostController: NavHostController) {
    FillMaxRowTopStart(
        navHostController,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    )
}

@Composable
fun FillMaxSizeRowChildSpaceAround(navHostController: NavHostController) {
    FillMaxRowTopStart(
        navHostController,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    )
}


@Composable
fun FillMaxSizeRowChildSpaceBetween(navHostController: NavHostController) {
    FillMaxRowTopStart(
        navHostController,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
}

@Composable
fun IndividualChildAlignmentRow(navHostController: NavHostController) {
    AddBackIconToScreen(screen = {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentHeight(align = Alignment.Top)
                .height(150.dp)
                .fillMaxWidth()
                .commonBorder(color = MaterialTheme.colors.onSurface, width = 2.dp)
        ) {
            CommonBoxForRow(color = Color.Red, modifier = Modifier.align(Alignment.Top))
            CommonBoxForRow(
                color = Color.Yellow,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            CommonBoxForRow(color = Color.Green, modifier = Modifier.align(Alignment.Bottom))
            CommonBoxForRow(color = Color.Red, modifier = Modifier.align(Alignment.Top))
            CommonBoxForRow(
                color = Color.Yellow,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

@Composable
fun ChildWeightRow(navHostController: NavHostController) {
    AddBackIconToScreen(screen = {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentHeight(align = Alignment.Top)
                .fillMaxWidth()
                .commonBorder(color = MaterialTheme.colors.onSurface, width = 2.dp)
        ) {
            CommonBoxForRow(color = Color.Red, modifier = Modifier.weight(weight = .25f))
            CommonBoxForRow(color = Color.Yellow, modifier = Modifier.weight(weight = .25f))
            CommonBoxForRow(color = Color.Green, modifier = Modifier.weight(weight = .25f))
            CommonBoxForRow(color = Color.Magenta, modifier = Modifier.weight(weight = .25f))
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

@Composable
fun ScrollableRow(navHostController: NavHostController) {
    AddBackIconToScreen(screen = {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentHeight(align = Alignment.Top)
                .fillMaxWidth()
                .commonBorder(color = MaterialTheme.colors.onSurface, width = 2.dp)
                .horizontalScroll(state = rememberScrollState())
        ) {
            CommonBoxForRow(color = Color.Red)
            CommonBoxForRow(color = Color.Yellow)
            CommonBoxForRow(color = Color.Green)
            CommonBoxForRow(color = Color.Magenta)
            CommonBoxForRow(color = Color.Cyan)
            CommonBoxForRow(color = Color.Gray)
            CommonBoxForRow(color = Color.Red)
            CommonBoxForRow(color = Color.Yellow)
            CommonBoxForRow(color = Color.Green)
            CommonBoxForRow(color = Color.Magenta)
            CommonBoxForRow(color = Color.Cyan)
            CommonBoxForRow(color = Color.Gray)
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

@Composable
fun AlignAllChildRow(navHostController: NavHostController) {
    AddBackIconToScreen(screen = {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .commonBorder(color = MaterialTheme.colors.onSurface),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CommonBoxForRow(color = Color.Red)
            CommonBoxForRow(color = Color.Yellow)
            CommonBoxForRow(color = Color.Green)
            CommonBoxForRow(color = Color.Magenta)
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}