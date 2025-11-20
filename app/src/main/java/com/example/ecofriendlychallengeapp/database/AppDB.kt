package com.example.ecofriendlychallengeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ecofriendlychallengeapp.data.dao.ChallengeDao
import com.example.ecofriendlychallengeapp.data.model.ChallengeLogEntry

// This database consists of one table which lists ChallengeLogEntry entities in an array
@Database(entities = [ChallengeLogEntry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // Accessing the DAO
    abstract fun challengeDao(): ChallengeDao

    companion object {

        // @Volatile ensures that the instance is always up-to-date and visible to other threads
        @Volatile private var INSTANCE: AppDatabase? = null

        // Returning an instance of the database
        fun getDatabase(context: Context): AppDatabase {

            // Checking if an instance already exists:
            return INSTANCE ?: synchronized(this) {

                // If not, building a new database instance:
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "challenge_db" // Name of the database file
                ).build()

                // Storing an instance:
                INSTANCE = db
                db
            }
        }
    }
}
