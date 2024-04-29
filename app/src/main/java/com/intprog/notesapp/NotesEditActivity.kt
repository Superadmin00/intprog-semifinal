package com.intprog.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class NotesEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_edit)

        val noteTitle: EditText = findViewById(R.id.noteTitle)
        val noteContent: EditText = findViewById(R.id.noteContent)
        val btnSaveNote: Button = findViewById(R.id.btnSaveNote)

        // Get the passed note data
        val title = intent.getStringExtra("EXTRA_NOTE_TITLE")
        val content = intent.getStringExtra("EXTRA_NOTE_CONTENT")
        val position = intent.getIntExtra("EXTRA_NOTE_POSITION", -1)

        // Populate the EditTexts
        noteTitle.setText(title)
        noteContent.setText(content)

        btnSaveNote.setOnClickListener {
            val updatedTitle = noteTitle.text.toString()
            val updatedContent = noteContent.text.toString()

            if (updatedTitle.isNotEmpty() || updatedContent.isNotEmpty()) {
                val updatedNote = NoteDataClass(updatedTitle.ifEmpty { "No Title" }, updatedContent)
                if (position != -1) {
                    // Update the note in the list
                    NotesListActivity.noteItem[position] = updatedNote
                } else {
                    // Add a new note to the list
                    NotesListActivity.noteItem.add(updatedNote)
                }

                val intent = Intent(this, NotesListActivity::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext, "Note Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
