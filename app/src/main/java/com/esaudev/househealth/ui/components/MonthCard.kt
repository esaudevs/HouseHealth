package com.esaudev.househealth.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.esaudev.househealth.R
import com.esaudev.househealth.ext.getMonthNameWithYear
import com.esaudev.househealth.ext.toSentence
import java.time.LocalDateTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MonthCard(
    date: LocalDateTime,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(50),
        border = BorderStroke(
            width = 1.dp,
            color =  MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
        ),
        backgroundColor = Color.Transparent,
        onClick = onClick,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_date_range), contentDescription = "")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = date.getMonthNameWithYear().toSentence())
        }
    }
}

@Preview
@Composable
fun MonthCardPreview() {
    SurfaceThemed {
        MonthCard(
            date = LocalDateTime.now(),
            onClick = {}
        )
    }
}