package com.example.tasty.data.network.model

import org.junit.Assert
import org.junit.Test
import com.example.tasty.data.local.model.Recipe as LocalRecipe


class RecipeTest {

    @Test
    fun testToLocal() {
        val networkRecipes = listOf(
            Recipe(
                id = 1,
                name = "Tasty Recipe",
                description = "Delicious meal",
                thumbnailUrl = "https://img.buzzfeed.com/thumbnailer-prod-us-east-1/video-api/assets/249914.jpg",
                videoUrl = "",
                keywords = "",
                timeTier = TimeTier("Under 30 minutes"),
                instructions = listOf(),
                tags = listOf()
            ),
            Recipe(
                id = 2,
                name = "Tasty Recipe",
                description = "Delicious meal",
                thumbnailUrl = "https://img.buzzfeed.com/thumbnailer-prod-us-east-1/video-api/assets/258751.jpg",
                videoUrl = "",
                keywords = "",
                timeTier = TimeTier("Under 30 minutes"),
                instructions = listOf(),
                tags = listOf()
            )
        )

        val localRecipes = listOf(
            LocalRecipe(
                1,
                "Tasty Recipe",
                "Delicious meal",
                "https://img.buzzfeed.com/thumbnailer-prod-us-east-1/video-api/assets/249914.jpg",
                "",
                "",
                "Under 30 minutes",
                listOf()
            ),
            LocalRecipe(
                2,
                "Tasty Recipe",
                "Delicious meal",
                "https://img.buzzfeed.com/thumbnailer-prod-us-east-1/video-api/assets/258751.jpg",
                "",
                "",
                "Under 30 minutes",
                listOf()
            )
        )

        Assert.assertEquals(networkRecipes.first().toLocal(), localRecipes.first())
        Assert.assertEquals(networkRecipes.last().toLocal(), localRecipes.last())
    }

}