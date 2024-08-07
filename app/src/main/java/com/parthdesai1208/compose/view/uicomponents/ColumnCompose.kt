package com.parthdesai1208.compose.view.uicomponents


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.utils.setSizeByScreenPercentage
import com.parthdesai1208.compose.view.navigation.ColumnListingScreenPath
import com.parthdesai1208.compose.view.theme.Amber600
import com.parthdesai1208.compose.view.theme.Green800
import com.parthdesai1208.compose.view.theme.red1000

//region Column Listing Screen
enum class ColumnListingEnumType(
    val buttonTitle: Int,
    val func: @Composable (NavHostController) -> Unit
) {
    SimpleColumn(R.string.wrapcolumntopstart, { WrapColumnTopStart(it) }),
    WrapColumnTop(R.string.wrapcolumntop, { WrapColumnTop(it) }),
    WrapColumnTopEnd(R.string.wrapcolumntopend, { WrapColumnTopEnd(it) }),
    WrapColumnCenterStart(R.string.wrapcolumncenterstart, { WrapColumnCenterStart(it) }),
    WrapColumnCenter(R.string.wrapcolumncenter, { WrapColumnCenter(it) }),
    WrapColumnCenterEnd(R.string.wrapcolumncenterend, { WrapColumnCenterEnd(it) }),
    WrapColumnBottomStart(R.string.wrapcolumnbottomstart, { WrapColumnBottomStart(it) }),
    WrapColumnBottom(R.string.wrapcolumnbottom, { WrapColumnBottom(it) }),
    WrapColumnBottomEnd(R.string.wrapcolumnbottomend, { WrapColumnBottomEnd(it) }),
    FillMaxSizeColumn(R.string.fill_max_size_column, { FillMaxSizeColumn(it) }),
    FillMaxSizeChildTop(R.string.fillmaxsizechildtop, { FillMaxSizeChildTop(it) }),
    FillMaxSizeChildTopEnd(R.string.fillmaxsizechildtopend, { FillMaxSizeChildTopEnd(it) }),
    FillMaxSizeChildCenterStart(
        R.string.fillmaxsizechildcenterstart,
        { FillMaxSizeChildCenterStart(it) }),
    FillMaxSizeChildCenter(R.string.fillmaxsizechildcenter, { FillMaxSizeChildCenter(it) }),
    FillMaxSizeChildCenterEnd(
        R.string.fillmaxsizechildcenterend,
        { FillMaxSizeChildCenterEnd(it) }),
    FillMaxSizeChildBottomStart(
        R.string.fillmaxsizechildbottomstart,
        { FillMaxSizeChildBottomStart(it) }),
    FillMaxSizeChildBottomCenter(
        R.string.fillmaxsizechildbottomcenter,
        { FillMaxSizeChildBottomCenter(it) }),
    FillMaxSizeChildBottomEnd(
        R.string.fillmaxsizechildbottomend,
        { FillMaxSizeChildBottomEnd(it) }),
    FillMaxSizeChildSpaceEvenly(
        R.string.fillmaxsizechildspaceevenly,
        { FillMaxSizeChildSpaceEvenly(it) }),
    FillMaxSizeChildSpaceAround(
        R.string.fillmaxsizechildspacearound,
        { FillMaxSizeChildSpaceAround(it) }),
    FillMaxSizeChildSpaceBetween(
        R.string.fillmaxsizechildspacebetween,
        { FillMaxSizeChildSpaceBetween(it) }),

    IndividualChildAlignment(R.string.individual_child_alignment, { IndividualChildAlignment(it) }),
    ChildWeight(R.string.child_weight, { ChildWeight(it) }),

    ScrollableColumn(R.string.scrollable_column, { ScrollableColumn(it) }),

    AlignAllChild(R.string.apply_same_space_between_all_child, { AlignAllChild(it) }),
}

@Composable
fun ChildColumnScreen(onClickButtonTitle: Int?, navController: NavHostController) {
    enumValues<ColumnListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke(
        navController
    )
}

@Composable
fun ColumnListingScreen(navController: NavController) {
    @Composable
    fun MyButton(
        title: ColumnListingEnumType
    ) {
        val context = LocalContext.current
        Button(
            onClick = { navController.navigate(ColumnListingScreenPath(title.buttonTitle)) },
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
                    text = "Column Samples",
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
                enumValues<ColumnListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}
//endregion

fun Modifier.commonBorder(width: Dp = 10.dp, color: Color = Green800): Modifier {
    return this.border(width = width, color = color)
}

@Composable
fun WrapColumnTopStart(navHostController: NavHostController, modifier: Modifier = Modifier) {
    BuildTopBarWithScreen(screen = {
        Column(
            modifier = modifier.commonBorder()
        ) {
            CommonBoxForColumn1()
            CommonBoxForColumn2()
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

@Composable
fun WrapColumnTop(navHostController: NavHostController) {
    WrapColumnTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Top)
    )
}

@Composable
fun WrapColumnTopEnd(navHostController: NavHostController) {
    WrapColumnTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.End)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Top)
    )
}

@Composable
fun WrapColumnCenterStart(navHostController: NavHostController) {
    WrapColumnTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.Start)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Composable
fun WrapColumnCenter(navHostController: NavHostController) {
    WrapColumnTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Composable
fun WrapColumnCenterEnd(navHostController: NavHostController) {
    WrapColumnTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.End)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Composable
fun WrapColumnBottomStart(navHostController: NavHostController) {
    WrapColumnTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.Start)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Bottom)
    )
}

@Composable
fun WrapColumnBottom(navHostController: NavHostController) {
    WrapColumnTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Bottom)
    )
}

@Composable
fun WrapColumnBottomEnd(navHostController: NavHostController) {
    WrapColumnTopStart(
        navHostController,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.End)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Bottom)
    )
}

@Composable
private fun CommonBoxForColumn2(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .setSizeByScreenPercentage(70f, 20f)
            .border(width = 20.dp, color = red1000)
    )
}

@Composable
private fun CommonBoxForColumn1(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .setSizeByScreenPercentage(70f, 20f)
            .border(width = 20.dp, color = Amber600)
    )
}


@Composable
fun FillMaxSizeColumn(
    navHostController: NavHostController,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    BuildTopBarWithScreen(screen = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .commonBorder(),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            CommonBoxForColumn1()
            CommonBoxForColumn2()
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

@Composable
fun FillMaxSizeChildTop(navHostController: NavHostController) {
    FillMaxSizeColumn(
        navHostController = navHostController,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

@Composable
fun FillMaxSizeChildTopEnd(navHostController: NavHostController) {
    FillMaxSizeColumn(navHostController = navHostController, horizontalAlignment = Alignment.End)
}

@Composable
fun FillMaxSizeChildCenterStart(navHostController: NavHostController) {
    FillMaxSizeColumn(
        navHostController = navHostController,
        verticalArrangement = Arrangement.Center
    )
}

@Composable
fun FillMaxSizeChildCenter(navHostController: NavHostController) {
    FillMaxSizeColumn(
        navHostController = navHostController,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

@Composable
fun FillMaxSizeChildCenterEnd(navHostController: NavHostController) {
    FillMaxSizeColumn(
        navHostController = navHostController,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    )
}

@Composable
fun FillMaxSizeChildBottomStart(navHostController: NavHostController) {
    FillMaxSizeColumn(
        navHostController = navHostController,
        verticalArrangement = Arrangement.Bottom
    )
}

@Composable
fun FillMaxSizeChildBottomCenter(navHostController: NavHostController) {
    FillMaxSizeColumn(
        navHostController = navHostController,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

@Composable
fun FillMaxSizeChildBottomEnd(navHostController: NavHostController) {
    FillMaxSizeColumn(
        navHostController = navHostController,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    )
}

@Composable
fun FillMaxSizeChildSpaceEvenly(navHostController: NavHostController) {
    FillMaxSizeColumn(
        navHostController = navHostController,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

@Composable
fun FillMaxSizeChildSpaceAround(navHostController: NavHostController) {
    FillMaxSizeColumn(
        navHostController = navHostController,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

@Composable
fun FillMaxSizeChildSpaceBetween(navHostController: NavHostController) {
    FillMaxSizeColumn(
        navHostController = navHostController,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

@Composable
fun IndividualChildAlignment(navHostController: NavHostController) {
    BuildTopBarWithScreen(screen = {
        Column(
            modifier = Modifier
                .commonBorder()
                .fillMaxSize()
        ) {
            CommonBoxForColumn1(modifier = Modifier.align(alignment = Alignment.Start))
            CommonBoxForColumn2(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
            CommonBoxForColumn1(modifier = Modifier.align(alignment = Alignment.End))
        }
    }, onBackIconClick = { navHostController.popBackStack() })
}

@Composable
fun ChildWeight(navHostController: NavHostController) {
    BuildTopBarWithScreen(screen = {
        Column(
            modifier = Modifier
                .commonBorder()
                .fillMaxSize()
        ) {
            CommonBoxForColumn1(modifier = Modifier.weight(weight = .5f))
            CommonBoxForColumn2(modifier = Modifier.weight(weight = .5f))
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

@Composable
fun ScrollableColumn(navHostController: NavHostController) {
    BuildTopBarWithScreen(screen = {
        Column(
            modifier = Modifier
                .commonBorder()
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
        ) {
            CommonBoxForColumn1()
            CommonBoxForColumn2()
            CommonBoxForColumn1()
            CommonBoxForColumn2()
            CommonBoxForColumn1()
            CommonBoxForColumn2()
            CommonBoxForColumn1()
            CommonBoxForColumn2()
            CommonBoxForColumn1()
            CommonBoxForColumn2()
            CommonBoxForColumn1()
            CommonBoxForColumn2()
            CommonBoxForColumn1()
            CommonBoxForColumn2()
            CommonBoxForColumn1()
            CommonBoxForColumn2()
            CommonBoxForColumn1()
            CommonBoxForColumn2()
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

@Composable
fun AlignAllChild(navHostController: NavHostController) {
    BuildTopBarWithScreen(screen = {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .wrapContentHeight(), verticalArrangement = Arrangement.spacedBy(space = 16.dp)
            ) {
                AlignAllChildText("1st child")
                AlignAllChildText("2nd child")
                AlignAllChildText("3rd child")
                AlignAllChildText("4th child")
                AlignAllChildText("16dp space applied to all child vertically")
            }
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

@Composable
fun AlignAllChildText(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(),
        textAlign = TextAlign.Center
    )
}