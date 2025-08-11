package com.ll

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AppTest {

    fun runApp(input: String): String {
        val originalIn = System.`in`
        val originalOut = System.out

        try {
            val inputStream = input.byteInputStream()
            System.setIn(inputStream)

            val outputStream = java.io.ByteArrayOutputStream()
            System.setOut(java.io.PrintStream(outputStream))

            App().run()

            return outputStream.toString()
                .replace("\r\n", "\n")
                .trim()
        } finally {
            System.setIn(originalIn)
            System.setOut(originalOut)
        }
    }

    @Test
    @DisplayName("앱 시작")
    fun t1() {
        val input = "종료\n"
        val output = runApp(input)

        assertTrue(output.contains("== 명언 앱 =="))
    }

    @Test
    @DisplayName("등록 - 명언, 작가")
    fun t2() {
        val input = """
            등록
            현재를 사랑하라.
            작자미상
            종료
        """.trimIndent()

        val output = runApp(input)

        assertTrue(output.contains("명언 :"))
        assertTrue(output.contains("작가 :"))
    }

    @Test
    @DisplayName("등록 - '등록되었습니다.' 출력")
    fun t3() {
        val input = """
            등록
            현재를 사랑하라.
            작자미상
            종료
        """.trimIndent()

        val output = runApp(input)

        assertTrue(output.contains("1번 명언이 등록되었습니다."))
    }

    @Test
    @DisplayName("등록 - 번호 증가")
    fun t4() {
        val input = """
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            종료
        """.trimIndent()

        val output = runApp(input)

        assertTrue(output.contains("1번 명언이 등록되었습니다."))
        assertTrue(output.contains("2번 명언이 등록되었습니다."))
    }

    @Test
    @DisplayName("목록 - 최신순 출력")
    fun t5() {
        val input = """
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            목록
            종료
        """.trimIndent()

        val output = runApp(input)

        val expected = """
            번호 / 작가 / 명언
            ----------------------
            2 / 작자미상 / 과거에 집착하지 마라.
            1 / 작자미상 / 현재를 사랑하라.
        """.trimIndent()

        assertTrue(output.contains(expected))
    }

    @Test
    @DisplayName("삭제 - 1번 명언 삭제")
    fun t6() {
        val input = """
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            목록
            삭제?id=1
            목록
            종료
        """.trimIndent()

        val output = runApp(input)
            .replace("\r\n", "\n")

        assertTrue(output.contains("1번 명언이 삭제되었습니다."))

        val afterSecondList = """
            번호 / 작가 / 명언
            ----------------------
            2 / 작자미상 / 과거에 집착하지 마라.
        """.trimIndent()
        assertTrue(output.contains(afterSecondList))
    }

}