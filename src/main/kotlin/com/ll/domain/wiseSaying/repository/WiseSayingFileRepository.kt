package com.ll.domain.wiseSaying.repository

import com.ll.domain.wiseSaying.entity.WiseSaying
import com.ll.standard.file.WiseSayingFileManager

class WiseSayingFileRepository(
    private val fileManager: WiseSayingFileManager = WiseSayingFileManager()
) : WiseSayingRepository {
    private val wiseSayings = mutableListOf<WiseSaying>()
    private var lastId = 0

    init {
        val (loaded, last) = fileManager.loadAll()
        wiseSayings.addAll(loaded)
        lastId = last
    }

    override fun findAll(): List<WiseSaying> = wiseSayings.toList()

    override fun save(quote: String, author: String): WiseSaying {
        val id = ++lastId
        val newItem = WiseSaying(id, quote, author)
        wiseSayings.add(newItem)
        fileManager.saveOne(newItem)
        fileManager.saveLastId(lastId)
        return newItem
    }

    override fun deleteById(id: Int): Boolean {
        val removed = wiseSayings.removeIf { it.id == id }
        if (removed) fileManager.deleteOne(id)
        return removed
    }

    override fun exportAll() {
        fileManager.saveAll(wiseSayings)
    }

    override fun clear() {
        wiseSayings.clear()
        lastId = 0
        fileManager.clearAll()
    }

    override fun findById(id: Int): WiseSaying? = wiseSayings.find { it.id == id }

    override fun update(id: Int, quote: String, author: String): Boolean {
        val index = wiseSayings.indexOfFirst { it.id == id }
        if (index == -1) return false
        wiseSayings[index] = wiseSayings[index].copy(quote = quote, author = author)
        fileManager.saveOne(wiseSayings[index])
        return true
    }
}