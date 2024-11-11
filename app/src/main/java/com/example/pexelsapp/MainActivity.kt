package com.example.pexelsapp

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.pexelsapp.navigation.PexelsNavigation
import com.example.pexelsapp.ui.theme.PexelsAppTheme
import com.example.pexelsapp.utils.NetworkReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var networkReceiver: NetworkReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PexelsAppTheme {
                PexelsApp()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        networkReceiver = NetworkReceiver(this)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkReceiver)
    }
}


@Composable
fun PexelsApp() {
    PexelsNavigation()
}