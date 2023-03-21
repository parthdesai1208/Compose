package com.parthdesai1208.compose.view.state

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parthdesai1208.compose.utils.LogCompositions
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.view.theme.Green300
import com.parthdesai1208.compose.view.theme.GreyDark
import com.parthdesai1208.compose.viewmodel.state.DependentVariableStateVM

@Composable
fun DependentVariableState(viewModel: DependentVariableStateVM) {
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

            WrappedColumn(content = {
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
            }, verticalArrangement = verticalArrangement, horizontalAlignment = horizontalAlignment)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Approach-2: Updating state in kotlin")
            Spacer(modifier = Modifier.height(8.dp))
            WrappedColumn(content = {
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
            }, verticalArrangement = verticalArrangement, horizontalAlignment = horizontalAlignment)

            Spacer(modifier = Modifier.height(48.dp))
            Text(text = "Update variable state based on single variable state change")
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "here we are change color of button based on creditScore\nvalidation:if credit score is greater than 400 then the customer is considered eligible")
            Spacer(modifier = Modifier.height(8.dp))
            WrappedColumn(content = {
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
            }, verticalArrangement = verticalArrangement, horizontalAlignment = horizontalAlignment)
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
        DependentVariableState(androidx.lifecycle.viewmodel.compose.viewModel())
    }
}