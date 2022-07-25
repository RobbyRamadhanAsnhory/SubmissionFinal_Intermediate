package com.example.intermediate_submissionfinal_robbyramadhan

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_items")
data class RemoteItems(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)