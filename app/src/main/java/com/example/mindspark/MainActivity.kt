package com.example.mindspark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import com.example.mindspark.navigation.AppNavigation
import com.example.mindspark.utils.NoInternetWarning
import com.example.mindspark.utils.rememberNetworkState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val isConnected = rememberNetworkState(this)

//            MindSparkTheme {
            Column {
                NoInternetWarning(isConnected = isConnected.value)
                AppNavigation()
            }
            }
        }
    }
//}
