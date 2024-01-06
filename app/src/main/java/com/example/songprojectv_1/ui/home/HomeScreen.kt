package com.example.songprojectv_1.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.songApplication.*
import com.example.songprojectv_1.data.Songs
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.songprojectv_1.ui.navigation.NavigationDestination

import com.example.songprojectv_1.R
import com.example.songprojectv_1.SongsTopAppBar
import com.example.songprojectv_1.ui.AppViewModelProvider

//HomeScreen Composable likely serves as the UI entry point
//for the home screen of the app. It receives functions as parameters
object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

//likely responsible for displaying the main screen, this function takes five parameters
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToSongEntry: () -> Unit,
    navigateToSongEntrym: () -> Unit,
    navigateToSongUpdate: (Int) -> Unit,

    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
   //Collects the state from the HomeViewModel using a collectAsState method to observe changes in the UI state.
    val homeUiState by viewModel.homeUiState.collectAsState()
    //Sets up the basic structure for the screen, including top app bar and body content.
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
       //Defines the top app bar using SongsTopAppBar, displaying the title defined in the HomeDestination with scrolling behavior.
        topBar = {
            SongsTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },{
      //  floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_large))
                    .fillMaxWidth()
            ) {
                val customColor = Color(0xFF2D8FC7)
                //Floating Action Buttons for adding songs, followed by the HomeBody Composable displaying the list of songs.
                FloatingActionButton(
                    onClick = navigateToSongEntry,
                    shape = MaterialTheme.shapes.medium,
                    containerColor = customColor,
                    modifier = Modifier
                    .padding(bottom = 16.dp),
                ) {
                    Text(text = "    Add from Available songs  ", color = Color.White, style = TextStyle(fontSize = 16.sp))

//                    Icon(
//                        imageVector = Icons.Default.Add,
//                        contentDescription = stringResource(R.string.song_entry_title)
//                    )
                }
//                val customColor = Color(0xFF2D8FC7)
                FloatingActionButton(
                    onClick = navigateToSongEntrym,
                    shape = MaterialTheme.shapes.medium,
                    containerColor = customColor,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(text = "        Add Custom Songs          ",  color = Color.White, style = TextStyle(fontSize = 16.sp))
//                    Icon(
//                        imageVector = Icons.Default.Add,
//                        contentDescription = stringResource(R.string.song_entry_title)
//                    )
                }

            }
       }

    )
    //for displaying a list of songs
    { innerPadding ->
        HomeBody(
            songsList = homeUiState.songsList,

            onItemClick = navigateToSongUpdate,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun HomeBody(
    songsList: List<Songs>, onItemClick: (Int) -> Unit, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        //if-else block is used to determine what to display based on the songsList
       //If songsList is empty, it displays a Text Composable indicating no songs are available.
        if (songsList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_song_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        }
        //If songsList is not empty, it displays a SongList Composable.
        else {
            SongList(
                songsList = songsList,
                onItemClick = { onItemClick(it.track_id) },
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}
//SongList function creates a scrollable list of songs using LazyColumn and sets up each
//song item to be clickable, invoking the provided onItemClick callback when clicked with
//the corresponding Songs object.
@Composable
private fun SongList(
    songsList: List<Songs>, onItemClick: (Songs) -> Unit, modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = songsList, key = { it.track_id }) { item ->
            SongsItem(songs = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(item) })
        }
    }
}


@Composable
//This SongsItem function seems to define the UI representation for an individual item (song) in a list.
private fun SongsItem(
    songs: Songs, modifier: Modifier = Modifier
) {
    val customBackgroundColor = colorResource(id = R.color.customBackground)
    //Represents a Material Design card component that provides elevation and a surface to hold content.
    Card(
        modifier = modifier
            .background(customBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
//        val customBackgroundColor = colorResource(id = R.color.customBackground)
        //Acts as a container to hold other Composables and manage their layout.
        Box(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_large))
//                .background(customBackgroundColor)
        ) {
            //SongsItem Composable generates a structured layout for displaying specific details about a song within a card-like container.
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_large)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            )
            {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val customColor = Color(0xFF2D8FC7)
                    Text(
                        text = "Track ID: ${songs.track_id}",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = customColor,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "*",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Name: ${songs.track_name}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "*",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp)) // Add some space between the bullet and text
                    Text(
                        text = "artist name: ${songs.artist_name}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "*",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp)) // Add some space between the bullet and text
                    Text(
                        text = "lyrics: ${songs.lyrics_body.split(" ").take(5).joinToString(" ")}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

