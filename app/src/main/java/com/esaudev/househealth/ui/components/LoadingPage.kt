package com.esaudev.househealth.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.esaudev.househealth.R

@Composable
fun LoadingPage(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LoadingWheel(
            contentDesc = stringResource(id = R.string.loading)
        )
    }
}