package com.example.intermediate_submissionfinal_robbyramadhan.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.intermediate_submissionfinal_robbyramadhan.model.StoryModel

@Dao
interface StoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(stories: List<StoryModel>)

    @Query("SELECT * FROM story_items")
    fun getStories(): PagingSource<Int, StoryModel>

    @Query("DELETE FROM story_items")
    suspend fun deleteAllStories()
}