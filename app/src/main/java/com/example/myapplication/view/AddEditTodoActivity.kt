package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcher
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAddEditTodoBinding
import com.example.myapplication.model.TodoDatabase
import com.example.myapplication.model.TodoItem
import com.example.myapplication.model.TodoRepository
import com.example.myapplication.viewModel.TodoViewModel
import com.example.myapplication.viewModel.TodoViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddEditTodoActivity : AppCompatActivity() {

    private var noteId = -1L
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var todoRepository: TodoRepository
    private lateinit var database: TodoDatabase
    private val mbinding: ActivityAddEditTodoBinding by lazy { ActivityAddEditTodoBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mbinding.root)

        database = TodoDatabase.getInstance(this)
        todoRepository = TodoRepository(database.todoDao())
        todoViewModel = ViewModelProvider(this, TodoViewModelFactory(todoRepository)).get(TodoViewModel::class.java)

        val currentDate = getDate()
        mbinding.lastUpdateDate.text = currentDate

        mbinding.backButton.setOnClickListener{
           onBackPressedDispatcher.onBackPressed()
//            Toast.makeText(this, "Button pressed", Toast.LENGTH_SHORT).show()
        }

        val noteType = intent.getStringExtra("noteType")
        if(noteType == "Edit") {
            mbinding.addEditTaskBar.text = "Edit Todo"
            val todoItem = intent.getSerializableExtra("TodoItem") as TodoItem
            mbinding.titleTextView.setText(todoItem.title)
            mbinding.descriptionTextView.setText(todoItem.description)
            noteId = todoItem.id
        }
        mbinding.addEditTodoBtn.setOnClickListener {
            val title = mbinding.titleTextView.text.trim().toString()
            val desc = mbinding.descriptionTextView.text.trim().toString()
            val lastUpdated = mbinding.lastUpdateDate.text.toString()
            if(validateTodo(title)) {
                val todoItem = TodoItem(
                    title = title,
                    description = desc,
                    lastUpdated = lastUpdated,
                    isCompleted = false
                )
                if(noteType == "Edit") {
                    todoItem.id = noteId
                    todoViewModel.update(todoItem)
                }else {
                    todoViewModel.insert(todoItem)
                }
                onBackPressedDispatcher.onBackPressed()
//                finish()
            } else {
                showToast("Title Can't be empty")
            }
        }
    }


    private fun getDate(): String {
        val dateFormat = SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

    private fun validateTodo(title: String): Boolean {
        if(title.isEmpty()) {
            return false
        }
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}