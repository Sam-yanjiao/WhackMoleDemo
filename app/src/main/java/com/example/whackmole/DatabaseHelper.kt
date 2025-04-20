package com.example.whackmole

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "WhacAMole.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "leaderboard"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "player_name"
        private const val COLUMN_SCORE = "score"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_SCORE INTEGER
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertScore(playerName: String, score: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, playerName)
            put(COLUMN_SCORE, score)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result.toInt() != -1
    }

    fun getLeaderboard(): List<ScoreEntry> {
        val leaderboard = mutableListOf<ScoreEntry>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_SCORE DESC LIMIT 10"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val score = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE))
            leaderboard.add(ScoreEntry(id, name, score))
        }
        cursor.close()
        db.close()
        return leaderboard
    }

    fun clearLeaderboard() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        db.close()
    }
}

data class ScoreEntry(val id: Int, val playerName: String, val score: Int)