package com.example.simpletodolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.simpletodolist.entity.TodoItem
import com.example.simpletodolist.repository.TodoItemsRepository

class TodoViewModel(application: Application): AndroidViewModel(application) {

    private var repository = TodoItemsRepository(application)

    fun insert(todoItem: TodoItem) = repository.insert(todoItem)
    fun getAllTodos() = repository.getAllTodos()
    fun deleteTodoItem(todoItem: TodoItem) = repository.deleteTodoItemWithId(todoItem.id)

}