package com.example.myapplication.model

import android.widget.Toast
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todo_table")
data class TodoItem(
    val title: String,
    val description: String,
    val lastUpdated: String,
    var isCompleted: Boolean,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
) : Serializable {
    companion object {

    }

    fun toggleIsComplete() {
        isCompleted = !isCompleted
    }
}
