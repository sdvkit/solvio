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
import com.sdv.kit.solvio.entity.Action
import com.sdv.kit.solvio.entity.relation.GameLevelWithSituationsAndActions
import com.sdv.kit.solvio.entity.relation.SituationWithActions
import com.sdv.kit.solvio.util.FirebaseEntityConverterUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _levels = MutableLiveData(listOf<GameLevelWithSituationsAndActions>())
    val levels: LiveData<List<GameLevelWithSituationsAndActions>> = _levels

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
        val levelsWithSituationsAndActions = mutableListOf<GameLevelWithSituationsAndActions>()

        snapshots.forEach { snapshot ->
            val levelMap = snapshot.value as Map<*, *>
            val situationsWithActions = mutableListOf<SituationWithActions>()
            val situations = extractSituations(levelMap)

            situations.forEach { situationMap -> if (situationMap != null) {
                val situation = FirebaseEntityConverterUtil.toSituation(situationMap as Map<*, *>)
                val actions = mutableListOf<Action>()

                extractActions(situationMap).forEach { actionMap -> if (actionMap != null)
                    actions.add(FirebaseEntityConverterUtil.toAction(actionMap as Map<*, *>))
                }

                situationsWithActions.add(SituationWithActions(situation, actions))
            } }

            levelsWithSituationsAndActions.add(GameLevelWithSituationsAndActions(
                FirebaseEntityConverterUtil.toGameLevel(levelMap), situationsWithActions))
        }

        _levels.value = levelsWithSituationsAndActions
    }

    private fun extractSituations(levelMap: Map<*, *>): List<*> = try {
        val tempSituations = levelMap["situations"] ?: arrayListOf<String>()
        (tempSituations as ArrayList<*>).toList()
    } catch (e: ClassCastException) {
        (levelMap["situations"] as HashMap<*, *>).values.toList()
    }

    private fun extractActions(situationMap: Map<*, *>): List<*> = try {
        (situationMap["actions"] as HashMap<*, *>).values.toList()
    } catch (e: ClassCastException) {
        (situationMap["actions"] as ArrayList<*>).toList()
    }
}