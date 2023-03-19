package io.github.gunkim.domain.plantuml

import io.github.gunkim.domain.plantuml.Visibility.PUBLIC

class PumlMethod(
    private val name: String,
    private val returnType: String = "Unit",
    private val parameters: List<PumlField>,
    private val visibility: Visibility = PUBLIC,
) {
    fun toPlantUml() =
        "${visibility.symbol}fun $name(${parameters.joinToString(separator = ", ") { it.toPlantUml() }})" +
            "${if (returnType != "Unit" && returnType.isNotBlank()) ": $returnType" else ""}"
}
