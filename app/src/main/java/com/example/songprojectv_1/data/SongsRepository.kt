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

package com.example.songprojectv_1.data


import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SongsRepository {

    fun getAllItemsStream(): Flow<List<Songs>>

    fun getSongStream(id: Int): Flow<Songs?>

    suspend fun insertSong(songs: Songs)
  //  suspend fun insertSongm(songs: com.example.songprojectv_1.ui.song.Songs)
    suspend fun deleteSong(songs: Songs)

    suspend fun updateSong(songs: Songs)
    suspend fun getTrackDetail(
        trackName: String,
        lyrics_body: String,
        apiKey: String

    ): Response<MusixmatchResponse>

    suspend fun getDetailSongs(Trackid: Int, apiKey: String): Response<MusicDetail>



}

