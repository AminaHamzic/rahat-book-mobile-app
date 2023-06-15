package com.example.moodapp.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.moodapp.Note
import com.example.moodapp.data.Event
import com.example.moodapp.data.Mood


class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery =
            "CREATE TABLE ${CalenderData.EventEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${CalenderData.EventEntry.COLUMN_NAME_DATE} TEXT," +
                    "${CalenderData.EventEntry.COLUMN_NAME_EVENT_TITLE} TEXT,"  +
                    "${CalenderData.EventEntry.COLUMN_NAME_MOOD} INTEGER)"
        db?.execSQL(createQuery)
        // Create a new table for the notes
        val createNotesQuery =
            "CREATE TABLE ${NotesData.NotesEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${NotesData.NotesEntry.COLUMN_NAME_TITLE} TEXT," +
                    "${NotesData.NotesEntry.COLUMN_NAME_DESCRIPTION} TEXT)"
        db?.execSQL(createNotesQuery)
    }



    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val deleteQuery = "DROP TABLE IF EXISTS ${CalenderData.EventEntry.TABLE_NAME}"
        db?.execSQL(deleteQuery)
        onCreate(db)
    }


    fun addEvent(date: String, event: Event) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(CalenderData.EventEntry.COLUMN_NAME_DATE, date)
            put(CalenderData.EventEntry.COLUMN_NAME_EVENT_TITLE, event.name)
            put(CalenderData.EventEntry.COLUMN_NAME_MOOD, event.mood.ordinal)
        }
        db?.insert(CalenderData.EventEntry.TABLE_NAME, null, values)
    }

    fun fetchAllMoods(): List<Mood> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + CalenderData.EventEntry.TABLE_NAME, null)

        val moods = mutableListOf<Mood>()
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                moods.add(
                    Mood.values()[cursor.getInt(3)]
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return moods
    }

    fun fetchEventsFromDate(date: String): List<Event> {
        val db = this.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID,
            CalenderData.EventEntry.COLUMN_NAME_DATE,
            CalenderData.EventEntry.COLUMN_NAME_EVENT_TITLE,
            CalenderData.EventEntry.COLUMN_NAME_MOOD
        )
        val selection = "${CalenderData.EventEntry.COLUMN_NAME_DATE} = ?"
        val selectionArgs = arrayOf(date)
        val sortOrder = "${CalenderData.EventEntry.COLUMN_NAME_EVENT_TITLE} DESC"
        val cursor = db.query(
            CalenderData.EventEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
        val events = mutableListOf<Event>()
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                events.add(
                    Event(
                        cursor.getString(2),
                        Mood.values()[cursor.getInt(3)]
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return events
    }
fun addNote(note: Note) {
    val db = this.writableDatabase
    val values = ContentValues().apply {
        put(NotesData.NotesEntry.COLUMN_NAME_TITLE, note.title)
        put(NotesData.NotesEntry.COLUMN_NAME_DESCRIPTION, note.description)
    }
    db?.insert(NotesData.NotesEntry.TABLE_NAME, null, values)
}

@SuppressLint("Range")
fun fetchAllNotes(): List<Note> {
    val db = this.readableDatabase
    val cursor = db.rawQuery("SELECT * FROM " + NotesData.NotesEntry.TABLE_NAME, null)

    val notes = mutableListOf<Note>()
    if (cursor.moveToFirst()) {
        do {
            notes.add(
                Note(
                    cursor.getString(cursor.getColumnIndex(NotesData.NotesEntry.COLUMN_NAME_TITLE)),
                    cursor.getString(cursor.getColumnIndex(NotesData.NotesEntry.COLUMN_NAME_DESCRIPTION))
                )
            )
        } while (cursor.moveToNext())
    }
    cursor.close()
    return notes
}


    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"
    }
}
object NotesData {
    object NotesEntry : BaseColumns {
        const val TABLE_NAME = "notes"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_DESCRIPTION = "description"
    }
}

object CalenderData {
    object EventEntry : BaseColumns {
        const val TABLE_NAME = "calender"
        const val COLUMN_NAME_DATE = "date"
        const val COLUMN_NAME_EVENT_TITLE = "event"
        const val COLUMN_NAME_MOOD = "mood"
    }
}
