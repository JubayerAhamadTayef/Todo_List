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

    private val calendar by lazy { Calendar.getInstance() }
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

    @SuppressLint("DefaultLocale", "SimpleDateFormat")
    private fun pickATime() {

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val showTimePicker = TimePickerDialog(
            requireActivity(),
            TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minutes ->


                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minutes)

                val timeFormatAmPm = SimpleDateFormat("hh:mm aa")
                val timeFormat24Hours = SimpleDateFormat("HH:mm")

                binding.pickATimeBtn.setText(
                    when(timePicker.is24HourView){
                        true -> timeFormat24Hours.format(calendar.time)
                        false -> timeFormatAmPm.format(calendar.time)
                    }
                )
            },
            hour, minute, false
        )

        showTimePicker.show()
    }
}
