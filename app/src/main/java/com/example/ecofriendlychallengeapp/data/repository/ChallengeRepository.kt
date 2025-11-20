package com.example.ecofriendlychallengeapp.data.repository

import androidx.lifecycle.LiveData
import com.example.ecofriendlychallengeapp.data.dao.ChallengeDao
import com.example.ecofriendlychallengeapp.data.model.ChallengeLogEntry

class ChallengeRepository(private val dao: ChallengeDao) {

    // Exposing the observable data source
    val allEntries: LiveData<List<ChallengeLogEntry>> = dao.getAllEntries()

    // Inserting a new challenge entry to the database
    suspend fun insertEntry(entry: ChallengeLogEntry) {
        dao.insertEntry(entry)
    }

    // Deleting a challenge entry
    suspend fun deleteEntry(challenge: String) {
        dao.deleteEntry(challenge)
    }

    // Checking if an entry has already been logged today
    suspend fun checkIfEntryLoggedToday(challenge: String, date: String): Boolean {
        return dao.getEntryBasedOnDate(challenge, date) != null
    }
}