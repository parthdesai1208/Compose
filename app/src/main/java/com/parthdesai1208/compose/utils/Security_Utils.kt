package com.parthdesai1208.compose.utils

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.AuthenticationCallback
import androidx.biometric.BiometricPrompt.PromptInfo
import com.parthdesai1208.compose.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class BiometricPromptManager(private val activity: AppCompatActivity) {

    private val resultChannel = Channel<BiometricResult>()
    val promptResults = resultChannel.receiveAsFlow()

    fun showBioMetricPrompt(
        title: String,
        description: String,
        subtitle: String = "",
        authenticator: Int,
        isConfirmationRequired: Boolean = true
    ) {
        val manager = BiometricManager.from(activity)


        val promptInfo = PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)
            .setAllowedAuthenticators(authenticator)
            .setConfirmationRequired(isConfirmationRequired)

        if (authenticator == BIOMETRIC_STRONG) { //required as negative Text not working with "DEVICE_CREDENTIAL" authenticator
            promptInfo.setNegativeButtonText(activity.getString(R.string.cancel))
        }

        when (manager.canAuthenticate(authenticator)) {
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                resultChannel.trySend(BiometricResult.HardwareUnavailable)
                return
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                resultChannel.trySend(BiometricResult.FeatureUnavailable)
                return
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                resultChannel.trySend(BiometricResult.AuthenticationNotSet)
                return
            }

            else -> Unit
        }
        val prompt = BiometricPrompt(
            activity, object : AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    resultChannel.trySend(BiometricResult.AuthenticationError(errString.toString()))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    resultChannel.trySend(BiometricResult.AuthenticationSuccess)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    resultChannel.trySend(BiometricResult.AuthenticationFailed)
                }
            }
        )
        prompt.authenticate(promptInfo.build())

    }

    sealed interface BiometricResult {
        data object HardwareUnavailable : BiometricResult
        data object FeatureUnavailable : BiometricResult
        data class AuthenticationError(val errorMessage: String) : BiometricResult
        data object AuthenticationFailed : BiometricResult
        data object AuthenticationNotSet : BiometricResult
        data object AuthenticationSuccess : BiometricResult
    }

}

@RequiresApi(Build.VERSION_CODES.R)
fun biometricEnrollIntent(authenticator: Int): Intent {
    return Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
        putExtra(
            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, authenticator
        )
    }
}