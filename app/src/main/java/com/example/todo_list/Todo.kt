package com.example.todo_list

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    var todo: String,
    var date: String,
    var time: String
)
