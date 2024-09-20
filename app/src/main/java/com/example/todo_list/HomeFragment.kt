package com.example.todo_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.todo_list.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: TodoDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        database = Room.databaseBuilder(requireActivity(), TodoDatabase::class.java, "TODO_Database").allowMainThreadQueries().build()

        val todos: List<Todo> = database.getTodoDao().getAllTodo()

        todos.let {
            val adapter = TodoAdapter()
            adapter.submitList(it)

            binding.recyclerView.adapter = adapter
        }

        binding.addBtn.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_addTodoFragment)

        }

        return binding.root
    }
}