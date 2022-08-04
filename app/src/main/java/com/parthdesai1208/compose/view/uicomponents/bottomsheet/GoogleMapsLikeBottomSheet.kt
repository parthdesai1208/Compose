@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.parthdesai1208.compose.view.uicomponents.bottomsheet

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.view.theme.blueDirectionColor
import com.parthdesai1208.compose.view.theme.restaurant_gmap
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
//@Preview(showSystemUi = true)
@Composable
fun GoogleMapsLikeBottomSheet() {
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            ComposeTheme {
                MapsLikeContent()
            }
        }, scaffoldState = scaffoldState,
        sheetPeekHeight = 200.dp
    ) {
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
                Text(text = "Google maps like BottomSheet", color = MaterialTheme.colors.onPrimary)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MapsLikeContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {
        val commonPadding = Modifier.padding(start = 16.dp, end = 16.dp)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .size(width = 50.dp, height = 5.dp)
                .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Pune",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h5,
            modifier = commonPadding.padding(top = 16.dp)
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
        DividerLine()
        CategoryList()
        DividerLine()
        PlaceDescriptionText()
        DividerLine()
        StateText()
        DividerLine()
        MeasureDistance()
        PhotosImage()
        DividerLine()
        IconAndTextInRow(icon = Icons.Default.Edit, text = "Suggest an edit on Pune")
        Spacer(modifier = Modifier.height(12.dp))
        IconAndTextInRow(icon = Icons.Default.Map, text = "Add a missing place")
        Spacer(modifier = Modifier.height(12.dp))
        IconAndTextInRow(icon = Icons.Default.Place, text = "Add your business to Maps for free")
        DividerLine()
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = commonPadding.padding(bottom = 8.dp)
        ) {
            ChipDirection(
                image = Icons.Default.Directions, text = "",
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
        shape = if (text.isNotBlank()) RoundedCornerShape(32.dp) else CircleShape,
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
            if (text.isNotBlank()) {
                Text(text = text, color = contentColor)
            }
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
        horizontalArrangement = Arrangement.spacedBy(20.dp),
//        verticalArrangement = Arrangement.SpaceBetween,
        content = {
            categoryList.forEachIndexed { _, categoryData ->
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

@Composable
fun DividerLine() {
    Spacer(modifier = Modifier.height(8.dp))
    Box(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color = Color.LightGray)
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun PlaceDescriptionText() {
    Text(
        text = stringResource(id = R.string.GoogleMapsPlaceDescription),
        color = MaterialTheme.colors.onSurface.copy(alpha = .5f),
        modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp)
    )
}

@Composable
fun IconAndTextInRow(icon: ImageVector, text: String) {
    Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        Icon(
            imageVector = icon, contentDescription = null,
            tint = restaurant_gmap
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = text, color = MaterialTheme.colors.onSurface.copy(alpha = .5f))
    }
}

@Composable
fun StateText() {
    IconAndTextInRow(icon = Icons.Default.Map, text = "Maharashtra")
}

@Composable
fun MeasureDistance() {
    IconAndTextInRow(icon = Icons.Default.Calculate, text = "Measure distance")
}

@Composable
fun PhotosImage() {
    val density = LocalDensity.current
    val width = remember { mutableStateOf(0f) }
    val height = remember { mutableStateOf(0f) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(start = 16.dp, top = 8.dp, end = 16.dp)
            .clip(shape = RoundedCornerShape(8.dp)), elevation = 4.dp
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.gi8),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        width.value = (it.size.width / density.density.toInt()).toFloat()
                        height.value = (it.size.height / density.density.toInt()).toFloat()
                    }
            )
            Column(
                modifier = Modifier
                    .size(width = width.value.dp, height = height.value.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black),
                            startY = 0f,
                            endY = 500f
                        )
                    )
            ) {}
            Text(
                text = "Photos",
                color = Color.White,
                modifier = Modifier
                    .padding(start = 12.dp, bottom = 12.dp)
                    .align(Alignment.BottomStart)
            )
        }
    }
}