package com.example.myapplication.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table")
    fun getAllTodo(): LiveData<List<TodoItem>>

    @Query("SELECT * FROM todo_table WHERE id =:id")
    fun getTodoById(id: Int) : TodoItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todoItem: TodoItem)

    @Delete
    fun delete(todoItem: TodoItem)

    @Update
    fun update(todoItem: TodoItem)

}