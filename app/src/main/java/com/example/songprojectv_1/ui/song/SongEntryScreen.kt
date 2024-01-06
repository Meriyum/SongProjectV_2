package com.example.songprojectv_1.ui.song

import android.util.Log

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.songprojectv_1.data.MusixmatchResponse
import com.example.songprojectv_1.data.Track

import com.example.songprojectv_1.ui.navigation.NavigationDestination

import com.example.songprojectv_1.R
import com.example.songprojectv_1.SongsTopAppBar
import com.example.songprojectv_1.ui.AppViewModelProvider
import kotlinx.coroutines.launch


object SongEntryDestination : NavigationDestination {
    override val route = "item_entry"
    override val titleRes = R.string.song_entry_title
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
//Composable function representing a screen for entering song details
fun SongEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    viewModel: ItemEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)

) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()
    val trackId = remember { mutableStateOf("") }
    val trackName = remember { mutableStateOf("") }
    val q_lyrics = remember { mutableStateOf("") }
    // val songState by viewModel.song.observeAsState()


    //layout structure that typically contains app bars, drawers,
    //and other surfaces for composing app screens.
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SongsTopAppBar(
                title = stringResource(SongEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            Box {
                // in the column several UI elements are arranged vertically to gather song details and perform a search
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Text(
                        text = "*",
                        style = TextStyle(
                            color = Color.Red,
                            fontSize = 24.sp
                        ),
                        modifier = Modifier.padding(start = 18.dp)
                    )
                    //used to input the song name and lyrics, each with a close button to clear the text.
                    TextField(
                        value = trackName.value,
                        onValueChange = { trackName.value = it },
                        label = { Text("Enter Song Name") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),


                        trailingIcon = {
                            IconButton(onClick = {
                                trackName.value = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = ""
                                )
                            }
                        }
                    )
                    Text(
                        text = "*",
                        style = TextStyle(
                            color = Color.Red,
                            fontSize = 24.sp
                        ),
                        modifier = Modifier.padding(start = 18.dp)
                    )
                    TextField(
                        value = q_lyrics.value,
                        onValueChange = { q_lyrics.value = it },
                        label = { Text("Enter Lyrics") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        shape = RoundedCornerShape(16.dp),
                        trailingIcon = {
                            IconButton(onClick = {
                                q_lyrics.value = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = ""
                                )
                            }
                        }
                    )
                    Text(
                        text = "Required Fields",
                        style = TextStyle(
                            color = Color.Red,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier.padding(start = 18.dp)
                    )
                    val controller =
                        LocalSoftwareKeyboardController.current
//                    Button is provided with the label "Search" to trigger a search operation
//                    when clicked. It hides the keyboard and uses a view model (viewModel) to
//                    perform a song search based on the entered song name and lyrics.
                    Button(

                        onClick = {
                            coroutineScope.launch {
                                controller?.hide()
                                viewModel.searchSong(trackName, q_lyrics)


                            }
                        },
                        modifier = Modifier

                            .padding(vertical = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Search")
                    }
                }
            }

            val songState = viewModel.song.observeAsState()

            if (songState != null) {
                SongsList(songState, viewModel)
            } else {
                Text(
                    text = "No match Found",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier.padding(start = 18.dp)
                )
            }
        }


    }
}

//displaying a list of songs retrieved from a MusixmatchResponse
//songState containing MusixmatchResponse,presumably representing
//the state of songs obtained from some data source.
//view model in songlist is used for handling interactions or updates related to song items.
@Composable
fun SongsList(songState: State<MusixmatchResponse?>, viewModel: ItemEntryViewModel) {
    //let function is used to execute only if songState.value is not null
    songState.value?.let { musixmatchResponse ->

        //if statement checks if the musixmatchResponse is not null.
        if (musixmatchResponse != null) {
            //Iteration through the list of tracks received in the MusixmatchResponse
            musixmatchResponse.message.body.track_list.forEach { trackItem ->
                //prints artist name and track ID, to see track details in the console
                Log.d(
                    "test1",
                    "name=${trackItem.track.artist_name}, track_id=${trackItem.track.track_id}"
                )
                //Calls TrackItemshow that is responsible for displaying each individual track item in the UI.
                //It passes the track details and the viewModel instance to handle
                //updates or activity related to that track item.
                TrackItemshow(trackItem.track, viewModel)
            }
        }
    }

}

// responsible for rendering a visual representation of a track with a
//checkbox, displaying its artist name and ID
///////////////////////////////////////////
//represents a UI component responsible for displaying information about a specific track.
@Composable
fun TrackItemshow(track: Track, viewModel: ItemEntryViewModel) {
//Mutable state variables are declared using remember mutableStateOf().
// These variables are used to keep track of the state within this Composable.
    var Trackid by remember { mutableStateOf(0) }
    var titledetail by remember { mutableStateOf("") }
    var authordetail by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier

            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                modifier = Modifier.padding(end = 16.dp)
            )
            Column {
                Text(text = "Name: ${track.artist_name}", style = TextStyle(fontSize = 18.sp))
                Text(
                    text = "Track ID: ${track.track_id}",
                    style = TextStyle(fontSize = 16.sp, color = Color.Gray)
                )
            }
        }
    }
    //When the checkbox state changes (isChecked), it logs the checked track's ID and updates
    // the state variables with the current track's details.
    LaunchedEffect(isChecked) {
        if (isChecked) {
            android.util.Log.d("checkbox", "Checked Track ID: ${track.track_id}")
            Trackid = track.track_id
            titledetail = track.track_name
            authordetail = track.artist_name


            viewModel.loadSongsDetail(Trackid, titledetail, authordetail)
        }
    }
}
