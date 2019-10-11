package com.example.simpletodolist.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
class TodoItem(val title: String, val description: String) {

    @PrimaryKey(autoGenerate = true)
    var id = 0

}