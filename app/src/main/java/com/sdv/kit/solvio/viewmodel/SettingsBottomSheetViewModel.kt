package com.sdv.kit.solvio.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsBottomSheetViewModel(application: Application) : AndroidViewModel(application) {
    private val _editLevelsKey = MutableLiveData<String>()
    val editLevelsKey: LiveData<String> = _editLevelsKey

    fun getEditLevelsKey() = CoroutineScope(Dispatchers.IO).launch {
        Firebase.database.getReference("edit_levels_key/").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    _editLevelsKey.value = snapshot.value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) { }
        })
    }
}