package com.example.pomo.activities

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pomo.R
import com.example.pomo.model.PomodoroSession

class PomodoroAdapter(
    private val listener: MainActivity
) : RecyclerView.Adapter<PomodoroAdapter.PomodoroViewHolder>() {
    private var pomodoroList = emptyList<PomodoroSession>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PomodoroViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.pomodoro_item,
            parent, false
        )
        return PomodoroViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PomodoroViewHolder, position: Int) {
        val currentItem = pomodoroList[position]

//        holder.imageView.setImageResource(currentItem.imageResource)
        holder.titleView.text = currentItem.title
        holder.descriptionView.text = currentItem.description
        holder.timeSplit.text = "${currentItem.studyMinutes} / ${currentItem.breakMinutes}"
    }

    override fun getItemCount() = pomodoroList.size

    @SuppressLint("NewApi")
    inner class PomodoroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

//        val imageView: ImageView = itemView.findViewById(R.id.pomo_image_view)
        val titleView: TextView = itemView.findViewById(R.id.pomo_title_view)
        val descriptionView: TextView = itemView.findViewById(R.id.pomo_description_view)
        val timeSplit: TextView = itemView.findViewById(R.id.time_split)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun getData(index: Int): PomodoroSession {
        return this.pomodoroList[index]
    }

    fun setData(pomodoroSessionList: List<PomodoroSession>) {
        this.pomodoroList = pomodoroSessionList
        notifyDataSetChanged()
    }
}