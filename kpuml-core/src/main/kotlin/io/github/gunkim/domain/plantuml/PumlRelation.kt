package io.github.gunkim.domain.plantuml

class PumlRelation(
    val target: String,
    val type: RelationType,
) {
    fun toPlantUml(origin: String) = """
        |$origin ${type.symbol} $target
    """.trimMargin()
}
