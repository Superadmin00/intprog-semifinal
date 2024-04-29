package com.intprog.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*

class NotesListActivity : AppCompatActivity() {
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var notesList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)

        notesList = findViewById(R.id.notesList)

        notesAdapter = NotesAdapter(noteItem)
        notesList.layoutManager = LinearLayoutManager(this)
        notesList.adapter = notesAdapter

        notesAdapter.onItemClick = { clickedNote, position ->
            val intent = Intent(this, NotesEditActivity::class.java)
            intent.putExtra("EXTRA_NOTE_TITLE", clickedNote.title)
            intent.putExtra("EXTRA_NOTE_CONTENT", clickedNote.content)
            intent.putExtra("EXTRA_NOTE_POSITION", position)
            startActivity(intent)
        }

        val btnAddItem: ImageButton = findViewById(R.id.btnAddItem)
        btnAddItem.setOnClickListener {
            val intent = Intent(this, NotesEditActivity::class.java)
            startActivity(intent)
        }
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                noteItem.removeAt(position)
                notesAdapter.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(notesList)
    }

    companion object {
        var noteItem = ArrayList<NoteDataClass>()
    }

    override fun onResume() {
        super.onResume()
        notesAdapter.notifyDataSetChanged()
    }
}