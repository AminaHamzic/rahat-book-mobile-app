package com.example.moodapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moodapp.R
import com.example.moodapp.Screen

@Composable
fun WelcomeScreen(navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxSize(),

            contentAlignment = Alignment.Center,


    ) {
        Image(
            painter = painterResource(id = R.drawable.yellow_gradient_background_minimal_ui_mockup_smartphone_instagram_story_for_mobile_application),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {




            Button(
                onClick = { navController.navigate(Screen.LoginScreen.route) },
                colors = ButtonDefaults.buttonColors(Color.Magenta),
                )
            {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate(Screen.RegisterScreen.route) },
                colors = ButtonDefaults.buttonColors(Color.Magenta),
            )
            {
                Text(text = "Create an account")
            }
        }




    }

}

