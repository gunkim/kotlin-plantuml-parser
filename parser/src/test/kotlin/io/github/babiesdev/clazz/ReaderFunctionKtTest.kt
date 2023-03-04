package io.github.babiesdev.clazz

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import java.nio.file.Paths

class ReaderFunctionKtTest : BehaviorSpec({

    Given("파일경로") {
        val path = Paths.get("../parser/src/test/kotlin/io/github/babiesdev/sample/TestData.kt")

        When("경로를 세팅하여 글자로 변환") {
            val result = pathToStrings(path)

            Then("빈값이 튀어나와선 안된다") {
                result shouldNotBe ""
            }
        }

    }
})