package com.example.pomo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pomo.R
import com.example.pomo.model.PomodoroSession
import com.example.pomo.viewmodel.PomodoroSessionViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable
import java.util.*

class MainActivity : AppCompatActivity(), PomodoroAdapter.OnItemClickListener {
    private val adapter = PomodoroAdapter(this)
    private lateinit var mPomodoroSessionViewModel: PomodoroSessionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecyclerView()
    }

    override fun onItemClick(position: Int) {
        val pomo = adapter.getData(position)
        val l1 = PomodoroSession(pomo.id,pomo.title, pomo.description, pomo.studyMinutes, pomo.breakMinutes, pomo.startDateTime, pomo.endDateTime)
        val i =Intent(this, ItemActivity::class.java)
        i.putExtra("POMOITEM", l1 as Serializable )
        startActivity(i)
    }

    fun addItemScreen(view: View) {
        startActivity(Intent(this, CreatePomoItem::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        MenuInflater(this).inflate(R.menu.delete_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
        }
        return super.onOptionsItemSelected(item)
    }
    //SWIPE FUNCTIONALITY
    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            TODO("Not yet implemented")
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var position = viewHolder.adapterPosition
            when(direction){

                ItemTouchHelper.LEFT->{

                    mPomodoroSessionViewModel.deletePomodoroSession(adapter.getData(position))
                    adapter.notifyItemRemoved(position)

                }
            }
        }

    }
    private fun setRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        val itemTouchHelper = ItemTouchHelper(simpleCallback) //swipe function
        itemTouchHelper.attachToRecyclerView(recyclerView) //swipe function
        mPomodoroSessionViewModel = ViewModelProvider(this).get(PomodoroSessionViewModel::class.java)
        mPomodoroSessionViewModel.readAllData.observe(this, Observer { pomodoroSession ->
            adapter.setData(pomodoroSession)
        })
    }
}


