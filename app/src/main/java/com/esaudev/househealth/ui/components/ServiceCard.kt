package com.esaudev.househealth.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.esaudev.househealth.domain.model.ServiceType
import com.esaudev.househealth.domain.model.getContent
import com.esaudev.househealth.ui.theme.LightGray

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ServiceCard(
    serviceType: ServiceType,
    isSelected: Boolean = false,
    onClick: (ServiceType) -> Unit,
    enabled: Boolean = true
) {
    val containerAnimatedColor = animateColorAsState(
        targetValue = when {
            isSelected && enabled -> MaterialTheme.colors.primary
            isSelected && !enabled -> LightGray.copy(alpha = 0.4f)
            !isSelected && enabled -> MaterialTheme.colors.surface
            else -> {
                LightGray.copy(alpha = 0.4f)
            }
        },
        label = "containerColor"
    )

    val contentAnimatedColor = animateColorAsState(
        targetValue = when {
            isSelected && enabled -> MaterialTheme.colors.onPrimary
            isSelected && !enabled -> MaterialTheme.colors.onSurface.copy(
                alpha = 0.4f
            )
            !isSelected && enabled -> MaterialTheme.colors.onSurface
            else -> {
                MaterialTheme.colors.onSurface.copy(
                    alpha = 0.4f
                )
            }
        },
        label = "contentColor"
    )

    Card(
        modifier = Modifier
            .size(width = 64.dp, height = 80.dp),
        onClick = {
            onClick(serviceType)
        },
        enabled = enabled,
        shape = RoundedCornerShape(40),
        backgroundColor = containerAnimatedColor.value,
        contentColor = contentAnimatedColor.value,
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp),
                painter = painterResource(id = serviceType.getContent().iconRes),
                contentDescription = serviceType.getContent().iconDesc.asString()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = serviceType.getContent().name.asString(),
                style = MaterialTheme.typography.body2
            )
        }
    }
}

private class CardSelectedProvider : PreviewParameterProvider<Pair<ServiceType, Boolean>> {
    override val values: Sequence<Pair<ServiceType, Boolean>>
        get() = sequenceOf(
            *ServiceType.values().map {
                Pair(it, false)
            }.toTypedArray(),
            *ServiceType.values().map {
                Pair(it, true)
            }.toTypedArray()
        )
}

@Preview
@Composable
private fun ServiceCardPreview(
    @PreviewParameter(CardSelectedProvider::class) providerPair: Pair<ServiceType, Boolean>
) {
    SurfaceThemed {
        ServiceCard(
            serviceType = providerPair.first,
            isSelected = providerPair.second,
            onClick = {},
            enabled = false
        )
    }
}