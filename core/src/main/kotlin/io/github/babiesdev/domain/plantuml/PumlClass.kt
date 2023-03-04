package io.github.babiesdev.domain.plantuml

class PumlClass(
    private val name: String,
    private val basePackage: PumlPackage,
    private val fields: List<PumlField>,
    private val methods: List<PumlMethod>,
    private val visibility: Visibility = Visibility.PUBLIC,
) {
    fun toPlantUml() = """
            |${visibility.symbol}class ${basePackage.name}.${name} {
            |   ${fields.joinToString(separator = "") { it.toPlantUml() }}
            |   --
            |   ${methods.joinToString(separator = "") { it.toPlantUml() }}
            |}
    """.trimMargin()
}
