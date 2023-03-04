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

            when {
                withAccessor.matches(values) -> {
                    val split = values.split(" ")
                    val accessor = when (split[0]) {
                        "private" -> Accessor.PRIVATE
                        "public" -> Accessor.PUBLIC
                        "protected" -> Accessor.PROTECTED
                        else -> Accessor.NONE
                    }
                    val changeable = when (split[1]) {
                        "val" -> Changeable.VAL
                        "var" -> Changeable.VAR
                        else -> throw IllegalArgumentException("Changeable must be val or var")
                    }
                    val name = split[2]
                    val type = split[3]
                    return FieldInfo(accessor, changeable, name, type)
                }

                singleness.matches(values) -> {
                    val split = values.split(" ")
                    val accessor = Accessor.NONE
                    val changeable = when (split[0]) {
                        "val" -> Changeable.VAL
                        "var" -> Changeable.VAR
                        else -> throw IllegalArgumentException("Changeable must be val or var")
                    }
                    val name = split[1]
                    val type = split[2]
                    return FieldInfo(accessor, changeable, name, type)
                }
            }
            throw IllegalArgumentException("The value is not a field")
        }
    }

    enum class Accessor {
        PRIVATE, PUBLIC, PROTECTED, NONE
    }

    enum class Changeable {
        VAR, VAL
    }
}