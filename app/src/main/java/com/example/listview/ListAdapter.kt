package com.example.listview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import org.json.JSONArray

class ListAdapter (act : FragmentActivity, val dataSource: JSONArray) : BaseAdapter()  {
    private val thisActivity : FragmentActivity = act
    private val thiscontext: Context = act.baseContext
    private val inflater: LayoutInflater = thiscontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.length()
    }

    override fun getItem(position: Int): Any {
        return dataSource.getJSONObject(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View
        val holder : ViewHolder

        // 1
        if (convertView == null) {

            // 2
            view = inflater.inflate(R.layout.layout_recycleview, parent, false)

            // 3
            holder = ViewHolder()
            holder.titleTextView = view.findViewById(R.id.title) as TextView
            holder.detailTextView = view.findViewById(R.id.description) as TextView
            holder.image = view.findViewById(R.id.img) as ImageView

            // 4
            view.tag = holder
        } else {
            // 5
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // 6
        val titleTextView = holder.titleTextView
        val detailTextView = holder.detailTextView
        val image = holder.image

        titleTextView.setText( dataSource.getJSONObject(position).getString("title").toString() )
        detailTextView.setText( dataSource.getJSONObject(position).getString("description").toString() )
        Glide.with(thiscontext)
            .load(dataSource.getJSONObject(position).getString("image").toString())
            .into(image)

        view.setOnClickListener{

            Toast.makeText(thiscontext,holder.titleTextView.text.toString(),Toast.LENGTH_SHORT).show()

            var key : String = dataSource.getJSONObject(position).getString("key").toString()
            var head : String = dataSource.getJSONObject(position).getString("title").toString()
            var body : String = dataSource.getJSONObject(position).getString("description").toString()
            var img : String = dataSource.getJSONObject(position).getString("image").toString()
            var option : String = dataSource.getJSONObject(position).getString("option").toString()
            var dimention : String = dataSource.getJSONObject(position).getString("dimention").toString()
            val fragment_cart_item_selected = cart_item_selected().newInstance(key, head,body,img,option,dimention)
            val fm = thisActivity.supportFragmentManager
            val transaction : FragmentTransaction =  fm.beginTransaction()
            transaction.replace(R.id.Layout, fragment_cart_item_selected,"fragment_cart_item_selected")
            transaction.addToBackStack("fragment_cart_item_selected")
            transaction.commit()
        }

        return view
    }

    private class ViewHolder {

        lateinit var titleTextView: TextView
        lateinit var detailTextView: TextView
        lateinit var image: ImageView


    }

}