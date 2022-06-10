package com.amosh.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.amosh.local.model.ComicsLocalModel

@Dao
interface ComicsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addComicsItem(comics: ComicsLocalModel): Long

    @Query("SELECT * FROM comics WHERE title = :title")
    suspend fun getComicsItemByTitle(title: String): ComicsLocalModel

    @Query("SELECT * FROM comics WHERE comicId = :id")
    suspend fun getComicsItemById(id: String): ComicsLocalModel

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addComicsItems(ComicsList: List<ComicsLocalModel>): List<Long>

    @Query("SELECT * FROM comics")
    suspend fun getComicsItems(): List<ComicsLocalModel>

    @Update
    suspend fun updateComicsItem(comics: ComicsLocalModel): Int

    @Query("DELETE FROM comics WHERE comicId = :id")
    suspend fun deleteComicsItemById(id: Int): Int

    @Delete
    suspend fun deleteComicsItem(comics: ComicsLocalModel): Int

    @Query("DELETE FROM comics")
    suspend fun clearCachedComicsItems(): Int
}