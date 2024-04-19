package com.parthdesai1208.compose.view.state

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.LogCompositions
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.view.theme.Green300
import com.parthdesai1208.compose.view.theme.GreyDark
import com.parthdesai1208.compose.viewmodel.state.DependentVariableStateVM

@Composable
fun DependentVariableState(
    navHostController: NavHostController,
    viewModel: DependentVariableStateVM
) {
    val isEligible by remember { viewModel.isEligible }
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val isEligibleFlow by viewModel.isEligibleFlow.collectAsStateWithLifecycle(
        initialValue = false,
        lifecycle = lifecycle
    )
    val ageFlowWrapper by viewModel.ageFlowWrapper.collectAsStateWithLifecycle()
    val creditScoreWrapper by viewModel.creditScoreWrapper.collectAsStateWithLifecycle()

    val isEligibleFlowSingle by viewModel.isEligibleFlowSingle.collectAsStateWithLifecycle(
        initialValue = false,
        lifecycle = lifecycle
    )
    val creditScoreWrapperSingle by viewModel.creditScoreWrapperSingle.collectAsStateWithLifecycle()

    val verticalArrangement = Arrangement.Center
    val horizontalAlignment = Alignment.CenterHorizontally

    Surface {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        navHostController.popBackStack()
                    }, imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.dependentVariableState),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp),
                verticalArrangement = verticalArrangement,
                horizontalAlignment = horizontalAlignment
            ) {
                LogCompositions("DebugRecomposition", "root Column() scope started")
                Text(text = "Update variable state based on multiple variable state change")
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "here we are change color of button based on age & creditScore\nvalidation:if age is greater than 18 and less than 70 and credit score is greater than 400 then the customer is considered eligible")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Approach-1: Updating state in compose")
                Spacer(modifier = Modifier.height(8.dp))

                WrappedColumn(
                    content = {
                        LogCompositions("DebugRecomposition", "root WrappedColumn1() scope started")
                        Text(text = "Age:${viewModel.age} & CreditScore:${viewModel.creditScore}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                viewModel.updateCreditScoreAge(
                                    creditScore = (0..900).random(),
                                    age = (0..100).random()
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (isEligible) Green300 else GreyDark
                            )
                        ) {
                            LogCompositions("DebugRecomposition", "root Button() scope started")
                            Text(text = "Submit")
                        }
                    },
                    verticalArrangement = verticalArrangement,
                    horizontalAlignment = horizontalAlignment
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Approach-2: Updating state in kotlin")
                Spacer(modifier = Modifier.height(8.dp))
                WrappedColumn(
                    content = {
                        Text(text = "Age:${ageFlowWrapper} & CreditScore:${creditScoreWrapper}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                viewModel.updateCreditScoreFlow((0..900).random())
                                viewModel.updateAgeFlow((0..100).random())
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (isEligibleFlow) Green300 else GreyDark
                            )
                        ) {
                            Text(text = "Submit")
                        }
                    },
                    verticalArrangement = verticalArrangement,
                    horizontalAlignment = horizontalAlignment
                )

                Spacer(modifier = Modifier.height(48.dp))
                Text(text = "Update variable state based on single variable state change")
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "here we are change color of button based on creditScore\nvalidation:if credit score is greater than 400 then the customer is considered eligible")
                Spacer(modifier = Modifier.height(8.dp))
                WrappedColumn(
                    content = {
                        Text(text = "CreditScore:${creditScoreWrapperSingle}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                viewModel.updateCreditScoreSingleFlow((0..900).random())
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (isEligibleFlowSingle) Green300 else GreyDark
                            )
                        ) {
                            Text(text = "Submit")
                        }
                    },
                    verticalArrangement = verticalArrangement,
                    horizontalAlignment = horizontalAlignment
                )
            }
        }
    }
}

@Composable
private fun WrappedColumn(
    content: @Composable ColumnScope.() -> Unit,
    verticalArrangement: Arrangement.Vertical,
    horizontalAlignment: Alignment.Horizontal,
) {
    Column(
        content = content, verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    )
}

@Phone
@Composable
fun DependentVariableStatePreview() {
    ComposeTheme {
        DependentVariableState(
            rememberNavController(),
            androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }
}