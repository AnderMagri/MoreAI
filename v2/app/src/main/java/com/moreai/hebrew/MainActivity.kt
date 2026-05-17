package com.moreai.hebrew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.moreai.hebrew.ui.screens.MainScreen
import com.moreai.hebrew.ui.theme.MoreAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoreAITheme {
                MainScreen()
            }
        }
    }
}
