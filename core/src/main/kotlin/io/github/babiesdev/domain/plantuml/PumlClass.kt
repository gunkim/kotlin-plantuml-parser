package io.github.babiesdev.domain.plantuml

class PumlClass(
    private val name: String,
    private val type: PumlClassType = PumlClassType.CLASS,
    private val basePackage: PumlPackage,
    private val fields: List<PumlField>,
    private val methods: List<PumlMethod>,
    private val relation: PumlRelation? = null,
    private val visibility: Visibility = Visibility.PUBLIC,
) {
    fun toPlantUml() = """
            |${visibility.symbol}${type.symbol} ${basePackage.name}.${name} {
            |  ${fields.joinToString(separator = "\n  ") { it.toPlantUml() }}
            |  --
            |  ${methods.joinToString(separator = "\n  ") { it.toPlantUml() }}
            |}
            |${relation?.toPlantUml("${basePackage.name}.${name}") ?: ""}
    """.trimMargin()
}
