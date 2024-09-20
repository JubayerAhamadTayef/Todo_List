package com.example.todo_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.todo_list.databinding.TodoItemBinding

class TodoAdapter: ListAdapter<Todo, TodoViewHolder>(comparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(TodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        getItem(position).let {

            holder.binding.apply{
                todoTitle.text = it.todo
                todoDate.text = it.date
                todoTime.text = it.time
            }

        }
    }

    companion object {
        val comparator = object : DiffUtil.ItemCallback<Todo>(){
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

}

