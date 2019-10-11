package com.example.simpletodolist.dataaccess

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.simpletodolist.entity.TodoItem

@Dao
interface TodoItemDao {

    @Insert
    fun insert(todoItem: TodoItem)

    @Query("SELECT * from todo_table")
    fun getAllNotes(): LiveData<List<TodoItem>>

    @Query("DELETE from todo_table where id == :id")
    fun deleteTodoItemWithId(id: Int)

}
