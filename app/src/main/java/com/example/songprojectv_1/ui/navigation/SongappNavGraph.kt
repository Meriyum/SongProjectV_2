

package com.example.songprojectv_1.ui.navigation

import com.example.songprojectv_1.ui.song.MqttComposeActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import com.example.songprojectv_1.ui.song.SongEntryScreen
import com.example.songprojectv_1.ui.song.SongEntryDestination

import com.example.songprojectv_1.ui.song.SongDetailsDestination
import com.example.songprojectv_1.ui.home.HomeDestination
import com.example.songprojectv_1.ui.home.HomeScreen
import com.example.songprojectv_1.ui.song.SongDetailsScreen
import com.example.songprojectv_1.ui.song.SongEditDestination
import com.example.songprojectv_1.ui.song.SongEditScreen
import com.example.songprojectv_1.ui.song.SongEntryDestinationm
import com.example.songprojectv_1.ui.song.SongEntryScreenm
import com.example.songprojectv_1.ui.song.SongMqttDestination


@Composable
fun SongsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        // the starting destination of the navigation flow to the "HomeDestination"
        // within the navigation graph associated with the given navController.
        navController = navController, startDestination = HomeDestination.route, modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                //following functions or callbacks that seem to trigger navigation
                // actions to different destinations within the app
               navigateToSongEntry = { navController.navigate(SongEntryDestination.route) },
              //  navigateToMqttEntrym = { navController.navigate({SongEntryDestinationm.route)},
               navigateToSongEntrym = { navController.navigate(SongEntryDestinationm.route) },
                navigateToSongUpdate = {  navController.navigate("${SongDetailsDestination.route}/${it}")
                })
        }
        //this code sets up a Composable function associated with
        // the SongEntryDestination,SongEntryDestinationm and SongDetailDestination
        // handle navigation actions, specifically moving back and navigating up within the app'
        composable(route = SongEntryDestination.route) {
            SongEntryScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }

        composable(route = SongEntryDestinationm.route) {
            SongEntryScreenm(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
        //
        composable(
            route = SongDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(SongDetailsDestination.songIdArg) {
                type = NavType.IntType
            })
        ) {
            //SongDetailsScreen is a Composable function representing the UI for displaying song details.
            // It provides functionality to navigate to an edit screen, MQTT-related entry screen.
            SongDetailsScreen(
                navigateToEditItem = { navController.navigate("${SongEditDestination.route}/$it") },
                navigateToMqttEntry = { navController.navigate("${SongMqttDestination.route}/$it")
                    println("Navigating to ${SongMqttDestination.route}/$it")},

                navigateBack = { navController.navigateUp() }
            )
        }
        //this code sets up a Composable function associated with
        // the SongEditDestinatio and songmqttdestination
        // handle navigation actions, specifically moving back and navigating up within the app'
        composable(
            route = SongEditDestination.routeWithArgs,
            arguments = listOf(navArgument(SongEditDestination.songIdArg) {
                type = NavType.IntType
            })
        ) {
            SongEditScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
        composable(
            route = SongMqttDestination.routeWithArgs,
            arguments = listOf(navArgument(SongMqttDestination.songIdArg) {
                type = NavType.IntType
            })
        ) {


            MqttComposeActivity(
                navigateToMqttEntry = { /* provide the appropriate logic or leave it empty if not needed */ },
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                navController = navController,

            )
        }
    }
}


