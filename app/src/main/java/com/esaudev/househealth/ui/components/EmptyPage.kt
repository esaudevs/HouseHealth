package com.esaudev.househealth.ui.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.esaudev.househealth.R

@Composable
fun EmptyPage(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    @StringRes iconContentDescRes: Int = R.string.empty_page_icon_content_desc,
    message: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.alpha(.6f).size(32.dp),
            painter = painterResource(id = iconRes),
            contentDescription = stringResource(id = iconContentDescRes)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .alpha(.6f)
                .padding(horizontal = 32.dp),
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
@Preview(name = "Light mode", showBackground = true)
@Preview(name = "Dark mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun EmptyPagePreview() {
    SurfaceThemed {
        EmptyPage(
            modifier = Modifier.fillMaxSize(),
            iconRes = R.drawable.ic_people_roof_solid,
            message = stringResource(id = R.string.empty_page_icon_content_desc)
        )
    }
}