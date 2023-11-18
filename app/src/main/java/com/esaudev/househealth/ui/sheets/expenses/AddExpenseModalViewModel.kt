package com.esaudev.househealth.ui.sheets.expenses

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.househealth.domain.model.ServiceType
import com.esaudev.househealth.domain.repository.ExpensesRepository
import com.esaudev.househealth.domain.usecase.ValidateExpenseStateUseCase
import com.esaudev.househealth.ext.isNumeric
import com.esaudev.househealth.ui.screens.expenses.navigation.houseIdArg
import com.esaudev.househealth.ui.util.UiTopLevelEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@HiltViewModel
class AddExpenseModalViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val expensesRepository: ExpensesRepository,
    private val validateExpenseStateUseCase: ValidateExpenseStateUseCase
) : ViewModel() {

    var uiState by mutableStateOf(AddExpenseUiState())
        private set

    private val _uiTopLevelEvent = Channel<UiTopLevelEvent>()
    val uiTopLevelEvent = _uiTopLevelEvent.receiveAsFlow()

    fun initializeExpense() {
        val houseId = savedStateHandle.get<String>(houseIdArg)
        uiState = uiState.copy(
            houseId = houseId
        )
    }

    fun onEvent(addExpenseUiEvent: AddExpenseUiEvent) {
        viewModelScope.launch {
            when (addExpenseUiEvent) {
                is AddExpenseUiEvent.AddExpenseClick -> {
                    val validatedExpense = validateExpenseStateUseCase(uiState)
                    validatedExpense
                        .onSuccess {
                            expensesRepository.add(it)
                            _uiTopLevelEvent.send(UiTopLevelEvent.Success)
                        }
                        .onFailure {
                        }
                }
                is AddExpenseUiEvent.AmountChanged -> {
                    if (addExpenseUiEvent.amount.isNumeric() || addExpenseUiEvent.amount.isEmpty()) {
                        uiState = uiState.copy(amount = addExpenseUiEvent.amount)
                    }
                }
                is AddExpenseUiEvent.CommentsChanged -> {
                    uiState = uiState.copy(comments = addExpenseUiEvent.comments)
                }
                is AddExpenseUiEvent.ServiceTypeChanged -> {
                    uiState = uiState.copy(serviceType = addExpenseUiEvent.serviceType)
                }
                is AddExpenseUiEvent.DateChanged -> {
                    uiState = uiState.copy(date = addExpenseUiEvent.date)
                }
                AddExpenseUiEvent.SheetDismissed -> {
                    uiState = uiState.copy(
                        amount = "",
                        comments = "",
                        serviceType = null,
                        date = LocalDateTime.now()
                    )
                }
            }
        }
    }
}

data class AddExpenseUiState(
    val amount: String = "",
    val comments: String = "",
    val date: LocalDateTime = LocalDateTime.now(),
    val serviceType: ServiceType? = null,
    val isLoading: Boolean = false,
    val houseId: String? = null
)

sealed class AddExpenseUiEvent {
    data class AmountChanged(val amount: String) : AddExpenseUiEvent()
    data class CommentsChanged(val comments: String) : AddExpenseUiEvent()
    data class DateChanged(val date: LocalDateTime): AddExpenseUiEvent()
    data object AddExpenseClick : AddExpenseUiEvent()
    data class ServiceTypeChanged(val serviceType: ServiceType) : AddExpenseUiEvent()
    data object SheetDismissed : AddExpenseUiEvent()
}