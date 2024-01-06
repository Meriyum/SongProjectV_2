

package com.example.songprojectv_1.ui.song

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.songprojectv_1.data.SongsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class SongDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val songsRepository: SongsRepository,
) : ViewModel() {

    private val track_id: Int = checkNotNull(savedStateHandle[SongDetailsDestination.songIdArg])
    val uiState: StateFlow<SongDetailsUiState> =
        songsRepository.getSongStream(track_id)
            .filterNotNull()

            .map {
                SongDetailsUiState(songDetails = it.toItemDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = SongDetailsUiState()
            )

    suspend fun deleteSong() {
        songsRepository.deleteSong(uiState.value.songDetails.toItem())
    }

//    fun mqttFunction() {
//        com.example.songprojectv_1.ui.song.MqttComposeActivity()
//    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class SongDetailsUiState(
//    val outOfStock: Boolean = true,
    val songDetails: SongDetails = SongDetails()
)
