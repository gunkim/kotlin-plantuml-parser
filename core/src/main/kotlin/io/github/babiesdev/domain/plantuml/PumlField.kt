package io.github.babiesdev.domain.plantuml

import io.github.babiesdev.domain.plantuml.Visibility.PUBLIC

class PumlField(
    private val name: String,
    private val type: String,
    private val visibility: Visibility = PUBLIC,
) {
    fun toPlantUml() = "${visibility.symbol}$name:$type"
}
