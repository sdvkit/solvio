package com.sdv.kit.solvio.entity

data class Action(
    val actionId: Long,
    val actionDescription: String,
    val actionResult: String,
    val actionResultImageUrl: String,
    val isPositive: Boolean
) {
    class Builder {
        private var actionId: Long = 0
        private var actionDescription: String = ""
        private var actionResult: String = ""
        private var actionResultImageUrl: String = ""
        private var isPositive: Boolean = false

        fun actionId(actionId: Long) = apply { this.actionId = actionId }
        fun actionDescription(actionDescription: String) = apply { this.actionDescription = actionDescription }
        fun actionResult(actionResult: String) = apply { this.actionResult = actionResult }
        fun actionResultImageUrl(actionResultImageUrl: String) = apply { this.actionResultImageUrl = actionResultImageUrl }
        fun isPositive(isPositive: Boolean) = apply { this.isPositive = isPositive }
        fun build(): Action = Action(actionId, actionDescription, actionResult, actionResultImageUrl, isPositive)
    }
}