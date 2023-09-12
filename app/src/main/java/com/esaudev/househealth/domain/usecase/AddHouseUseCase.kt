package com.esaudev.househealth.domain.usecase

import com.esaudev.househealth.domain.model.House
import com.esaudev.househealth.domain.repository.HousesRepository
import javax.inject.Inject

class AddHouseUseCase @Inject constructor(
    private val housesRepository: HousesRepository
) {
    suspend operator fun invoke(house: House) = housesRepository.add(house)
}