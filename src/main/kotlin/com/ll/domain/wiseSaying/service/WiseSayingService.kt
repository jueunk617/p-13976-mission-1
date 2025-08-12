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

    fun findByKeywordWithPaging(
        keywordType: String?,
        keyword: String?,
        page: Int,
        itemsPerPage: Int = 5
    ): Pair<List<WiseSaying>, Int> {
        val filtered = repository.findAll().filter {
            when (keywordType) {
                "content" -> keyword == null || it.quote.contains(keyword)
                "author" -> keyword == null || it.author.contains(keyword)
                else -> true
            }
        }.sortedByDescending { it.id }

        val totalPages = (filtered.size + itemsPerPage - 1) / itemsPerPage
        val startIdx = (page - 1) * itemsPerPage
        val endIdx = (startIdx + itemsPerPage).coerceAtMost(filtered.size)

        val pagedItems = if (startIdx in filtered.indices) {
            filtered.subList(startIdx, endIdx)
        } else {
            emptyList()
        }

        return pagedItems to totalPages
    }

}