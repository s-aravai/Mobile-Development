package com.example.bromode.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bromode.ui.screens.auth.LoginScreen
import com.example.bromode.ui.screens.auth.SignupScreen
import com.example.bromode.ui.screens.calendar.CalendarScreen
import com.example.bromode.ui.screens.home.HomeScreen
import com.example.bromode.ui.screens.profile.ProfileScreen
import com.example.bromode.ui.screens.settings.SettingsScreen
import com.example.bromode.ui.screens.splash.SplashScreen
import com.example.bromode.ui.screens.tracker.ExerciseTrackerScreen
import com.example.bromode.ui.screens.virtual.VirtualScreen
import com.example.bromode.ui.screens.workout.WorkoutScreen
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bromode.viewmodel.WorkoutViewModel

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }

        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }

        composable(Routes.SIGNUP) {
            SignupScreen(navController)
        }

        composable(Routes.HOME) {
            HomeScreen(navController)
        }

        composable(Routes.WORKOUT) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.HOME)
            }
            val workoutViewModel: WorkoutViewModel = viewModel(parentEntry)

            WorkoutScreen(navController, workoutViewModel)
        }

        composable(Routes.TRACKER) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.HOME)
            }
            val workoutViewModel: WorkoutViewModel = viewModel(parentEntry)

            ExerciseTrackerScreen(navController, workoutViewModel)
        }

        composable(Routes.PROFILE) {
            ProfileScreen(navController)
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(navController)
        }

        composable(Routes.VIRTUAL) {
            VirtualScreen(navController)
        }

        composable(Routes.CALENDAR) {
            CalendarScreen(navController)
        }

    }
}