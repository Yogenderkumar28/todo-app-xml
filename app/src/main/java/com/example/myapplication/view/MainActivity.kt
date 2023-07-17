package com.example.myapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.DeleteListener
import com.example.myapplication.adapter.OnCheckedListener
import com.example.myapplication.adapter.TodoAdapter
import com.example.myapplication.adapter.TodoListener
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.TodoItemViewBinding
import com.example.myapplication.model.TodoDatabase
import com.example.myapplication.model.TodoItem
import com.example.myapplication.model.TodoRepository
import com.example.myapplication.viewModel.TodoViewModel
import com.example.myapplication.viewModel.TodoViewModelFactory

class MainActivity : AppCompatActivity(), TodoListener, DeleteListener, OnCheckedListener {

    private lateinit var todoItemBinding: TodoItemViewBinding
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var todoRepository: TodoRepository
    private lateinit var database: TodoDatabase
    private val mbinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        todoItemBinding = TodoItemViewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mbinding.root)
//        todoItemView =
        database = TodoDatabase.getInstance(this)
        todoRepository = TodoRepository(database.todoDao())
        val adapter = TodoAdapter(this, this, this)
        mbinding.todoListRecyclerView.adapter = adapter
        mbinding.todoListRecyclerView.layoutManager = LinearLayoutManager(this)
        todoViewModel = ViewModelProvider(this, TodoViewModelFactory(todoRepository)).get(TodoViewModel::class.java)
        todoViewModel.allTodos.observe(this) { item ->
            item?.let {
                adapter.setItems(it)
            }
        }

        mbinding.addTodo.setOnClickListener{
            var intent = Intent(this, AddEditTodoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onTodoClick(todoItem: TodoItem) {
        val intent = Intent(this, AddEditTodoActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("TodoItem", todoItem)
        startActivity(intent)
        finish()
    }

    override fun onDeleteClick(todoItem: TodoItem) {
        todoViewModel.delete(todoItem)
        showToast("${todoItem.id} Deleted")
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onTodoDone(todoItem: TodoItem, isDone: Boolean) {
        todoViewModel.insert(todoItem.copy(isCompleted = isDone))
    }
}