package com.ll.domain.wiseSaying.repository

import com.ll.domain.wiseSaying.entity.WiseSaying

class WiseSayingMemoryRepository : WiseSayingRepository {
    private val wiseSayings = mutableListOf<WiseSaying>()
    private var lastId = 0

    override fun findAll(): List<WiseSaying> = wiseSayings.toList()

    override fun save(quote: String, author: String): WiseSaying {
        val id = ++lastId
        val newItem = WiseSaying(id, quote, author)
        wiseSayings.add(newItem)
        return newItem
    }

    override fun deleteById(id: Int): Boolean {
        return wiseSayings.removeIf { it.id == id }
    }

    override fun exportAll() {
        // 메모리 버전은 아무것도 안 해도 됨
    }

    override fun clear() {
        wiseSayings.clear()
        lastId = 0
    }

    override fun findById(id: Int): WiseSaying? = wiseSayings.find { it.id == id }

    override fun update(id: Int, quote: String, author: String): Boolean {
        val index = wiseSayings.indexOfFirst { it.id == id }
        if (index == -1) return false
        wiseSayings[index] = wiseSayings[index].copy(quote = quote, author = author)
        return true
    }
}