package io.github.babiesdev.clazz

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.nio.file.Paths

class ClassInfoTest : BehaviorSpec({
    Given("Path를 읽어 ClassInfo를 뽑아온다.") {
        val path = Paths.get("../parser/src/test/kotlin/io/github/babiesdev/sample/TestData.kt")

        When("Parsing ") {
            val result = ClassInfo.fromPath(path)

            Then("ClassInfo를 뽑아온다") {
                result.className shouldBe "TestData"
                result.packages shouldBe "io.github.babiesdev.sample"
                result.methods.size shouldBe 1
                result.fields.size shouldBe 3
                result.imports.size shouldBe 1
            }
        }
    }

})