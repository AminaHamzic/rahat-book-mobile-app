package com.example.moodapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moodapp.screens.Notes
import com.example.moodapp.screens.Tips

/*
@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomNav.MoodTracker.route
    ){
        composable(route = BottomNav.MoodTracker.route) {
            BottomNav.MoodTracker
        }
        composable(route = BottomNav.Notes.route) {
            BottomNav.Notes
        }
        composable(route = BottomNav.Tips.route) {
            BottomNav.Tips
        }
    }

}*/


@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNav.MoodTracker.route
    ) {
        composable(route = BottomNav.MoodTracker.route) {
            CalenderScreen()
        }
        composable(route = BottomNav.Tips.route) {
            Tips()
        }
        composable(route = BottomNav.Notes.route) {
            Notes()
        }
    }
}