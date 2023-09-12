package com.esaudev.househealth.ui.screens.houses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.househealth.domain.model.House
import com.esaudev.househealth.domain.repository.HousesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class HousesViewModel @Inject constructor(
    private val housesRepository: HousesRepository
) : ViewModel() {
    val uiState: StateFlow<HousesUiState> = housesRepository.observeAll()
        .map {
            if (it.isEmpty()) HousesUiState.Empty else HousesUiState.HousesWithHouses(houses = it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HousesUiState.Loading
        )
}

sealed interface HousesUiState {
    data object Loading : HousesUiState

    data class HousesWithHouses(
        val houses: List<House>
    ) : HousesUiState

    data object Empty : HousesUiState
}