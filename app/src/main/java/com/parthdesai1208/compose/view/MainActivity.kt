package com.parthdesai1208.compose.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            /*Surface {
                TodoActivityScreen(todoViewModel)
            }*/
            Text("Hi there!", Modifier.baseLineToTop(32.dp).wrapContentWidth().wrapContentHeight(), color = MaterialTheme.colors.onSurface)
        }
    }
}