package com.esaudev.househealth.ui.sheets.expenses

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.househealth.domain.model.Expense
import com.esaudev.househealth.domain.model.ServiceType
import com.esaudev.househealth.ext.isNumeric
import com.esaudev.househealth.ui.util.UiTopLevelEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AddExpenseModalViewModel @Inject constructor() : ViewModel() {

    var uiState by mutableStateOf(AddExpenseUiState())
        private set

    private val _uiTopLevelEvent = Channel<UiTopLevelEvent>()
    val uiTopLevelEvent = _uiTopLevelEvent.receiveAsFlow()

    fun onEvent(addExpenseUiEvent: AddExpenseUiEvent) {
        viewModelScope.launch {
            when (addExpenseUiEvent) {
                is AddExpenseUiEvent.AddExpenseClick -> {
                    _uiTopLevelEvent.send(UiTopLevelEvent.Success)
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
                AddExpenseUiEvent.SheetDismissed -> {
                    uiState = uiState.copy(
                        amount = "",
                        comments = "",
                        serviceType = null
                    )
                }
            }
        }
    }
}

data class AddExpenseUiState(
    val amount: String = "",
    val comments: String = "",
    val serviceType: ServiceType? = null,
    val isLoading: Boolean = false
)

sealed class AddExpenseUiEvent {
    data class AmountChanged(val amount: String) : AddExpenseUiEvent()
    data class CommentsChanged(val comments: String) : AddExpenseUiEvent()
    data class AddExpenseClick(val expense: Expense) : AddExpenseUiEvent()
    data class ServiceTypeChanged(val serviceType: ServiceType) : AddExpenseUiEvent()
    data object SheetDismissed : AddExpenseUiEvent()
}