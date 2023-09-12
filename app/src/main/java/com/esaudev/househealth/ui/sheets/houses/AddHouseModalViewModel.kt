package com.esaudev.househealth.ui.sheets.houses

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.househealth.domain.model.House
import com.esaudev.househealth.domain.usecase.AddHouseUseCase
import com.esaudev.househealth.ui.util.UiTopLevelEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AddHouseModalViewModel @Inject constructor(
    private val addHouseUseCase: AddHouseUseCase
) : ViewModel() {

    var uiState by mutableStateOf(AddHouseUiState())
        private set

    private val _uiTopLevelEvent = Channel<UiTopLevelEvent>()
    val uiTopLevelEvent = _uiTopLevelEvent.receiveAsFlow()

    fun onEvent(addHouseUiEvent: AddHouseUiEvent) {
        viewModelScope.launch {
            when (addHouseUiEvent) {
                is AddHouseUiEvent.AddHouseClick -> {
                    if (addHouseUiEvent.house.name.isEmpty()) {
                        uiState = uiState.copy(
                            hasError = true
                        )
                    } else {
                        uiState = uiState.copy(
                            name = ""
                        )
                        addHouseUseCase(addHouseUiEvent.house)
                        _uiTopLevelEvent.send(UiTopLevelEvent.Success)
                    }
                }

                is AddHouseUiEvent.NameChanged -> {
                    uiState = uiState.copy(
                        name = addHouseUiEvent.name,
                        hasError = false
                    )
                }

                is AddHouseUiEvent.SheetDismissed -> {
                    uiState = uiState.copy(
                        name = "",
                        hasError = false
                    )
                }
            }
        }
    }
}

data class AddHouseUiState(
    val name: String = "",
    val isLoading: Boolean = false,
    val hasError: Boolean = false
)

sealed class AddHouseUiEvent {
    data class NameChanged(val name: String) : AddHouseUiEvent()
    data class AddHouseClick(val house: House) : AddHouseUiEvent()
    data object SheetDismissed : AddHouseUiEvent()
}