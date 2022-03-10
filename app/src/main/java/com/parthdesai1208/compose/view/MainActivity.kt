package com.parthdesai1208.compose.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
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

    @Preview(name = "light", showSystemUi = true)
    @Preview(name = "Dark", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Preview(name = "landscape", showSystemUi = true, device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
    @Composable
    fun PreviewUI() {
        ComposeTheme {
            //region text
//            TextInCenter("Parth")
            //endregion
            //region recyclerview
//            CollapsableRecyclerView()
            //endregion
            //region Learn state
            /*Surface {
                TodoActivityScreen(todoViewModel)
            }*/
            //endregion
            //region custom modifier
//            Text("Hi there!", Modifier.baseLineToTop(32.dp).wrapContentWidth().wrapContentHeight(), color = MaterialTheme.colors.onSurface)
            //endregion
            //region Custom recyclerview
            /*Column(verticalArrangement = Arrangement.Top) {
                StaggeredGridFun(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                )
            }*/
            //endregion
            //region Constraint Layout
            //ConstraintLayoutContent()
            DecoupledConstraintLayout()
            //endregion
        }
    }
}