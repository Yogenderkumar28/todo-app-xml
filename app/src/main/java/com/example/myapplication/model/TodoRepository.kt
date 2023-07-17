package com.example.myapplication.model

import androidx.lifecycle.LiveData

class TodoRepository(private val todoDao: TodoDao) {

    val allTodos: LiveData<List<TodoItem>> = todoDao.getAllTodo()

    suspend fun delete(todoItem: TodoItem) {
        todoDao.delete(todoItem)
    }

    suspend fun insert(todoItem: TodoItem) {
        todoDao.insert(todoItem)
    }

    suspend fun update(todoItem: TodoItem) {
        todoDao.update(todoItem)
    }
}