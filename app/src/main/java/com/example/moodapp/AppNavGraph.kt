package com.example.moodapp


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moodapp.screens.*

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object WelcomeScreen : Screen("welcome_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")}

@Composable
fun AppNavGraph(navController: NavHostController, userViewModel: UserViewModel) {
    NavHost(navController = navController,
    startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Screen.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController, userViewModel)
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController, userViewModel)
        }
        composable(route = BottomNav.MoodTracker.route) {
            MainScreen()
        }
        composable(route = BottomNav.Tips.route) {
            Tips()
        }
        composable(route = BottomNav.Notes.route) {
            Notes()
        }
    }}



