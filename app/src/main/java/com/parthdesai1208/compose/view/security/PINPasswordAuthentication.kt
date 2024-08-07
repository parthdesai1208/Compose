package com.parthdesai1208.compose.view.security

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BiometricPromptManager
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.utils.biometricEnrollIntent

@Composable
fun PINPasswordAuthenticationScreen(
    navHostController: NavHostController, modifier: Modifier = Modifier
) {
    val activity = LocalContext.current as AppCompatActivity

    val promptManager = remember {
        BiometricPromptManager(activity)
    }
    val authenticator = remember { BIOMETRIC_WEAK or DEVICE_CREDENTIAL }
    val biometricResult by promptManager.promptResults.collectAsState(initial = null)
    var isSetHereButtonVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = biometricResult) {
        if (biometricResult is BiometricPromptManager.BiometricResult.AuthenticationNotSet) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                isSetHereButtonVisible = true
            }
        }
    }
    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            if (it.resultCode in listOf(RESULT_OK, 2)) {
                isSetHereButtonVisible = false
            } else if (it.resultCode == RESULT_CANCELED) {
                isSetHereButtonVisible = true
            }
        })

    BuildTopBarWithScreen(title = stringResource(id = R.string.pinPasswordAuthentication),
        screen = {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    enabled = !isSetHereButtonVisible,
                    onClick = {
                        promptManager.showBioMetricPrompt(
                            title = "Title",
                            subtitle = "Subtitle",
                            description = "Description",
                            authenticator = authenticator,
                        )
                    }) {
                    Text("Authenticate")
                }
                Spacer(modifier = Modifier.height(16.dp))
                biometricResult?.let { result ->
                    Text(
                        text = when (result) {
                            is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                                result.errorMessage
                            }

                            BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                                "Authentication Failed"
                            }

                            BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                                "Authentication not set"
                            }

                            BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                                "Authentication Success"
                            }

                            BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                                "Feature Unavailable"
                            }

                            BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                                "Hardware Unavailable"
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (isSetHereButtonVisible && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Button(onClick = {
                        enrollLauncher.launch(biometricEnrollIntent(authenticator))
                    }) {
                        Text(text = "Set Here")
                    }
                }
            }
        },
        onBackIconClick = {
            navHostController.popBackStack()
        })

}