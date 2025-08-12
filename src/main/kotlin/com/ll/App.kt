package com.ll

import java.util.Scanner

data class WiseSaying(
    val id: Int,
    val quote: String,
    val author: String
)

class App {
    private val wiseSayings = mutableListOf<WiseSaying>()
    private var lastId = 0

    fun run() {
        val sc = Scanner(System.`in`)
        println("== 명언 앱 ==")

        while (true) {
            print("명령) ")
            val cmd = sc.nextLine().trim()
            val rq = Rq(cmd)

            when (rq.action) {
                "종료" -> return

                "등록" -> {
                    print("명언 : ")
                    val quote = sc.nextLine().trim()

                    print("작가 : ")
                    val author = sc.nextLine().trim()

                    val id = ++lastId
                    wiseSayings.add(WiseSaying(id, quote, author))

                    println("${id}번 명언이 등록되었습니다.")
                }

                "목록" -> {
                    println("번호 / 작가 / 명언")
                    println("----------------------")
                    wiseSayings
                        .sortedByDescending { it.id }
                        .forEach { println("${it.id} / ${it.author} / ${it.quote}") }
                }

                "삭제" -> {
                    val id = rq.getParamValueAsInt("id", 0)
                    if (id == 0) {
                        println("id를 정확히 입력해주세요.")
                        continue
                    }

                    val removed = wiseSayings.removeIf { it.id == id }
                    if (removed) {
                        println("${id}번 명언이 삭제되었습니다.")
                    } else {
                        println("${id}번 명언은 존재하지 않습니다.")
                    }
                }

                "수정" -> {
                    val id = rq.getParamValueAsInt("id", 0)
                    if (id == 0) {
                        println("id를 정확히 입력해주세요.")
                        continue
                    }

                    val index = wiseSayings.indexOfFirst { it.id == id }
                    if (index == -1) {
                        println("${id}번 명언은 존재하지 않습니다.")
                        continue
                    }

                    val current = wiseSayings[index]

                    println("명언(기존) : ${current.quote}")
                    print("명언 : ")
                    val newQuote = sc.nextLine().trim()

                    println("작가(기존) : ${current.author}")
                    print("작가 : ")
                    val newAuthor = sc.nextLine().trim()

                    wiseSayings[index] = current.copy(
                        quote = newQuote,
                        author = newAuthor
                    )
                }
            }
        }
    }
}