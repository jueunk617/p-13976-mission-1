package com.ll.domain.wiseSaying.controller

import com.ll.TestRunner
import com.ll.global.bean.SingletonScope
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

class WiseSayingControllerTest {

    @BeforeEach
    fun setUp() {
        SingletonScope.wiseSayingRepository.clear()
    }

    @Test
    @DisplayName("등록")
    fun t1() {
        val result = TestRunner.run("""
            등록
            인생은 짧고 예술은 길다.
            히포크라테스
        """.trimIndent())

        assertThat(result).contains("명언 :")
        assertThat(result).contains("작가 :")
        assertThat(result).contains("1번 명언이 등록되었습니다.")
    }

    @Test
    @DisplayName("목록 - 최신순")
    fun t2() {
        val result = TestRunner.run("""
            등록
            과거에 집착하지 마라.
            작자미상 
            등록
            현재를 사랑하라.
            작자미상 
            목록
        """.trimIndent())

        val expected = """
            번호 / 작가 / 명언
            ----------------------
            2 / 작자미상 / 현재를 사랑하라.
            1 / 작자미상 / 과거에 집착하지 마라.
        """.trimIndent()

        assertThat(result).contains(expected)
    }

    @Test
    @DisplayName("삭제")
    fun t3_1() {
        val result = TestRunner.run("""
            등록
            삭제될 명언
            작가A
            등록
            유지될 명언
            작가B
            삭제?id=1
            목록
        """.trimIndent())

        assertThat(result).contains("1번 명언이 삭제되었습니다.")
        assertThat(result).contains("2 / 작가B / 유지될 명언")
        assertThat(result).doesNotContain("1 / 작가A / 삭제될 명언")
    }

    @Test
    @DisplayName("삭제 - 존재하지 않는 ID 삭제 시")
    fun t3_2() {
        val result = TestRunner.run("""
            삭제?id=999
        """.trimIndent())

        assertThat(result).contains("999번 명언은 존재하지 않습니다.")
    }

    @Test
    @DisplayName("수정")
    fun t4_1() {
        val result = TestRunner.run("""
            등록
            원래 명언
            원래 작가
            수정?id=1
            바뀐 명언
            바뀐 작가
            목록
        """.trimIndent())

        assertThat(result).contains("명언(기존) : 원래 명언")
        assertThat(result).contains("작가(기존) : 원래 작가")
        assertThat(result).contains("1 / 바뀐 작가 / 바뀐 명언")
    }

    @Test
    @DisplayName("수정 - 존재하지 않는 ID 수정 시")
    fun t4_2() {
        val result = TestRunner.run("""
            수정?id=999
        """.trimIndent())

        assertThat(result).contains("999번 명언은 존재하지 않습니다.")
    }

    @Test
    @DisplayName("빌드")
    fun t5() {
        val result = TestRunner.run("""
            등록
            빌드를 테스트합니다.
            테스트러
            빌드
        """.trimIndent())

        assertThat(result).contains("data.json 파일의 내용이 갱신되었습니다.")
    }

    @Test
    @DisplayName("검색 - keywordType=content, keyword=과거")
    fun t6_1() {
        val result = TestRunner.run("""
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            목록?keywordType=content&keyword=과거
        """.trimIndent())

        assertThat(result).contains("검색타입 : content")
        assertThat(result).contains("검색어 : 과거")
        assertThat(result).contains("2 / 작자미상 / 과거에 집착하지 마라.")
        assertThat(result).doesNotContain("1 / 작자미상 / 현재를 사랑하라.")
    }

    @Test
    @DisplayName("검색 - keywordType=author, keyword=작자")
    fun t6_2() {
        val result = TestRunner.run("""
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            목록?keywordType=author&keyword=작자
        """.trimIndent())

        assertThat(result).contains("검색타입 : author")
        assertThat(result).contains("검색어 : 작자")
        assertThat(result).contains("2 / 작자미상 / 과거에 집착하지 마라.")
        assertThat(result).contains("1 / 작자미상 / 현재를 사랑하라.")
    }

}