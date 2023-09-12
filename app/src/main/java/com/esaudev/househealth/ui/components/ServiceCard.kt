package com.esaudev.househealth.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.esaudev.househealth.domain.model.ServiceType
import com.esaudev.househealth.domain.model.ServiceTypeContent
import com.esaudev.househealth.domain.model.getContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceCard(
    serviceTypeContent: ServiceTypeContent,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val containerAnimatedColor = animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer,
        label = "containerColor"
    )
    val contentAnimatedColor = animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
        label = "contentColor"
    )
    Card(
        modifier = Modifier
            .size(width = 64.dp, height = 80.dp),
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(40),
        colors = CardDefaults.cardColors(
            containerColor = containerAnimatedColor.value,
            contentColor = contentAnimatedColor.value
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp),
                painter = painterResource(id = serviceTypeContent.iconRes),
                contentDescription = serviceTypeContent.iconDesc.asString()
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = serviceTypeContent.name.asString(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

private class CardSelectedProvider : PreviewParameterProvider<Pair<ServiceTypeContent, Boolean>> {
    override val values: Sequence<Pair<ServiceTypeContent, Boolean>>
        get() = sequenceOf(
            *ServiceType.values().map {
                Pair(it.getContent(), false)
            }.toTypedArray(),
            *ServiceType.values().map {
                Pair(it.getContent(), true)
            }.toTypedArray()
        )
}

@Preview
@Composable
private fun ServiceCardPreview(
    @PreviewParameter(CardSelectedProvider::class) providerPair: Pair<ServiceTypeContent, Boolean>
) {
    SurfaceThemed {
        ServiceCard(
            serviceTypeContent = providerPair.first,
            isSelected = providerPair.second,
            onClick = {}
        )
    }
}