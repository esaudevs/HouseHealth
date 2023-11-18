package com.esaudev.househealth.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.esaudev.househealth.R
import com.esaudev.househealth.ext.getMonthNameWithYear
import java.time.LocalDateTime

@Composable
fun MonthSelector(
    date: LocalDateTime,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonthClick) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = stringResource(id = R.string.previous_month_content_desc)
            )
        }

        AnimatedContent(
            targetState = date, label = ""
        ) { targetState ->
            Text(
                text = targetState.getMonthNameWithYear(),
                style = MaterialTheme.typography.h4
            )
        }

        IconButton(onClick = onNextMonthClick) {
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = stringResource(id = R.string.next_month_content_desc)
            )
        }
    }
}

@Preview
@Composable
private fun MonthSelectorPreview() {
    SurfaceThemed {
        MonthSelector(
            date = LocalDateTime.now(),
            onPreviousMonthClick = {},
            onNextMonthClick = {}
        )
    }
}