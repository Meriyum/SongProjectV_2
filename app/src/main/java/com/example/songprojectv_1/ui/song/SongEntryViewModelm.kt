/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.songprojectv_1.ui.song

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.songprojectv_1.data.Songs
import com.example.songprojectv_1.data.SongsRepository


/**
 * ViewModel to validate and insert items in the Room database.
 */
class ItemEntryViewModelm(private val songsRepository: SongsRepository) : ViewModel() {

    /**
     * Holds current song ui state
     */
    var songUiStatem by mutableStateOf(SongUiStatem())
        private set

    /**
     * Updates the [songUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiStatem(songDetailsm: SongDetailsm) {
        songUiStatem =
            SongUiStatem(songDetailsm = songDetailsm, isEntryValid = validateInputm(songDetailsm))
    }

    suspend fun saveSongm() {
        if (validateInputm()) {
            songsRepository.insertSong(songUiStatem.songDetailsm.toItem())
        }
    }

    private fun validateInputm(uiStatem: SongDetailsm = songUiStatem.songDetailsm): Boolean {
        return with(uiStatem) {
            namem.isNotBlank() && authorm.isNotBlank() && lyricsm.isNotBlank()
        }
    }
}

/**
 * Represents Ui State for an Item.
 */
data class SongUiStatem(
    val songDetailsm: SongDetailsm = SongDetailsm(),
    val isEntryValid: Boolean = false
)

data class SongDetailsm(
    val id: Int = 0,
    val namem: String = "",
    val authorm: String = "",
    val lyricsm: String = "",
)

/**
 * Extension function to convert [SongDetails] to [Songs]. If the value of [SongDetails.author] is
 * not a valid [Double], then the author will be set to 0.0. Similarly if the value of
 * [SongDetails.lyrics] is not a valid [Int], then the lyrics will be set to 0
 */
fun SongDetailsm.toItem(): Songs = Songs(
    track_id = id,
    track_name = namem,
    artist_name = authorm,
    lyrics_body = lyricsm

)



/**
 * Extension function to convert [Songs] to [SongUiState]
 */
fun Songs.toSongUiStatem(isEntryValid: Boolean = false): SongUiStatem = SongUiStatem(
    songDetailsm = this.toItemDetailsm(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Songs] to [SongDetails]
 */
fun Songs.toItemDetailsm(): SongDetailsm = SongDetailsm(
    id = track_id,
    namem = track_name,
    authorm = artist_name,
    lyricsm = lyrics_body
)
