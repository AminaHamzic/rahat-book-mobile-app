package com.example.moodapp.screens

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import com.example.moodapp.R
import com.example.moodapp.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()

    // Define the transition states
    val transitionState = remember { MutableTransitionState(false) }
    val transition = updateTransition(transitionState)

    // Define the animation properties
    val scale by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 1000) },
        targetValueByState = { if (it) 1.2f else 1.0f }
    )
    val alpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 1000) },
        targetValueByState = { if (it) 1f else 0f }
    )

    LaunchedEffect(key1 = true) {
        scope.launch {
            delay(3000)  // wait for 3 seconds
            transitionState.targetState = true
            delay(1000)  // wait for additional 1 second
            navController.navigate(Screen.WelcomeScreen.route)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(400.dp)
                .scale(scale)
                .alpha(alpha)
        )
    }
}


