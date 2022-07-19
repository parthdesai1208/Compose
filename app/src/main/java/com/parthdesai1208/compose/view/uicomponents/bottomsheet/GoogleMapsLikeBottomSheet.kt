@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.parthdesai1208.compose.view.uicomponents.bottomsheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.model.GoogleMapsImageList
import com.parthdesai1208.compose.model.GoogleMapsImageModel
import com.parthdesai1208.compose.model.categoryList
import com.parthdesai1208.compose.view.animation.getScreenWidth
import com.parthdesai1208.compose.view.theme.blueDirectionColor
import com.parthdesai1208.compose.view.uicomponents.DoubleSizedLeftRowGridCell
import com.parthdesai1208.compose.view.uicomponents.ItemViewFirstItemTakeWholeSpace
import com.parthdesai1208.compose.view.uicomponents.commonBorder
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
//@Preview(showSystemUi = true)
@Composable
fun GoogleMapsLikeBottomSheet() {
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(sheetContent = { MapsLikeContent() }, scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .fillMaxHeight()
                .wrapContentHeight(align = Alignment.CenterVertically)
        ) {
            Button(onClick = {
                scope.launch {
                    if (sheetState.isCollapsed) {
                        sheetState.expand()
                    } else {
                        sheetState.collapse()
                    }
                }
            }) {
                Text(text = "Google maps", color = MaterialTheme.colors.onPrimary)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MapsLikeContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        val commonPadding = Modifier.padding(start = 16.dp)
        Text(
            text = "Pune",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h5,
            modifier = commonPadding
        )

        Text(
            text = "पुणे",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h6,
            modifier = commonPadding.alpha(0.5f)
        )

        Text(
            text = "Maharashtra",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h6,
            modifier = commonPadding.alpha(0.5f)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = commonPadding.padding(top = 8.dp)
        ) {
            ChipDirection(
                image = Icons.Default.Directions, text = "Directions",
                bgColor = blueDirectionColor, contentColor = MaterialTheme.colors.surface,
                borderColor = blueDirectionColor
            )
            ChipDirection(
                image = Icons.Default.BookmarkBorder,
                text = "Save",
                bgColor = MaterialTheme.colors.surface,
                contentColor = blueDirectionColor,
                borderColor = Color.Gray
            )
            ChipDirection(
                image = Icons.Default.Share,
                text = "Share",
                bgColor = MaterialTheme.colors.surface,
                contentColor = blueDirectionColor,
                borderColor = Color.Gray
            )
        }

        HorizontalStaggeredImageList()

        CategoryList()
    }
}

@Composable
fun ChipDirection(
    image: ImageVector,
    text: String,
    bgColor: Color,
    contentColor: Color,
    borderColor: Color
) {
    Card(
        border = BorderStroke(color = borderColor, width = Dp.Hairline),
        shape = RoundedCornerShape(32.dp),
        backgroundColor = bgColor
    ) {
        Row(
            modifier = Modifier.padding(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(
                8.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            Icon(
                imageVector = image,
                contentDescription = null,
                tint = contentColor
            )
            Text(text = text, color = contentColor)
        }
    }
}

val DoubleSizedRowGridCell = object : GridCells {
    override fun Density.calculateCrossAxisCellSizes(availableSize: Int, spacing: Int): List<Int> {
        val firstColumn = (availableSize - spacing) * 2 / 4
        val secondColumn = availableSize - spacing - firstColumn
        return listOf(firstColumn, secondColumn)
    }
}

@Composable
fun HorizontalStaggeredImageList() {
    LazyHorizontalGrid(contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        modifier = Modifier
            .height(300.dp)
            .padding(top = 8.dp)
            .fillMaxWidth(), rows = DoubleSizedRowGridCell,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            GoogleMapsImageList.forEachIndexed { index, googleMapsImageModel ->
                if (index % 3 == 0) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        ItemHorizontalStaggeredImageList(item = googleMapsImageModel)
                    }
                } else { //for remaining items in list
                    item(span = { GridItemSpan(1) }) {
                        ItemHorizontalStaggeredImageList(item = googleMapsImageModel)
                    }
                }
            }
        })
}

class FakeDataProvider : PreviewParameterProvider<GoogleMapsImageModel> {
    override val values: Sequence<GoogleMapsImageModel>
        get() = sequenceOf(
            GoogleMapsImageModel(
                R.drawable.gi1,
                false,
                false,
                Icons.Default.Image,
                "See All"
            )
        )
}

@PreviewParameter(FakeDataProvider::class)
@Composable
fun ItemHorizontalStaggeredImageList(item: GoogleMapsImageModel) {
    ConstraintLayout(
        modifier = Modifier
            .width(getScreenWidth().dp - 30.dp)
    ) {
        val (image, icon, downText, centerText) = createRefs()
        Image(
            painter = painterResource(id = item.drawable), contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(8.dp))
                .constrainAs(image) {
                    linkTo(start = parent.start, end = parent.end)
                    linkTo(top = parent.top, bottom = parent.bottom)
                }
                .drawWithContent {
                    if (item.isIconVisible && item.isTextVisible) {
                        drawContent()
                        drawRect(color = Color.Black, alpha = .6f)
                    } else {
                        drawContent()
                    }
                }
        )
        if (item.isIconVisible) {
            Icon(imageVector = item.icon, contentDescription = null, tint = Color.White,
                modifier = Modifier.constrainAs(icon) {
                    linkTo(start = parent.start, end = parent.end)
                    linkTo(top = parent.top, bottom = parent.bottom, bottomMargin = 16.dp)
                })
        }
        if (item.isIconVisible && item.isTextVisible) {
            Text(
                text = item.text,
                color = Color.White,
                modifier = Modifier
                    .constrainAs(centerText) {
                        linkTo(start = parent.start, end = parent.end)
                        top.linkTo(anchor = icon.bottom, margin = 8.dp)
                    })
        }
        if (!item.isIconVisible && item.isTextVisible) {
            Text(text = item.text, color = Color.White,
                modifier = Modifier
                    .background(
                        color = Color.Black.copy(alpha = .6f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(all = 5.dp)
                    .constrainAs(downText) {
                        start.linkTo(parent.start, margin = 16.dp)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                    })
        }
    }
}

@Composable
fun CategoryList() {
    LazyHorizontalGrid(rows = GridCells.Fixed(2),
        modifier = Modifier
            .padding(start = 16.dp, top = 18.dp, end = 16.dp)
            .fillMaxWidth()
            .height(200.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            categoryList.forEachIndexed { index, categoryData ->
                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .background(color = categoryData.tintColor)
                                .size(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = categoryData.icon,
                                contentDescription = null,
                                tint = MaterialTheme.colors.surface
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = categoryData.categoryName,
                            color = MaterialTheme.colors.onSurface,
                        )

                    }
                }
            }
        })
}