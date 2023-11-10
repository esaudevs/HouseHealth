package com.esaudev.househealth.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.esaudev.househealth.navigation.HouseHealthNavHost
import com.esaudev.househealth.ui.theme.HouseHealthTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HouseHealthTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val snackbarHostState = remember { SnackbarHostState() }

                    Scaffold(
                        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                    ) { paddingValues ->
                        HouseHealthNavHost(
                            modifier = Modifier.padding(paddingValues),
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}