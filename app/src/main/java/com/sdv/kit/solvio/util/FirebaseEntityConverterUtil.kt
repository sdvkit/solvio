package com.sdv.kit.solvio.util

import com.sdv.kit.solvio.entity.Action
import com.sdv.kit.solvio.entity.GameLevel
import com.sdv.kit.solvio.entity.Situation

class FirebaseEntityConverterUtil private constructor() {
    companion object {
        fun toAction(actionMap: Map<*, *>): Action = Action.Builder()
            .actionId(actionMap["actionId"].toString().toLong())
            .actionDescription(actionMap["actionDescription"].toString())
            .actionResult(actionMap["actionResult"].toString())
            .actionResultImageUrl(actionMap["actionResultImageUrl"].toString())
            .isPositive(actionMap["isPositive"].toString().toBoolean())
            .build()

        fun toSituation(situationMap: Map<*, *>): Situation = Situation.Builder()
            .situationId(situationMap["situationId"].toString().toLong())
            .actorName(situationMap["actorName"].toString())
            .actorImageUrl(situationMap["actorImageUrl"].toString())
            .situationDescription(situationMap["situationDescription"].toString())
            .build()

        fun toGameLevel(levelMap: Map<*, *>): GameLevel {
            val situations = extractSituations(levelMap).filterNotNull()
            return GameLevel.Builder()
                .levelName(levelMap["levelName"].toString())
                .backgroundImageUrl(levelMap["backgroundImageUrl"].toString())
                .levelDescription(levelMap["levelDescription"].toString())
                .situationsCount(if (situations.isEmpty()) 0 else situations.size)
                .build()
        }

        private fun extractSituations(levelMap: Map<*, *>): List<*> = try {
            val tempSituations = levelMap["situations"] ?: arrayListOf<String>()
            (tempSituations as ArrayList<*>).toList()
        } catch (e: ClassCastException) {
            (levelMap["situations"] as HashMap<*, *>).values.toList()
        }
    }
}