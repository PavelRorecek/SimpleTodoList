package com.example.simpletodolist

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_add_todo.view.*


class AddTodoDialog : DialogFragment() {

    private lateinit var listener: DialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_add_todo, null)

            return AlertDialog
                .Builder(it)
                .setView(view)
                .setTitle("New todo item")
                .setPositiveButton("Create") { _, _ ->
                    listener.onPositiveButtonClick(
                        this,
                        view.text_title.text.toString(),
                        view.text_description.text.toString())
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        when (context) {
            is DialogListener -> listener = context
            else -> throw IllegalStateException("Context does not implement ${DialogListener::class}.")
        }
    }

    internal interface DialogListener {
        fun onPositiveButtonClick(dialog: DialogFragment, title: String, description: String)
    }

}