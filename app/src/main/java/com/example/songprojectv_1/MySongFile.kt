package com.example.songprojectv_1


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import androidx.navigation.compose.rememberNavController
import com.example.songprojectv_1.ui.navigation.SongsNavHost

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
//the SongApp function is an entry point or a higher-level component
//in the app that initializes a navigation controller (navController)
// and utilizes the SongsNavHost
fun SongApp(navController: NavHostController = rememberNavController()) {
    SongsNavHost(navController = navController)
}
// this creates a top app bar for displaying in a Jetpack Compose UI.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsTopAppBar(
    //Represents the text title displayed in the app bar.
    title: String,
    //A Boolean flag that determines if the back navigation icon should be visible or not.
    canNavigateBack: Boolean,
    //allows modifying the appearance or behavior of the app bar using Jetpack Compose's Modifier system
    modifier: Modifier = Modifier,
    //Describes the behavior of the app bar during scrolling.
    scrollBehavior: TopAppBarScrollBehavior? = null,
    //Represents the action to be performed when the back navigation icon is clicked. By default, it's an empty lambda function.
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        //this part is for displaying song icon.
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) { Text(
                text = title,
                modifier = Modifier.weight(.9f),
                textAlign = TextAlign.Center
            )

                Icon(
                    painter = painterResource(id = R.drawable.karaoke),
                    contentDescription = "karaoke",
                    modifier = Modifier.size(50.dp)
                        .weight(.2f)
                )
                // Adjust spacing as needed

            }
        },
        //this part is for navigation button
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            Row() {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            }
        }
       )}



