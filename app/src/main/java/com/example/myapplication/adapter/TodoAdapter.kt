package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.TodoItemViewBinding
import com.example.myapplication.model.TodoItem

class TodoAdapter(val todoListener: TodoListener, val deleteListener: DeleteListener, val onCheckedListener: OnCheckedListener) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private var todoList = emptyList<TodoItem>()
    inner class TodoViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        private val  binding: TodoItemViewBinding by lazy { TodoItemViewBinding.bind(view) }

        fun bind(item: TodoItem) {
            binding.titleTextView.text = item.title
            binding.descriptionTextView.text = item.description
            binding.lastUpdateTextView.text = item.lastUpdated
            binding.root.setOnClickListener{
                todoListener.onTodoClick(item)
            }
            binding.deleteBtn.setOnClickListener {
                deleteListener.onDeleteClick(item)
            }
            binding.todoIsDone.setOnClickListener {
                item.toggleIsComplete()
            }
            binding.todoIsDone.setOnCheckedChangeListener{ butonView, isChecked ->
                onCheckedListener.onTodoDone(todoItem = item, isDone = isChecked)
                if(item.isCompleted == true) {
                    binding.todoIsDone.isChecked = true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item_view, parent, false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = todoList[position]
        holder.bind(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<TodoItem>) {
        this.todoList = items
        notifyDataSetChanged()
    }
}

interface TodoListener {
    fun onTodoClick(todoItem: TodoItem)
}

interface DeleteListener {
    fun onDeleteClick(todoItem: TodoItem)
}

interface OnCheckedListener {
    fun onTodoDone(todoItem: TodoItem, isDone: Boolean)
}