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

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SongsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(songs: Songs)
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertm(songs: Songs)
    @Update
    suspend fun update(songs: Songs)

    @Delete
    suspend fun delete(songs: Songs)

    @Query("SELECT * from songs WHERE track_id = :track_id")
    fun getSong(track_id: Int): Flow<Songs>

    @Query("SELECT * from songs ORDER BY track_name ASC")
    fun getAllSongs(): Flow<List<Songs>>

}
