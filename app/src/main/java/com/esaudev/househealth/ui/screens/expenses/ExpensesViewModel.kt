package com.esaudev.househealth.ui.screens.expenses

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.househealth.domain.model.Expense
import com.esaudev.househealth.domain.model.ServiceType
import com.esaudev.househealth.domain.repository.ExpensesRepository
import com.esaudev.househealth.ui.screens.expenses.navigation.houseIdArg
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
            queryState.collectLatest {
                expensesRepository.observeAllByQuery(
                    houseId = savedStateHandle.get<String>(houseIdArg).orEmpty(),
                    query = it
                ).collectLatest { expenses ->
                    if (expenses.isEmpty()) {
                        _uiState.value = ExpensesUiState.HouseWithExpenses(expenses = expenses)
                    } else {
                        _uiState.value = ExpensesUiState.HouseWithExpenses(expenses = expenses)
                    }
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

    fun onServiceClick(serviceType: ServiceType) {
        viewModelScope.launch {
            _queryState.update {
                val updatedList = it.serviceTypes.map { it }.toMutableList()

                if (it.serviceTypes.contains(serviceType)) {
                    updatedList.remove(serviceType)
                } else {
                    updatedList.add(serviceType)
                }

                it.copy(
                    serviceTypes = updatedList
                )
            }
        }
    }

    fun onAllServiceClick() {
        viewModelScope.launch {
            _queryState.update {
                it.copy(
                    allServicesLocked = !it.allServicesLocked
                )
            }
        }
    }
}

data class ExpensesQueryState(
    val date: LocalDateTime = LocalDateTime.now(),
    val serviceTypes: List<ServiceType> = ServiceType.values().toList(),
    val allServicesLocked: Boolean = true
)

sealed interface ExpensesUiState {
    data object Loading : ExpensesUiState

    data class HouseWithExpenses(
        val expenses: List<Expense>
    ) : ExpensesUiState

    data object Empty : ExpensesUiState
}