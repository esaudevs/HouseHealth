package com.esaudev.househealth.ui.screens.expenses

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.househealth.domain.model.Expense
import com.esaudev.househealth.domain.repository.ExpensesRepository
import com.esaudev.househealth.ui.screens.expenses.navigation.houseIdArg
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _queryState: MutableStateFlow<ExpensesQueryState> =
        MutableStateFlow(ExpensesQueryState())
    val queryState = _queryState.asStateFlow()

    private val _uiState: MutableStateFlow<ExpensesUiState> =
        MutableStateFlow(ExpensesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getExpensesByQuery() {
        viewModelScope.launch {
            queryState.collect {
                val expenses = expensesRepository.observeAllByHouseMonth(
                    houseId = savedStateHandle.get<String>(houseIdArg).orEmpty(),
                    date = it.date
                ).first()

                if (expenses.isEmpty()) {
                    _uiState.value = ExpensesUiState.Empty
                } else {
                    _uiState.value = ExpensesUiState.HouseWithExpenses(expenses = expenses)
                }
            }
        }
    }

    fun onNextMonth() {
        viewModelScope.launch {
            _queryState.update {
                it.copy(
                    date = it.date.plusMonths(1)
                )
            }
        }
    }

    fun onPreviousMonth() {
        viewModelScope.launch {
            _queryState.update {
                it.copy(
                    date = it.date.minusMonths(1)
                )
            }
        }
    }
}

data class ExpensesQueryState(
    val date: LocalDateTime = LocalDateTime.now()
)

sealed interface ExpensesUiState {
    data object Loading : ExpensesUiState

    data class HouseWithExpenses(
        val expenses: List<Expense>
    ) : ExpensesUiState

    data object Empty : ExpensesUiState
}