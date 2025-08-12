package com.ll.domain.wiseSaying.repository

import com.ll.global.app.AppConfig
import com.ll.global.bean.SingletonScope
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import java.io.File

class WiseSayingFileRepositoryTest {

    private val repository = SingletonScope.wiseSayingFileRepository

    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            AppConfig.setModeToTest()
        }
    }

    @BeforeEach
    fun setUp() {
        repository.clear()
    }

    @Test
    @DisplayName("save() - 명언 저장 후 파일 생성")
    fun t1() {
        val wiseSaying = repository.save("인생은 속도가 아니라 방향이다", "괴테")

        val file = File("db/wiseSaying/${wiseSaying.id}.json")
        assertThat(file.exists()).isTrue()
    }

    @Test
    @DisplayName("findById() - ID로 명언 조회")
    fun t2() {
        val saved = repository.save("실패는 성공의 어머니", "속담")
        val found = repository.findById(saved.id)

        assertThat(found).isEqualTo(saved)
    }

    @Test
    @DisplayName("findById() - 존재하지 않는 ID로 조회 시 null 반환")
    fun t3() {
        val found = repository.findById(999)
        assertThat(found).isNull()
    }

    @Test
    @DisplayName("findAll() - 모든 명언 조회")
    fun t4() {
        val w1 = repository.save("명언1", "작가1")
        val w2 = repository.save("명언2", "작가2")

        val list = repository.findAll()

        assertThat(list).containsExactly(w1, w2) // 저장 순서 그대로 -> controller에서 역순으로 반환됨
    }

    @Test
    @DisplayName("deleteById() - ID로 명언 삭제")
    fun t5() {
        val saved = repository.save("삭제될 명언", "익명")

        val deleted = repository.deleteById(saved.id)
        val found = repository.findById(saved.id)
        val file = File("db/wiseSaying/${saved.id}.json")

        assertThat(deleted).isTrue()
        assertThat(found).isNull()
        assertThat(file.exists()).isFalse()
    }

    @Test
    @DisplayName("deleteById() - 존재하지 않는 명언 ID 삭제 시 false 반환")
    fun t6() {
        val deleted = repository.deleteById(9999)
        assertThat(deleted).isFalse()
    }

    @Test
    @DisplayName("update() - 명언 수정")
    fun t7() {
        val saved = repository.save("원본 명언", "원작자")

        val updated = repository.update(saved.id, "수정된 명언", "수정자")
        val found = repository.findById(saved.id)

        assertThat(updated).isTrue()
        assertThat(found?.quote).isEqualTo("수정된 명언")
        assertThat(found?.author).isEqualTo("수정자")
    }

    @Test
    @DisplayName("update() - 존재하지 않는 ID 수정 시 false 반환")
    fun t8() {
        val updated = repository.update(999, "아무거나", "누구나")
        assertThat(updated).isFalse()
    }

    @Test
    @DisplayName("exportAll() - 모든 명언을 JSON 파일로 저장")
    fun t9() {
        repository.save("명언1", "작가1")
        repository.save("명언2", "작가2")

        repository.exportAll()

        val dataJson = File("db/wiseSaying/data.json")
        assertThat(dataJson.exists()).isTrue()
        assertThat(dataJson.readText()).contains("명언1", "작가2")
    }

    @Test
    @DisplayName("clear() - 모든 명언 삭제 및 파일 제거")
    fun t10() {
        repository.save("임시 명언", "작가")

        repository.clear()

        val dir = File("db/wiseSaying")
        val files = dir.listFiles { file -> file.extension == "json" } ?: emptyArray()
        val lastIdFile = File(dir, "lastId.txt")

        assertThat(files).isEmpty()
        assertThat(lastIdFile.exists()).isFalse()
    }

}
