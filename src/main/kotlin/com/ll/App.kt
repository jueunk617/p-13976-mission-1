package com.ll

import com.ll.domain.wiseSaying.controller.WiseSayingController
import com.ll.domain.wiseSaying.repository.WiseSayingFileRepository
import com.ll.domain.wiseSaying.service.WiseSayingService
import com.ll.global.rq.Rq

import java.util.Scanner

class App {

    fun run() {
        val sc = Scanner(System.`in`)
        val repository = WiseSayingFileRepository()
        val service = WiseSayingService(repository)
        val controller = WiseSayingController(service, sc)

        println("== 명언 앱 ==")

        while (sc.hasNextLine()) {
            print("명령) ")
            val cmd = sc.nextLine().trim() ?: break
            val rq = Rq(cmd)

            when (rq.action) {
                "종료" -> break
                "등록" -> controller.write()
                "목록" -> controller.list()
                "삭제" -> controller.delete(rq)
                "수정" -> controller.modify(rq)
                "빌드" -> controller.build()
                "초기화" -> controller.clear() // 테스트용

                else -> println("알 수 없는 명령어입니다: ${rq.action}")
            }
        }
    }
}
