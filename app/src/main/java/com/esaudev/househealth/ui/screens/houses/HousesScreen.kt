package com.esaudev.househealth.ui.screens.houses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.esaudev.househealth.R
import com.esaudev.househealth.ui.components.EmptyPage
import com.esaudev.househealth.ui.components.HouseCard
import com.esaudev.househealth.ui.components.LoadingPage
import com.esaudev.househealth.ui.sheets.houses.AddHouseModalViewModel
import com.esaudev.househealth.ui.sheets.houses.AddHouseUiEvent
import com.esaudev.househealth.ui.theme.SolidWhite
import com.esaudev.househealth.ui.util.UiTopLevelEvent
import kotlinx.coroutines.launch

@Composable
fun HousesRoute(
    viewModel: HousesViewModel = hiltViewModel(),
    bottomViewModel: AddHouseModalViewModel = hiltViewModel(),
    onHouseClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HousesScreen(
        uiState = uiState,
        onHouseClick = onHouseClick,
        bottomViewModel = bottomViewModel
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun HousesScreen(
    uiState: HousesUiState,
    onHouseClick: (String) -> Unit,
    bottomViewModel: AddHouseModalViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        bottomViewModel.uiTopLevelEvent.collect { event ->
            when (event) {
                is UiTopLevelEvent.Success -> {
                    scope.launch {
                        modalSheetState.hide()
                    }
                }
                else -> Unit
            }
        }
    }

    if (modalSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                bottomViewModel.onEvent(AddHouseUiEvent.SheetDismissed)
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                HousesSheetContent(
                    value = bottomViewModel.uiState.name,
                    hasError = bottomViewModel.uiState.hasError,
                    onValueChange = { houseName ->
                        bottomViewModel.onEvent(AddHouseUiEvent.NameChanged(houseName))
                    },
                    onAddHouseClick = { house ->
                        bottomViewModel.onEvent(AddHouseUiEvent.AddHouseClick(house))
                    }
                )
            }
        },
        sheetShape = RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp),
        sheetBackgroundColor = SolidWhite,
        sheetState = modalSheetState,
        content = {
            when (uiState) {
                is HousesUiState.Empty -> {
                    HousesEmpty(
                        onAddHouseClick = {
                            scope.launch {
                                modalSheetState.show()
                            }
                        }
                    )
                }

                is HousesUiState.HousesWithHouses -> {
                    HousesContent(
                        modifier = Modifier.fillMaxSize(),
                        uiState = uiState,
                        onHouseClick = onHouseClick,
                        onAddHouseClick = {
                            scope.launch {
                                modalSheetState.show()
                            }
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
    )
}

@Composable
fun HousesEmpty(
    onAddHouseClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddHouseClick,
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            ) {
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
            FloatingActionButton(
                onClick = onAddHouseClick,
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            ) {
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
                    style = MaterialTheme.typography.h3
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