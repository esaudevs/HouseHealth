package com.esaudev.househealth.ui.screens.houses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.esaudev.househealth.R
import com.esaudev.househealth.ui.components.EmptyPage
import com.esaudev.househealth.ui.components.HouseCard
import com.esaudev.househealth.ui.components.LoadingPage
import com.esaudev.househealth.ui.sheets.houses.AddHouseModalBottomSheet
import kotlinx.coroutines.launch

@Composable
fun HousesRoute(
    viewModel: HousesViewModel = hiltViewModel(),
    onHouseClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HousesScreen(
        uiState = uiState,
        onHouseClick = onHouseClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HousesScreen(
    uiState: HousesUiState,
    onHouseClick: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()

    AddHouseModalBottomSheet(
        sheetState = sheetState,
        openSheet = openBottomSheet,
        onDismissRequest = {
            openBottomSheet = !openBottomSheet
        },
        onSuccess = {
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!bottomSheetState.isVisible) {
                    openBottomSheet = false
                }
            }
        }
    )

    when (uiState) {
        is HousesUiState.Empty -> {
            HousesEmpty(
                onAddHouseClick = {
                    openBottomSheet = !openBottomSheet
                }
            )
        }

        is HousesUiState.HousesWithHouses -> {
            HousesContent(
                modifier = Modifier.fillMaxSize(),
                uiState = uiState,
                onHouseClick = onHouseClick,
                onAddHouseClick = {
                    openBottomSheet = !openBottomSheet
                }
            )
        }

        HousesUiState.Loading -> {
            LoadingPage(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun HousesEmpty(
    onAddHouseClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddHouseClick) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(
                        id = R.string.open_add_house_bottom_sheet_content_desc
                    )
                )
            }
        }
    ) { paddingValues ->
        EmptyPage(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            iconRes = R.drawable.ic_people_roof_solid,
            message = stringResource(id = R.string.houses__empty_houses)
        )
    }
}

@Composable
fun HousesContent(
    uiState: HousesUiState.HousesWithHouses,
    onHouseClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    onAddHouseClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddHouseClick) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(
                        id = R.string.open_add_house_bottom_sheet_content_desc
                    )
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .padding(paddingValues),
            contentPadding = PaddingValues(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.your_houses),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            items(uiState.houses, key = { it.id }) {
                HouseCard(
                    house = it,
                    onClick = onHouseClick
                )
            }

            item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
    }
}