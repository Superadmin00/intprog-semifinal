package com.intprog.notesapp

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferencesManager(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(context,
        "encrypted_preferences",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveNotes(notes: ArrayList<NoteDataClass>) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(notes)
        editor.putString("notes", json)
        editor.apply()
    }

    fun getNotes(): ArrayList<NoteDataClass> {
        val gson = Gson()
        val json = sharedPreferences.getString("notes", "") ?: ""
        val type = object : TypeToken<ArrayList<NoteDataClass>>() {}.type
        return gson.fromJson(json, type) ?: ArrayList()
    }
}
