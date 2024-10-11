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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import modelClass.Webtoons
import repository.WebtoonsRepository
import room.MainDatabase
import viewmodel.MainViewModel
import viewmodel.ViewModelFactory

class FavouriteActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var data:ArrayList<Webtoons>
    //lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_favourite)

        val repo= WebtoonsRepository(MainDatabase.getDatabase(applicationContext).webtoonsDao())
        viewModel= ViewModelProvider(this, ViewModelFactory(repo)).get(MainViewModel::class.java)
        data=ArrayList<Webtoons>()
        recyclerView=findViewById(R.id.favrv)
        var mAdapter= MyRVAdapter(this,data)
        viewModel.getWt().observe(this, Observer {
            data.clear()  // Clear data before adding new
            for(i in it.indices){
                if(it[i].favourite==1){  //**
                    data.add(it[i])
                }
            }
            mAdapter.notifyDataSetChanged()  // Only update data after loading
        })

// Set adapter only once after initializing RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter

        //float working
        val floating=findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floating.setOnClickListener {
            val i= Intent(this,MainActivity::class.java)
            startActivity(i)
        }





    }
}