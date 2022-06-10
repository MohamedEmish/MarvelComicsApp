package com.amosh.local.source

import com.amosh.common.Mapper
import com.amosh.data.model.ComicsDTO
import com.amosh.data.repository.LocalDataSource
import com.amosh.local.database.ComicsDAO
import com.amosh.local.model.ComicsLocalModel
import javax.inject.Inject


/**
 * Implementation of [LocalDataSource] Source
 */
class LocalDataSourceImp @Inject constructor(
    private val comicsDAO: ComicsDAO,
    private val comicsMapper: Mapper<ComicsLocalModel, ComicsDTO>
) : LocalDataSource {

    override suspend fun addItem(comics: ComicsDTO): Long {
        val comicsLocalModel = comicsMapper.to(comics)
        return comicsDAO.addComicsItem(comics = comicsLocalModel)
    }

    override suspend fun getItemByTitle(title: String): ComicsDTO {
        val comicsLocalModel = comicsDAO.getComicsItemByTitle(title = title)
        return comicsMapper.from(comicsLocalModel)
    }

    override suspend fun getItemById(comicsId: String): ComicsDTO {
        val comicsLocalModel = comicsDAO.getComicsItemById(id = comicsId)
        return comicsMapper.from(comicsLocalModel)
    }

    override suspend fun addItems(comics: List<ComicsDTO>): List<Long> {
        val comicsLocalList = comicsMapper.toList(comics)
        return comicsDAO.addComicsItems(ComicsList = comicsLocalList)
    }

    override suspend fun getItems(): List<ComicsDTO> {
        val comicsLocalList = comicsDAO.getComicsItems()
        return comicsMapper.fromList(comicsLocalList)
    }
    
    override suspend fun updateItem(comics: ComicsDTO): Int {
        val comicsLocalModel = comicsMapper.to(comics)
        return comicsDAO.updateComicsItem(comics = comicsLocalModel)
    }

    override suspend fun deleteItemById(id: Int): Int {
        return comicsDAO.deleteComicsItemById(id = id)
    }

    override suspend fun deleteItem(comics: ComicsDTO): Int {
        val comicsLocalModel = comicsMapper.to(comics)
        return comicsDAO.deleteComicsItem(comics = comicsLocalModel)
    }

    override suspend fun clearCachedItems(): Int {
        return comicsDAO.clearCachedComicsItems()
    }
}