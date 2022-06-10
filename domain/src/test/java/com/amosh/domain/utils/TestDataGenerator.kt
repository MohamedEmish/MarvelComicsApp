package com.amosh.domain.utils

import com.amosh.domain.entity.ComicsEntity

class TestDataGenerator {
    companion object {
        fun generateComicsItems(): List<ComicsEntity> {

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

        fun generateComicsItem(): ComicsEntity {
            return ComicsEntity(
                id = "id_1",
                title = "title",
            )
        }
    }
}