package com.ll.domain.wiseSaying.service

import com.ll.domain.wiseSaying.entity.WiseSaying
import com.ll.domain.wiseSaying.repository.WiseSayingRepository

class WiseSayingService(
    private val repository: WiseSayingRepository
) {
    fun write(quote: String, author: String): WiseSaying {
        return repository.save(quote, author)
    }

    fun findAll(): List<WiseSaying> {
        return repository.findAll()
    }

    fun delete(id: Int): Boolean {
        return repository.deleteById(id)
    }

    fun findById(id: Int): WiseSaying? {
        return repository.findById(id)
    }

    fun update(id: Int, quote: String, author: String): Boolean {
        return repository.update(id, quote, author)
    }

    fun exportAll() {
        repository.exportAll()
    }

    fun clear() {
        repository.clear()
    }

}