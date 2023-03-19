package io.github.gunkim.domain.plantuml

import io.github.gunkim.domain.plantuml.Visibility.PUBLIC

class PumlField(
    private val name: String,
    private val type: String,
    private val visibility: Visibility = PUBLIC,
) {
    fun toPlantUml() = "${visibility.symbol}$name:$type"
}
