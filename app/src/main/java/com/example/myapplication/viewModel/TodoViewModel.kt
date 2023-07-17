package com.example.myapplication.viewModel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.TodoItem
import com.example.myapplication.model.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(private val todoRepository: TodoRepository): ViewModel() {

    val allTodos: LiveData<List<TodoItem>> = todoRepository.allTodos

    fun insert(todoItem: TodoItem) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.insert(todoItem)
    }

    fun update(todoItem: TodoItem) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.update(todoItem)
    }

    fun delete(todoItem: TodoItem) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.delete(todoItem)
    }
}