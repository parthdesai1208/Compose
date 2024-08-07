package com.parthdesai1208.compose.view.security

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
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.view.navigation.SecurityListingScreenPath

enum class SecurityListingEnumType(
    val buttonTitle: Int, val func: @Composable (NavHostController) -> Unit
) {
    BiometricAuthenticationScreen(
        R.string.biometricStrongAuthentication,
        { BiometricStrongAuthenticationScreen(it) }),
    PINPasswordAuthenticationScreen(
        R.string.pinPasswordAuthentication,
        { PINPasswordAuthenticationScreen(it) }),
}


@Composable
fun SecurityListingScreen(navHostController: NavHostController, modifier: Modifier = Modifier) {
    @Composable
    fun MyButton(
        title: SecurityListingEnumType
    ) {
        Button(
            onClick = { navHostController.navigate(SecurityListingScreenPath(title.buttonTitle)) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(stringResource(id = title.buttonTitle), textAlign = TextAlign.Center)
        }
    }

    BuildTopBarWithScreen(title = stringResource(id = R.string.security), screen = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            enumValues<SecurityListingEnumType>().forEach {
                MyButton(it)
            }
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

@Composable
fun ChildSecurityListingScreen(onClickButtonTitle: Int?, navController: NavHostController) {
    enumValues<SecurityListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke(
        navController
    )
}