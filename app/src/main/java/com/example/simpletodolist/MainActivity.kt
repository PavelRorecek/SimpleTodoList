package com.example.simpletodolist

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodolist.entity.TodoItem
import com.example.simpletodolist.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_todo_item.view.*

class MainActivity : AppCompatActivity(), AddTodoDialog.DialogListener {

    private lateinit var todoViewModel: TodoViewModel

    override fun onPositiveButtonClick(
        dialog: androidx.fragment.app.DialogFragment,
        title: String,
        description: String
    ) {
        todoViewModel.insert(TodoItem(title, description))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel::class.java)
        val todoListAdapter = TodoListAdapter(this, todoViewModel)

        todoViewModel.getAllTodos().observe(this, Observer<List<TodoItem>> {
            todoListAdapter.update(it!!)
        })

        todo_list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        todo_list.adapter = todoListAdapter

        fab.setOnClickListener {
            AddTodoDialog().show(supportFragmentManager, "AddTodoDialog")
        }
    }

    class TodoListAdapter(
        private val context: Context,
        private val todoViewModel: TodoViewModel
    ) : RecyclerView.Adapter<TodoListHolder>() {

        private var data = mutableListOf<TodoItem>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.fragment_todo_item, parent, false)

            return TodoListHolder(view)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(todoListHolder: TodoListHolder, position: Int) {
            todoListHolder.bindView(data[position])
            todoListHolder.itemView.todo_item_card.setOnLongClickListener {
                AlertDialog
                    .Builder(context)
                    .setMessage("Delete Todo item?")
                    .setPositiveButton("Delete") { _, _ ->
                        todoViewModel.deleteTodoItem(data[position])
                    }
                    .create()
                    .show()

                true
            }
        }

        fun update(data: List<TodoItem>) {
            this.data.clear()
            this.data.addAll(data)
            notifyDataSetChanged()
        }

    }

    class TodoListHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title = view.text_todo_name
        private val description = view.text_todo_description
        private val divider = view.todo_item_divider

        fun bindView(todoItem: TodoItem) {
            title.text = todoItem.title

            if (todoItem.description == "") {
                divider.visibility = View.GONE
                description.visibility = View.GONE
            } else {
                divider.visibility = View.VISIBLE
                description.visibility = View.VISIBLE
                description.text = todoItem.description
            }
        }

    }

}
