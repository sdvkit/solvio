package com.sdv.kit.solvio.entity

data class Situation(
    val situationId: Long,
    val actorImageUrl: String,
    val actorName: String,
    val situationDescription: String
) {
    class Builder() {
        private var situationId: Long = 0
        private var actorImageUrl: String = ""
        private var actorName: String = ""
        private var situationDescription: String = ""

        fun situationId(situationId: Long) = apply { this.situationId = situationId }
        fun actorImageUrl(actorImageUrl: String) = apply { this.actorImageUrl = actorImageUrl }
        fun actorName(actorName: String) = apply { this.actorName = actorName }
        fun situationDescription(situationDescription: String) = apply { this.situationDescription = situationDescription }
        fun build(): Situation = Situation(situationId, actorImageUrl, actorName, situationDescription)
    }
}