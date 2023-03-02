package io.github.babiesdev.sample

class TestData(
    val name: String,
    val age: Int,
    val datas: List<String>
) {
    fun print() {
        println("name: $name, age: $age")
    }
}

fun main() {
    println("DEFAULT_BUFFER_SIZE = ${DEFAULT_BUFFER_SIZE}")
}