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

        fun toGameLevel(levelMap: Map<*, *>): GameLevel = GameLevel.Builder()
            .levelName(levelMap["levelName"].toString())
            .backgroundImageUrl(levelMap["backgroundImageUrl"].toString())
            .levelDescription(levelMap["levelDescription"].toString())
            .situationsCount((levelMap["situations"] as ArrayList<*>).size - 1)
            .build()
    }
}