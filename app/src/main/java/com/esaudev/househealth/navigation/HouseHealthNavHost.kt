package com.esaudev.househealth.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.esaudev.househealth.ui.screens.expenses.navigation.expensesScreen
import com.esaudev.househealth.ui.screens.expenses.navigation.navigateToExpenses
import com.esaudev.househealth.ui.screens.houses.navigation.housesRoute
import com.esaudev.househealth.ui.screens.houses.navigation.housesScreen

@Composable
fun HouseHealthNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = housesRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        housesScreen(
            onHouseClick = { houseId ->
                navController.navigateToExpenses(houseId)
            }
        )

        expensesScreen(
            onExpenseClick = {
            }
        )
    }
}