package com.parthdesai1208.compose.view.uicomponents.bottomsheet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.parthdesai1208.compose.view.MainActivity
import com.parthdesai1208.compose.view.theme.ComposeTheme

class BottomSheetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                PreviewFun(this)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finishAffinity()
    }

}

@Composable
fun PreviewFun(activity: Activity) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            activity.showAsBottomSheet {
                SimpleBottomSheetContent()
            }
        }) {
            Text(text = "BS using Extension", color = MaterialTheme.colors.onPrimary)
        }
    }
}