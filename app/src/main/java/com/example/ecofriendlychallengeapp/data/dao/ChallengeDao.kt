package com.example.ecofriendlychallengeapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecofriendlychallengeapp.data.model.ChallengeLogEntry

@Dao
interface ChallengeDao {

    // --- Inserting ---
    // Room inserts each passed ChallengeLogEntry entity instance into the table in the DB.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: ChallengeLogEntry)

    // --- Retrieving by descending order ---
    // Room sorts all entries by entry date in descending order and then retrieves them all
    @Query("SELECT * FROM challenge_log_entries ORDER BY date DESC")
    fun getAllEntries(): LiveData<List<ChallengeLogEntry>>

    // --- Deleting ---
    // Room finds a challenge entry by ID and deletes it
    @Query("DELETE FROM challenge_log_entries WHERE challenge = :challenge")
    suspend fun deleteEntry(challenge: String)

    // --- Checking if exist ---
    // Room checks whether a specific entry exists
    @Query("SELECT * FROM challenge_log_entries WHERE challenge = :challenge AND date = :date LIMIT 1")
    suspend fun getEntryBasedOnDate(challenge: String, date: String): ChallengeLogEntry?
}