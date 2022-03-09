package com.parthdesai1208.compose.view

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.parthdesai1208.compose.view.theme.ComposeTheme

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        //create reference for button & text
        val (button, text) = createRefs()

        Button(onClick = { /*TODO*/ },
            //constrainAs takes 2 params (1) ConstrainedLayoutReference view (2) constrainBlock
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, 16.dp) //for single link
                linkTo(parent.start,parent.end,8.dp,8.dp) //for horizontal link
            }) {
            Text(text = "Button Text here")
        }

        Text(text = "Text"
            , modifier = Modifier.constrainAs(text){
                linkTo(button.bottom,parent.bottom,10.dp,8.dp, bias = 0f) //for vertical link
                linkTo(button.start,button.end) //for single link
            })
    }
}

@Preview
@Composable
fun ConstraintLayoutContentPreview() {
    ComposeTheme {
        ConstraintLayoutContent()
    }
}
