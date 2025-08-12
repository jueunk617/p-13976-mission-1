package com.ll.domain.wiseSaying.repository

import com.ll.domain.wiseSaying.entity.WiseSaying

interface WiseSayingRepository {
    fun findAll(): List<WiseSaying>
    fun save(quote: String, author: String): WiseSaying
    fun deleteById(id: Int): Boolean
    fun exportAll()
    fun clear()
    fun findById(id: Int): WiseSaying?
    fun update(id: Int, quote: String, author: String): Boolean
}