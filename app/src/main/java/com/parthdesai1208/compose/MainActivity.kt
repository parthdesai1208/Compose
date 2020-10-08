package com.parthdesai1208.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewGreeting()
        }
    }

    @Composable
    fun displayHelloName(name:String){
        Text (text = "Hello $name I am using compose")
    }

    @Preview
    @Composable
    fun PreviewGreeting() {
        displayHelloName("Parth")
    }
}