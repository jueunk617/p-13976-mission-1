package com.ll.domain.wiseSaying.controller

import com.ll.domain.wiseSaying.service.WiseSayingService
import com.ll.global.rq.Rq
import java.util.Scanner

class WiseSayingController(
    private val service: WiseSayingService,
    private val sc: Scanner
) {
    fun write() {
        print("명언 : ")
        val quote = sc.nextLine().trim()

        print("작가 : ")
        val author = sc.nextLine().trim()

        val saved = service.write(quote, author)
        println("${saved.id}번 명언이 등록되었습니다.")
    }

    fun list(rq: Rq) {
        val keywordType = rq.getParam("keywordType")
        val keyword = rq.getParam("keyword")

        val items = service.findByKeyword(keywordType, keyword)
            .sortedByDescending { it.id }

        if (!keyword.isNullOrBlank()) {
            println("----------------------")
            println("검색타입 : $keywordType")
            println("검색어 : $keyword")
        }

        println("----------------------")
        println("번호 / 작가 / 명언")
        println("----------------------")
        items.forEach {
            println("${it.id} / ${it.author} / ${it.quote}")
        }
    }

    fun delete(rq: Rq) {
        val id = rq.getParamAsInt("id", 0)
        if (id == 0) {
            println("id를 정확히 입력해주세요.")
            return
        }

        val removed = service.delete(id)
        if (removed) {
            println("${id}번 명언이 삭제되었습니다.")
        } else {
            println("${id}번 명언은 존재하지 않습니다.")
        }
    }

    fun modify(rq: Rq) {
        val id = rq.getParamAsInt("id", 0)
        if (id == 0) {
            println("id를 정확히 입력해주세요.")
            return
        }

        val found = service.findById(id)
        if (found == null) {
            println("${id}번 명언은 존재하지 않습니다.")
            return
        }

        println("명언(기존) : ${found.quote}")
        print("명언 : ")
        val newQuote = sc.nextLine().trim()

        println("작가(기존) : ${found.author}")
        print("작가 : ")
        val newAuthor = sc.nextLine().trim()

        val updated = service.update(id, newQuote, newAuthor)
        if (updated) println("${id}번 명언이 수정되었습니다.")
    }

    fun build() {
        service.exportAll()
        println("data.json 파일의 내용이 갱신되었습니다.")
    }

    fun clear() {
        service.clear()
        println("모든 데이터가 초기화되었습니다.")
    }

}