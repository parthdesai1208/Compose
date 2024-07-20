package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.view.navigation.BoxListingScreenPath

//region Box Listing Screen
enum class BoxListingEnumType(
    val buttonTitle: Int,
    val func: @Composable (NavHostController) -> Unit
) {
    WhatIsBox(R.string.what_is_box, { WhatIsBox(it) }),
    BoxAlignment(R.string.box_alignment, { BoxAlignment(it) }),
    EditProfileImage(R.string.edit_profile_image, { EditProfileImage(it) }),
}

@Composable
fun ChildBoxScreen(onClickButtonTitle: Int?, navHostController: NavHostController) {
    enumValues<BoxListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke(
        navHostController
    )
}

@Composable
fun BoxListingScreen(navController: NavController) {
    @Composable
    fun MyButton(
        title: BoxListingEnumType
    ) {
        val context = LocalContext.current
        Button(
            onClick = { navController.navigate(BoxListingScreenPath(title.buttonTitle)) },
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
                    text = "Box Samples",
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
                enumValues<BoxListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}
//endregion

@Composable
fun WhatIsBox(navHostController: NavHostController) {
    BuildTopBarWithScreen(screen = {
        Box(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .size(width = 150.dp, height = 200.dp)
                    .border(width = 5.dp, color = Color.Red, shape = RoundedCornerShape(16.dp))
                    .background(color = Color.Transparent)
                    .align(alignment = Alignment.CenterStart)
            )

            Box(
                modifier = Modifier
                    .size(width = 190.dp, height = 240.dp)
                    .border(width = 5.dp, color = Color.Yellow, shape = RoundedCornerShape(16.dp))
                    .background(color = Color.Transparent)
                    .align(alignment = Alignment.Center)
            )

            Box(
                modifier = Modifier
                    .size(width = 150.dp, height = 280.dp)
                    .border(width = 5.dp, color = Color.Green, shape = RoundedCornerShape(16.dp))
                    .background(color = Color.Transparent)
                    .align(alignment = Alignment.CenterEnd)
            )
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

@Composable
fun BoxAlignment(navHostController: NavHostController) {
    BuildTopBarWithScreen(screen = {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Top start",
                    modifier = Modifier.align(alignment = Alignment.TopStart)
                )
                Text(
                    text = "Top center hide XXXXX below Box",
                    modifier = Modifier.align(alignment = Alignment.TopCenter)
                )
                Text(
                    text = "Top End",
                    modifier = Modifier.align(alignment = Alignment.TopEnd)
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(50.dp)
                        .background(color = Color.LightGray)
                        .align(alignment = Alignment.Center)
                )
                Text(
                    text = "Center start",
                    modifier = Modifier.align(alignment = Alignment.CenterStart)
                )
                Text(
                    text = "Center text",
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
                Text(
                    text = "Center end",
                    modifier = Modifier.align(alignment = Alignment.CenterEnd)
                )

                Text(
                    text = "Bottom Start",
                    modifier = Modifier.align(alignment = Alignment.BottomStart)
                )
                Text(
                    text = "Bottom center",
                    modifier = Modifier.align(alignment = Alignment.BottomCenter)
                )
                Text(
                    text = "Bottom End",
                    modifier = Modifier.align(alignment = Alignment.BottomEnd)
                )

                FloatingActionButton(
                    onClick = { },
                    modifier = Modifier.align(alignment = Alignment.BottomEnd)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add icon")
                }
            }
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

@Composable
fun EditProfileImage(navHostController: NavHostController) {
    BuildTopBarWithScreen(screen = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .fillMaxHeight()
                .wrapContentHeight(align = Alignment.CenterVertically)
        ) {

            Image(
                painter = painterResource(id = R.drawable.hl1), contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape), contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(color = Color.Magenta.copy(alpha = .4f))
                    .padding(7.dp)
                    .align(alignment = Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
            }

        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}