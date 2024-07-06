package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Surface
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.utils.AddBackIconToScreen
import com.parthdesai1208.compose.view.theme.ComposeTheme

@Composable
fun ConstraintLayoutContent(navHostController: NavHostController) {
    AddBackIconToScreen(screen = {
        Surface {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {

                val (button1, text, button2) = createRefs()
                createHorizontalChain(button1, button2, chainStyle = ChainStyle.Spread)
                Button(onClick = { },
                    modifier = Modifier.constrainAs(button1) {
                        top.linkTo(parent.top, 16.dp) //for single link
                        linkTo(parent.start, button2.start)
                    }) {
                    Text(text = "Button1")
                }

                Button(onClick = { },
                    modifier = Modifier.constrainAs(button2) {
                        top.linkTo(button1.top) //for single link
                        bottom.linkTo(button1.bottom)
                        linkTo(button1.end, parent.end)
                    }) {
                    Text(text = "Button2")
                }

                Text(text = "Text",
                    modifier = Modifier.constrainAs(text) {
                        linkTo(
                            button1.bottom,
                            parent.bottom,
                            10.dp,
                            8.dp,
                            bias = 0f
                        ) //for vertical link
                        linkTo(button1.end, button1.end) //for single link
                    })
            }
        }
    }) {
        navHostController.popBackStack()
    }
}

@Preview(showSystemUi = true)
@Composable
fun ConstraintLayoutContentPreview() {
    ComposeTheme {
        //ConstraintLayoutContent()
        DecoupledConstraintLayout(rememberNavController())
    }
}

@Composable
fun DecoupledConstraintLayout(navHostController: NavHostController) {
    AddBackIconToScreen(screen = {
        Surface {
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

                    Text("Text", Modifier.layoutId("text"))
                }
            }
        }
    }) {
        navHostController.popBackStack()
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}

@Composable
fun ClockByConstraintLayout(navHostController: NavHostController) {
    AddBackIconToScreen(screen = {
        Surface {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (middleDot, one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve) = createRefs()
                Text(text = "Clock", modifier = Modifier.constrainAs(middleDot) {
                    linkTo(start = parent.start, end = parent.end)
                    linkTo(top = parent.top, bottom = parent.bottom)
                })
                Text(text = "1", modifier = Modifier.constrainAs(one) {
                    circular(other = middleDot, angle = 30f, distance = 90.dp)
                })
                Text(text = "2", modifier = Modifier.constrainAs(two) {
                    circular(other = middleDot, angle = 60f, distance = 90.dp)
                })
                Text(text = "3", modifier = Modifier.constrainAs(three) {
                    circular(other = middleDot, angle = 90f, distance = 90.dp)
                })
                Text(text = "4", modifier = Modifier.constrainAs(four) {
                    circular(other = middleDot, angle = 120f, distance = 90.dp)
                })
                Text(text = "5", modifier = Modifier.constrainAs(five) {
                    circular(other = middleDot, angle = 150f, distance = 90.dp)
                })
                Text(text = "6", modifier = Modifier.constrainAs(six) {
                    circular(other = middleDot, angle = 180f, distance = 90.dp)
                })
                Text(text = "7", modifier = Modifier.constrainAs(seven) {
                    circular(other = middleDot, angle = 210f, distance = 90.dp)
                })
                Text(text = "8", modifier = Modifier.constrainAs(eight) {
                    circular(other = middleDot, angle = 240f, distance = 90.dp)
                })
                Text(text = "9", modifier = Modifier.constrainAs(nine) {
                    circular(other = middleDot, angle = 270f, distance = 90.dp)
                })
                Text(text = "10", modifier = Modifier.constrainAs(ten) {
                    circular(other = middleDot, angle = 300f, distance = 90.dp)
                })
                Text(text = "11", modifier = Modifier.constrainAs(eleven) {
                    circular(other = middleDot, angle = 330f, distance = 90.dp)
                })
                Text(text = "12", modifier = Modifier.constrainAs(twelve) {
                    circular(other = middleDot, angle = 0f, distance = 90.dp)
                })
            }
        }
    }) {
        navHostController.popBackStack()
    }
}