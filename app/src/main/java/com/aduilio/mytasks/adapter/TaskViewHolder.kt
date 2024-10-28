package com.aduilio.mytasks.adapter

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.aduilio.mytasks.R
import com.aduilio.mytasks.databinding.TaskListItemBinding
import com.aduilio.mytasks.entity.Task
import com.aduilio.mytasks.listener.TaskItemClickListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class TaskViewHolder(
    private val context: Context,
    private val binding: TaskListItemBinding,
    private val listener: TaskItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun setValues(task: Task) {

        var getPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        var userPreference = getPreferences.getBoolean("date_format_month_extended", false)

        binding.tvTitle.text = task.title

        val currentDate = LocalDate.now()

        if (task.completed) {
            binding.tvTitle.setBackgroundResource(R.color.green_700)
        } else {
            if(task.date!! < currentDate){
                binding.tvTitle.setBackgroundResource(R.color.red_500)
            }else if(task.date!! == currentDate){
                binding.tvTitle.setBackgroundResource(R.color.yellow_500)
            }else{
                binding.tvTitle.setBackgroundResource(R.color.blue_700)
            }
        }

        binding.tvDate.text = task.date?.let { date ->
            if(userPreference){
                date.format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy ", Locale("pt", "BR")))
            }else{
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            }
        } ?: run {
            "-"
        }
        binding.tvTime.text = task.time?.let {
            task.time.toString()
        } ?: run {
            "-"
        }

        binding.root.setOnClickListener {
            listener.onClick(task)
        }

        binding.root.setOnCreateContextMenuListener { menu, _, _ ->
            menu.add(ContextCompat.getString(context, R.string.mark_as_completed)).setOnMenuItemClickListener {
                listener.onMarkAsCompleteClick(adapterPosition, task)
                true
            }
            menu.add(ContextCompat.getString(context, R.string.share)).setOnMenuItemClickListener {
                listener.onShareClick(task)
                true
            }
        }
    }
}