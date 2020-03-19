package com.example.listview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray

class MyRecyclerAdapter(fragmentActivity: FragmentActivity, val dataSource: JSONArray) : RecyclerView.Adapter<MyRecyclerAdapter.Holder>() {

    private val thiscontext : Context = fragmentActivity.baseContext
    private val thisActivity = fragmentActivity


    class Holder(view : View) : RecyclerView.ViewHolder(view) {

        private val View = view;

        lateinit var layout : LinearLayout
        lateinit var titleTextView: TextView
        lateinit var detailTextView: TextView
        lateinit var image: ImageView

        fun Holder(){

            layout = View.findViewById<View>(R.id.recycler_layout) as LinearLayout
            titleTextView = View.findViewById<View>(R.id.title) as TextView
            detailTextView = View.findViewById<View>(R.id.description) as TextView
            image = View.findViewById<View>(R.id.img) as ImageView

        }

    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.layout_recycleview, parent, false))
    }

    override fun getItemCount(): Int {
        return dataSource.length()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.Holder()

        holder.titleTextView.setText( dataSource.getJSONObject(position).getString("title").toString() )
        holder.detailTextView.setText( dataSource.getJSONObject(position).getString("description").toString() )

        Glide.with(thiscontext)
            .load(dataSource.getJSONObject(position).getString("image").toString())
            .into(holder.image)

        holder.layout.setOnClickListener{

            Toast.makeText(thiscontext,holder.titleTextView.text.toString(),Toast.LENGTH_SHORT).show()

            var head : String = dataSource.getJSONObject(position).getString("title").toString()
            var body : String = dataSource.getJSONObject(position).getString("description").toString()
            var img : String = dataSource.getJSONObject(position).getString("image").toString()
            var option : String = dataSource.getJSONObject(position).getString("option").toString()
            var dimention : String = dataSource.getJSONObject(position).getString("dimention").toString()

            val fragment_item_selected = item_selected().newInstance(head,body,img,option,dimention)
            val fm = thisActivity.supportFragmentManager
            val transaction : FragmentTransaction =  fm.beginTransaction()
            transaction.replace(R.id.Layout, fragment_item_selected,"fragment_MemberDetail")
            transaction.addToBackStack("fragment_MemberDetail")
            transaction.commit()

        }

    }


}
