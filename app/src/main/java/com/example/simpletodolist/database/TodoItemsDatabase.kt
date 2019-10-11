package com.example.simpletodolist.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.simpletodolist.dataaccess.TodoItemDao
import com.example.simpletodolist.entity.TodoItem

@Database(entities = [TodoItem::class], version = 1)
abstract class TodoItemsDatabase : RoomDatabase() {

    abstract fun todoItemDao(): TodoItemDao

    companion object {
        private var instance: TodoItemsDatabase? = null

        fun getInstance(context: Context): TodoItemsDatabase? {
            if (instance == null) {
                synchronized(TodoItemsDatabase::class) {
                    instance = Room.databaseBuilder(
                        context,
                        TodoItemsDatabase::class.java,
                        "todo_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return instance
        }
    }

}