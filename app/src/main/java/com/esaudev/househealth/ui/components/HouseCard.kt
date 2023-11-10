package com.esaudev.househealth.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.esaudev.househealth.R
import com.esaudev.househealth.domain.model.House

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HouseCard(
    house: House,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colors.background)
    ) {
        Card(
            shape = RoundedCornerShape(20),
            onClick = {
                onClick(house.id)
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.house_in),
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        modifier = Modifier,
                        text = house.name,
                        style = MaterialTheme.typography.h2
                    )
                }
                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = stringResource(
                        id = R.string.go_forward_house_selection_content_desc
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun HouseCardPreview() {
    SurfaceThemed {
        HouseCard(
            house = House(
                name = "Casa de Zihuatanejo"
            ),
            onClick = {}
        )
    }
}