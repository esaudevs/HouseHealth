package com.esaudev.househealth.ui.components

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.esaudev.househealth.ui.theme.HouseHealthTheme

@Composable
fun SurfaceThemed(content: @Composable () -> Unit) {
    HouseHealthTheme {
        Surface {
            content()
        }
    }
}