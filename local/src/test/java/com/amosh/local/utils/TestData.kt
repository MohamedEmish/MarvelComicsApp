package com.amosh.local.utils

import com.amosh.local.model.ComicsLocalModel

/**
 * Dummy data generator for tests
 */
class TestData {

    companion object {
        fun generateComicItems(): List<ComicsLocalModel> {

            return listOf(
                generateComicItem(),
                generateComicItem().copy(
                    id = 2,
                    comicId = "id_2"
                ), generateComicItem().copy(
                    id = 3,
                    comicId = "id_3"
                ))
        }

        fun generateComicItem(): ComicsLocalModel {
            return ComicsLocalModel(
                id = 1,
                comicId = "id_1",
                title = "title"
            )
        }
    }

}