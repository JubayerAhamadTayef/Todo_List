package com.example.todo_list

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Insert
    fun insertTodo(todoData: Todo)

    @Update
    fun updateTodo(todoData: Todo)

    @Delete
    fun deleteTodo(todoData: Todo)

    @Query("Select * from Todo")
    fun getAllTodo(): List<Todo>
}