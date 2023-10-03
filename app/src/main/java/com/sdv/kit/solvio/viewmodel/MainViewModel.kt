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
import com.sdv.kit.solvio.entity.GameLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _levels = MutableLiveData(listOf<GameLevel>())
    val levels: LiveData<List<GameLevel>> = _levels

    fun getLevels() = CoroutineScope(Dispatchers.IO).launch {
        Firebase.database.getReference("levels/").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    handleSnapshots(snapshot.children.toList())
                }
            }

            override fun onCancelled(error: DatabaseError) { }
        })
    }

    private fun handleSnapshots(snapshots: List<DataSnapshot>) {
        _levels.value = List(snapshots.size) { index: Int ->
            val levelMap = snapshots[index].value as Map<*, *>
            buildGameLevel(levelMap)
        }
    }

    private fun buildGameLevel(levelMap: Map<*, *>): GameLevel = GameLevel.Builder()
        .levelName(levelMap["levelName"].toString())
        .backgroundImageUrl(levelMap["backgroundImageUrl"].toString())
        .levelDescription(levelMap["levelDescription"].toString())
        .situationsCount((levelMap["situations"] as ArrayList<*>).size - 1)
        .build()
}