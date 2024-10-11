package com.example.webtoonsapp

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.webtoonsapp.databinding.ActivityDetailBinding
import com.example.webtoonsapp.databinding.ActivityMainBinding
import modelClass.Webtoons
import repository.WebtoonsRepository
import room.MainDatabase
import viewmodel.MainViewModel
import viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var data:ArrayList<Webtoons>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding by lazy {
            ActivityDetailBinding.inflate(layoutInflater)
        }
        setContentView(binding.root)

        val repo= WebtoonsRepository(MainDatabase.getDatabase(applicationContext).webtoonsDao())
        viewModel= ViewModelProvider(this, ViewModelFactory(repo)).get(MainViewModel::class.java)
        data=ArrayList<Webtoons>()



        //working of back button
        val bk=findViewById<ImageView>(R.id.backtohome)
        bk.setOnClickListener {
            finish()
        }

        binding.toonHeading.text=intent.getStringExtra("heading")
        binding.toonCreator.text="Creator: "+intent.getStringExtra("creator")
        val ep=intent.getIntExtra("episode",0).toString()
        binding.toonEpisode.text="Episode: $ep"
        binding.toonContent.text=intent.getStringExtra("content")
        val imgUrl=intent.getStringExtra("image")
        //taking id from intent
        val id=intent.getIntExtra("id",0)  //default is 0
        Glide.with(this).load(imgUrl)
            .into(binding.toonImg);

        //working of favourite button and rating bar
        var fav=0 //not at favorite
        var rating=0;
        viewModel.getWtWId(id).observe(this, Observer {
            fav=it.favourite
            rating=it.avg_rating
            // Update the favorite star image based on the value retrieved from the database
            if (fav == 1) {
                binding.toonfavourite.setImageResource(R.drawable.staryellow)
            } else {
                binding.toonfavourite.setImageResource(R.drawable.star)
            }
            binding.toonAvgRate.text="Average Rating: $rating/5"
        })

        //setting dialog for rating bar
        val sdTgl=binding.toonRate
        sdTgl.setOnClickListener {
            val dialog= Dialog(this)
            dialog.setContentView(R.layout.bottomrate)
            dialog.show()
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setGravity(Gravity.BOTTOM)

            val ratingBar = dialog.findViewById<RatingBar>(R.id.myRatingBar)
            val submitButton = dialog.findViewById<Button>(R.id.submitRating)

            submitButton.setOnClickListener {
                val curr_rating = ratingBar.rating
                rating=(curr_rating.toInt()+rating)/2
                viewModel.updateRat(rating,id)
                Toast.makeText(this, "You rated: $curr_rating\nThanks for your feedback!", Toast.LENGTH_SHORT).show()
                dialog.dismiss() // Close the dialog after submission
        }
    }

        binding.toonfavourite.setOnClickListener {
            if(fav==0){
                fav=1
                viewModel.updateFav(fav,id)
                Toast.makeText(this,"Added to favourites",Toast.LENGTH_SHORT).show()
            }else{
                fav=0
                viewModel.updateFav(fav,id)
                Toast.makeText(this,"Removed from favourites",Toast.LENGTH_SHORT).show()
            }
        }

    }
}