package com.esaudev.househealth.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.esaudev.househealth.R
import com.esaudev.househealth.domain.model.ServiceType
import com.esaudev.househealth.domain.model.getContent
import com.esaudev.househealth.ext.formatToMoney

@Composable
fun ExpenseCard(
    amount: Double,
    serviceType: ServiceType
) {
    Card(
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            Text(
                text = amount.formatToMoney(),
                style = MaterialTheme.typography.h2
            )
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                shape = RoundedCornerShape(24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(id = serviceType.getContent().iconRes),
                            contentDescription = serviceType.getContent().iconDesc.asString()
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = serviceType.getContent().name.asString(),
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.currency),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ExpenseCardPreview() {
    SurfaceThemed {
        ExpenseCard(amount = 100.503, serviceType = ServiceType.Light)
    }
}