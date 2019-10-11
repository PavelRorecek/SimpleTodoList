package com.example.simpletodolist.repository

import android.app.Application
import android.os.AsyncTask
import com.example.simpletodolist.dataaccess.TodoItemDao
import com.example.simpletodolist.database.TodoItemsDatabase
import com.example.simpletodolist.entity.TodoItem

class TodoItemsRepository(application: Application) {

    private lateinit var todoItemDao: TodoItemDao

    init {
        TodoItemsDatabase.getInstance(application)?.let {
            todoItemDao = it.todoItemDao()
        }
    }

    fun insert(todoItem: TodoItem) {
        InsertTodoItemAsync(todoItemDao).execute(todoItem)
    }

    fun getAllTodos() = todoItemDao.getAllNotes()

    fun deleteTodoItemWithId(id: Int) {
        DeleteTodoItemWithIdAsync(todoItemDao).execute(id)!!
    }

    class InsertTodoItemAsync(private val todoItemDao: TodoItemDao) : AsyncTask<TodoItem, Unit, Unit>() {

        override fun doInBackground(vararg params: TodoItem?) {
            todoItemDao.insert(params[0]!!)
        }

    }

    class DeleteTodoItemWithIdAsync(private val todoItemDao: TodoItemDao) : AsyncTask<Int, Unit, Unit>() {

        override fun doInBackground(vararg params: Int?) {
            todoItemDao.deleteTodoItemWithId(params[0]!!)
        }

    }

}