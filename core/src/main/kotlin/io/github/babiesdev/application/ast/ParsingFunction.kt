package io.github.babiesdev.application.ast

import io.github.babiesdev.domain.plantuml.PumlField
import io.github.babiesdev.domain.plantuml.PumlMethod
import io.github.babiesdev.domain.plantuml.Visibility

data class ParsingFunction(
    val name: String,
    val parsingParameters: List<ParsingParameter>,
    val returnType: String,
    val visibility: String,
) {
    fun convert() = PumlMethod(
        name = name,
        returnType = returnType,
        parameters = parsingParameters.map { param ->
            PumlField(
                name = param.name,
                type = param.type,
                visibility = Visibility.NONE,
            )
        }
    )
}
