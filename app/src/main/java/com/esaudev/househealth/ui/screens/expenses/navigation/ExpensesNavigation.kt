package com.esaudev.househealth.ui.screens.expenses.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.esaudev.househealth.ui.screens.expenses.ExpensesRoute
import com.esaudev.househealth.util.StringDecoder

const val houseIdArg = "houseId"

class ExpensesArgs(val houseId: String) {
    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
        this(stringDecoder.decodeString(checkNotNull(savedStateHandle[houseIdArg])))
}

fun NavController.navigateToExpenses(houseId: String) {
    val encodedId = Uri.encode(houseId)
    this.navigate("expenses_route?houseId=$encodedId")
}

fun NavGraphBuilder.expensesScreen(
    onExpenseClick: () -> Unit
) {
    composable(
        route = "expenses_route?houseId={$houseIdArg}",
        arguments = listOf(
            navArgument(houseIdArg) { type = NavType.StringType }
        )
    ) {
        ExpensesRoute(
            onExpenseClick = {
            }
        )
    }
}