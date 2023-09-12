package com.esaudev.househealth.domain.model

import androidx.annotation.DrawableRes
import com.esaudev.househealth.R
import com.esaudev.househealth.ui.util.UiText

enum class ServiceType {
    Light, Gas, Water, Other,
}

data class ServiceTypeContent(
    val name: UiText,
    @DrawableRes val iconRes: Int,
    val iconDesc: UiText
)

fun ServiceType.getContent(): ServiceTypeContent {
    return when (this) {
        ServiceType.Light -> ServiceTypeContent(
            name = UiText.StringResource(R.string.light),
            iconRes = R.drawable.ic_bolt_solid,
            iconDesc = UiText.StringResource(R.string.light)
        )
        ServiceType.Gas -> ServiceTypeContent(
            name = UiText.StringResource(R.string.gas),
            iconRes = R.drawable.ic_fire_burner_solid,
            iconDesc = UiText.StringResource(R.string.gas)
        )
        ServiceType.Water -> ServiceTypeContent(
            name = UiText.StringResource(R.string.water),
            iconRes = R.drawable.ic_water_solid,
            iconDesc = UiText.StringResource(R.string.water)
        )
        ServiceType.Other -> ServiceTypeContent(
            name = UiText.StringResource(R.string.other),
            iconRes = R.drawable.ic_wrench_solid,
            iconDesc = UiText.StringResource(R.string.other)
        )
    }
}