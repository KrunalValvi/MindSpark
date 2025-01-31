package com.example.mindspark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mindspark.navigation.AppNavigation
import com.example.mindspark.ui.theme.MindSparkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MindSparkTheme {
                AppNavigation()
            }
        }
    }
}
