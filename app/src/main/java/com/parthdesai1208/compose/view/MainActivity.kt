package com.parthdesai1208.compose.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.viewmodel.TodoViewModel

class MainActivity : AppCompatActivity() {

    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewUI()
        }
    }

    @Preview(name= "light" ,showSystemUi = true)
    @Preview(name = "Dark", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun PreviewUI() {
        ComposeTheme {
//            TextInCenter("Parth")
//            CollapsableRecyclerView()
            Surface {
                TodoActivityScreen(todoViewModel)
            }
        }
    }
}