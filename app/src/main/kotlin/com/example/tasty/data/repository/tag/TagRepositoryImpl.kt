package com.example.tasty.data.repository.tag

import androidx.room.withTransaction
import com.example.tasty.data.local.TastyDatabase
import com.example.tasty.data.local.model.Tag
import com.example.tasty.data.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tastyDatabase: TastyDatabase,
    private val networkDataSource: NetworkDataSource
) : TagRepository {
    override suspend fun fetchTags() {
        val (_, remoteTags) = networkDataSource.getTags()
        tastyDatabase.withTransaction {
            val tags = remoteTags.map { it.toLocal() }
            tastyDatabase.tagDao().upsertAll(tags)
        }
    }

    override fun getTagForId(tagId: Int?): Flow<Tag?> =
        tastyDatabase.tagDao().getTagForId(tagId)

    override fun getTagsFlow(): Flow<List<Tag>> = tastyDatabase.tagDao().getTagsFlow()
}