package adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.webtoonsapp.DetailActivity
import com.example.webtoonsapp.R
import modelClass.Webtoons

class MyRVAdapter(val context:Context,val data: ArrayList<Webtoons>):RecyclerView.Adapter<MyRVAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val tImg=itemView.findViewById<ImageView>(R.id.toonImg)
        val tHead=itemView.findViewById<TextView>(R.id.toolHeading)
        val tConst=itemView.findViewById<ConstraintLayout>(R.id.constraint)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.each_item_in_rv,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currItem=data[position]
        holder.tHead.text=currItem.heading
        Glide.with(context).load(currItem.image)
            .into(holder.tImg);
        holder.tConst.setOnClickListener {
            val intent= Intent(context,DetailActivity::class.java)
            intent.putExtra("heading",currItem.heading)
            intent.putExtra("image",currItem.image)
            intent.putExtra("content",currItem.content)
            intent.putExtra("creator",currItem.creator)
            intent.putExtra("episode",currItem.episode)
            intent.putExtra("id",currItem.id)
            context.startActivity(intent)
        }


    }


}