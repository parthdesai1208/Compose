package com.parthdesai1208.compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.parthdesai1208.compose.view.theme.ComposeTheme

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {

        val (button1, text, button2) = createRefs()
        createHorizontalChain(button1, button2, chainStyle = ChainStyle.Spread)
        Button(onClick = {  },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, 16.dp) //for single link
                linkTo(parent.start,button2.start)
            }) {
            Text(text = "Button1")
        }

        Button(onClick = {  },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(button1.top) //for single link
                bottom.linkTo(button1.bottom)
                linkTo(button1.end,parent.end)
            }) {
            Text(text = "Button2")
        }

        Text(text = "Text",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.constrainAs(text) {
                linkTo(button1.bottom, parent.bottom, 10.dp, 8.dp, bias = 0f) //for vertical link
                linkTo(button1.end, button1.end) //for single link
            })
    }
}

@Preview(showSystemUi = true)
@Composable
fun ConstraintLayoutContentPreview() {
    ComposeTheme {
        //ConstraintLayoutContent()
        DecoupledConstraintLayout()
    }
}

@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp) // Portrait constraints
        } else {
            decoupledConstraints(margin = 64.dp) // Landscape constraints
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = { /* Do something */ },
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId("text"), color = MaterialTheme.colors.onSurface)
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin= margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}
