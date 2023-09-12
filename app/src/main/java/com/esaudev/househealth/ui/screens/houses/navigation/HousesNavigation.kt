package com.esaudev.househealth.ui.screens.houses.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.esaudev.househealth.ui.screens.houses.HousesRoute

const val housesRoute = "houses_route"

fun NavController.navigateToHouses(navOptions: NavOptions? = null) {
    this.navigate(housesRoute, navOptions)
}

fun NavGraphBuilder.housesScreen(
    onHouseClick: (String) -> Unit
) {
    composable(
        route = housesRoute
    ) {
        HousesRoute(onHouseClick = onHouseClick)
    }
}