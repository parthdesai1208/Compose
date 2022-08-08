package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.parthdesai1208.compose.R

//region Box Listing Screen
enum class BoxListingEnumType(val buttonTitle: String, val func: @Composable () -> Unit) {
    WhatIsBox("What Is Box", { WhatIsBox() }),
    BoxAlignment("Box Alignment", { BoxAlignment() }),
    EditProfileImage("Edit Profile Image", { EditProfileImage() }),
}

object BoxDestinations {
    const val BOX_MAIN_SCREEN = "BOX_MAIN_SCREEN"
    const val BOX_SCREEN_ROUTE_PREFIX = "BOX_SCREEN_ROUTE_PREFIX"
    const val BOX_SCREEN_ROUTE_POSTFIX = "BOX_SCREEN_ROUTE_POSTFIX"
}

@Composable
fun BoxNavGraph(startDestination: String = BoxDestinations.BOX_MAIN_SCREEN) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = BoxDestinations.BOX_MAIN_SCREEN) {
            BoxListingScreen(navController = navController)
        }

        composable(
            route = "${BoxDestinations.BOX_SCREEN_ROUTE_PREFIX}/{${BoxDestinations.BOX_SCREEN_ROUTE_POSTFIX}}",
            arguments = listOf(navArgument(BoxDestinations.BOX_SCREEN_ROUTE_POSTFIX) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ChildBoxScreen(arguments.getString(BoxDestinations.BOX_SCREEN_ROUTE_POSTFIX))
        }
    }
}

@Composable
fun ChildBoxScreen(onClickButtonTitle: String?) {
    enumValues<BoxListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke()
}

@Composable
fun BoxListingScreen(navController: NavController) {
    @Composable
    fun MyButton(
        title: BoxListingEnumType
    ) {
        Button(
            onClick = { navController.navigate("${BoxDestinations.BOX_SCREEN_ROUTE_PREFIX}/${title.buttonTitle}") },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(title.buttonTitle, textAlign = TextAlign.Center)
        }
    }
    Surface {
        Column {
            Text(
                text = "Box Samples",
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif
            )
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
fun WhatIsBox() {
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
}

@Composable
fun BoxAlignment() {
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
}

@Composable
fun EditProfileImage() {
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
}