package com.amosh.remote.utils

import com.amosh.remote.model.ComicsListResults


/**
 * Dummy data generator for tests
 */
class TestDataGenerator {

    companion object {
        fun generateComicsItems(): List<ComicsListResults> {

            return listOf(
                generateComicsItem(),
                generateComicsItem()
                    .copy(
                        id = "id_2"
                    ),
                generateComicsItem()
                    .copy(
                        id = "id_3"
                    )
            )
        }

        fun generateComicsItem(): ComicsListResults {
            return ComicsListResults(
                id = "id_1",
                title = "title",
                dates = mutableListOf()
            )
        }
    }

}