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
import com.sdv.kit.solvio.entity.Situation
import com.sdv.kit.solvio.entity.relation.GameLevelWithSituations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _levels = MutableLiveData(listOf<GameLevelWithSituations>())
    val levels: LiveData<List<GameLevelWithSituations>> = _levels

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
        val levelsWithSituations = mutableListOf<GameLevelWithSituations>()

        snapshots.forEach { snapshot ->
            val levelMap = snapshot.value as Map<*, *>

            val situations = mutableListOf<Situation>()
            (levelMap["situations"] as ArrayList<*>).forEach {
                if (it != null) situations.add(buildSituation(it as Map<*, *>))
            }

            levelsWithSituations.add(GameLevelWithSituations(buildGameLevel(levelMap), situations))
        }

        _levels.value = levelsWithSituations
    }

    private fun buildSituation(situationMap: Map<*, *>): Situation = Situation.Builder()
        .situationId(situationMap["situationId"].toString().toLong())
        .actorName(situationMap["actorName"].toString())
        .actorImageUrl(situationMap["actorImageUrl"].toString())
        .situationDescription(situationMap["situationDescription"].toString())
        .build()

    private fun buildGameLevel(levelMap: Map<*, *>): GameLevel = GameLevel.Builder()
        .levelName(levelMap["levelName"].toString())
        .backgroundImageUrl(levelMap["backgroundImageUrl"].toString())
        .levelDescription(levelMap["levelDescription"].toString())
        .situationsCount((levelMap["situations"] as ArrayList<*>).size - 1)
        .build()
}