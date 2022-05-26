package com.eneskayiklik.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.eneskayiklik.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ContractViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = viewModel.state.collectAsState().value
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Greeting(state)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Your balance\n$name", textAlign = TextAlign.Center)
}