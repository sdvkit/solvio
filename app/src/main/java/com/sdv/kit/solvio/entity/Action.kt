package com.sdv.kit.solvio.entity

data class Action(
    val actionId: Long,
    val actionDescription: String,
    val actionResults: String,
    val isPositive: Boolean
)