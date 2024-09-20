package com.example.todo_list

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.todo_list.databinding.FragmentAddTodoBinding
import java.text.SimpleDateFormat

class AddTodoFragment : Fragment() {

    private lateinit var binding: FragmentAddTodoBinding
    private lateinit var showDate: String
    private lateinit var showTime: String

    private lateinit var database: TodoDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTodoBinding.inflate(inflater, container, false)

        database =
            Room.databaseBuilder(requireActivity(), TodoDatabase::class.java, "TODO_Database")
                .allowMainThreadQueries().build()

        binding.apply {

            pickADateBtn.setOnClickListener {

                pickADate()

            }

            pickATimeBtn.setOnClickListener {

                pickATime()

            }

            submitTodo.setOnClickListener {

                if (todoEditText.text.isNotEmpty() && pickADateBtn.text.isNotEmpty() && pickATimeBtn.text.isNotEmpty()) {
                    val todoText = todoEditText.text.toString()
                    val dateText = showDate
                    val timeText = showTime

                    val todo = Todo(todo = todoText, date = dateText, time = timeText)

                    database.getTodoDao().insertTodo(todo)

                    findNavController().navigate(R.id.action_addTodoFragment_to_homeFragment)
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Please, give needed all information first, then click again.",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

        }

        return binding.root
    }

    private fun pickADate() {
        val calendar = Calendar.getInstance()

        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val showDatePicker = DatePickerDialog(
            requireActivity(),
            DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                showDate = "$day/${month + 1}/$year"
                binding.pickADateBtn.text = showDate

            },
            year,
            month,
            day
        )

        showDatePicker.show()

    }

    @SuppressLint("SimpleDateFormat")
    private fun pickATime() {
        val calendar = Calendar.getInstance()

        val minute = calendar.get(Calendar.MINUTE)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        val showTimePicker = TimePickerDialog(
            requireActivity(),
            TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                val formatTime = SimpleDateFormat("hh:mm aa")

                showTime = formatTime.format(calendar.time)

                binding.pickATimeBtn.text = showTime

            },
            hour,
            minute,
            false
        )

        showTimePicker.show()
    }
}
