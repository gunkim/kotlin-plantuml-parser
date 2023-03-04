package io.github.babiesdev.clazz

class FieldInfo(
    val accessor: Accessor,
    val changeable: Changeable,
    val name: String,
    val type: String,
) {

    companion object {
        fun isField(values: String): FieldInfo {
            val withAccessor = Regex("""(private|public|protected)?\s+(val|var)\s+\w+\s*:\s*\w+""")
            val singleness = Regex("""(val|var)\s+\w+\s*:\s*\w+""")

            val valueList = values.split(" ")

            return when {
                withAccessor.matches(values) -> {
                    valueList.let { FieldInfo(accessor(it[0]), changeable(it[1]), it[2], it[3]) }
                }

                singleness.matches(values) -> {
                    valueList.let { FieldInfo(Accessor.NONE, changeable(it[0]), it[1], it[2]) }
                }

                else -> throw IllegalArgumentException("The value is not a field")
            }

        }

        private fun changeable(chageable: String): Changeable {
            return when (chageable) {
                "val" -> Changeable.VAL
                "var" -> Changeable.VAR
                else -> throw IllegalArgumentException("Changeable must be val or var")
            }
        }

        private fun accessor(accessor: String): Accessor {
            return when (accessor) {
                "private" -> Accessor.PRIVATE
                "public" -> Accessor.PUBLIC
                "protected" -> Accessor.PROTECTED
                else -> Accessor.NONE
            }
        }
    }

    enum class Accessor {
        PRIVATE, PUBLIC, PROTECTED, NONE
    }

    enum class Changeable {
        VAR, VAL
    }
}