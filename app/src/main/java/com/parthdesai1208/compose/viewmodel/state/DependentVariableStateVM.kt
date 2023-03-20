package com.parthdesai1208.compose.viewmodel.state

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DependentVariableStateVM : ViewModel() {
    var age by mutableStateOf(18)
    var creditScore by mutableStateOf(0)


    fun updateCreditScore(creditScore: Int) {
        this.creditScore = creditScore
    }

    fun updateAge(age: Int) {
        this.age = age
    }

    val isEligible = derivedStateOf {
        creditScore > 400 && age > 18 && age < 70
    }

    private var ageFlow = MutableStateFlow(18)
    val ageFlowWrapper: StateFlow<Int> = ageFlow

    private var creditScoreFlow = MutableStateFlow(0)
    val creditScoreWrapper: StateFlow<Int> = creditScoreFlow

    fun updateCreditScoreFlow(creditScore: Int) {
        viewModelScope.launch {
            creditScoreFlow.emit(creditScore)
        }
    }

    fun updateAgeFlow(age: Int) {
        viewModelScope.launch {
            ageFlow.emit(age)
        }
    }

    val isEligibleFlow = combine(flow = ageFlow, flow2 = creditScoreFlow) { age, creditScore ->
        creditScore > 400 && age > 18 && age < 70
    }

    private var creditScoreFlowSingle = MutableStateFlow(0)
    val creditScoreWrapperSingle: StateFlow<Int> = creditScoreFlowSingle

    fun updateCreditScoreSingleFlow(creditScore: Int) {
        viewModelScope.launch {
            creditScoreFlowSingle.emit(creditScore)
        }
    }

    val isEligibleFlowSingle = creditScoreFlowSingle.map {
        it > 400
    }
}