package com.example.webtoonsapp

import adapters.MyRVAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import androidx.room.util.foreignKeyCheck
import com.example.webtoonsapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelClass.Webtoons
import repository.WebtoonsRepository
import room.MainDatabase
import viewmodel.MainViewModel
import viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var data:ArrayList<Webtoons>
    //lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val repo=WebtoonsRepository(MainDatabase.getDatabase(applicationContext).webtoonsDao())
        viewModel=ViewModelProvider(this,ViewModelFactory(repo)).get(MainViewModel::class.java)
        data=ArrayList<Webtoons>()
        recyclerView=findViewById(R.id.rv)
        var mAdapter=MyRVAdapter(this,data)
        viewModel.getWt().observe(this, Observer {
            data.clear()  // Clear data before adding new
            data.addAll(it)
            Log.d("Vgdata", it.toString())
            mAdapter.notifyDataSetChanged()  // Only update data after loading
        })

// Set adapter only once after initializing RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter

        //float working
        val floating=findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floating.setOnClickListener {
            val i=Intent(this,FavouriteActivity::class.java)
            startActivity(i)
        }





    }
}