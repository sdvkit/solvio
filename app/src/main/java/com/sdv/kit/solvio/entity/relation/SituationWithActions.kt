package com.sdv.kit.solvio.entity.relation

import com.sdv.kit.solvio.entity.Action
import com.sdv.kit.solvio.entity.Situation

data class SituationWithActions(
    val situation: Situation,

    val actions: List<Action>
)