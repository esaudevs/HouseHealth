package com.esaudev.househealth.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.esaudev.househealth.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectAllServicesCard(
    isSelected: Boolean = false,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val containerAnimatedColor = animateColorAsState(
        targetValue = when {
            isSelected && enabled -> MaterialTheme.colors.primary
            isSelected && !enabled -> Color.Gray.copy(alpha = 0.5f)
            !isSelected && enabled -> MaterialTheme.colors.surface
            else -> {
                Color.Gray.copy(alpha = 0.5f)
            }
        },
        label = "containerColor"
    )
    val contentAnimatedColor = animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
        label = "contentColor"
    )

    Card(
        modifier = Modifier
            .size(
                width = 64.dp,
                height = 80.dp
            ),
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(40),
        backgroundColor = containerAnimatedColor.value,
        contentColor = contentAnimatedColor.value
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.all),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

private class SelectAllServicesCardProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(
            true,
            false
        )
}

@Preview
@Composable
private fun SelectAllServicesCardPreview(
    @PreviewParameter(SelectAllServicesCardProvider::class) isSelected: Boolean
) {
    SurfaceThemed {
        SelectAllServicesCard(
            onClick = {},
            isSelected = isSelected,
            enabled = false
        )
    }
}