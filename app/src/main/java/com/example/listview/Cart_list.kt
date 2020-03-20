package com.example.listview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Cart_list : Fragment() {

    private var uid : String ? = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart_list, container, false)

        /*--------set uid---------*/
        //get user data
        val user = FirebaseAuth.getInstance().currentUser
        //check login with local or login with facebook
        if(user != null){
            //login with facebook
            uid = user.uid
        }else{
            //login with local
            uid = "s60160335"
        }

        /*--------get data form database realtime---------*/
        val mRootRef = FirebaseDatabase.getInstance().reference
        val mcCartRef = mRootRef.child("cart").child(uid!!)//use uid as folder name in database to path
        mcCartRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val jsonArray : JSONArray? = JSONArray()
                val listView = view.findViewById<ListView>(R.id.cart_list)

                for (ds in dataSnapshot.children) {

                    val jObject = JSONObject()

                    val title = ds.child("title").getValue(String::class.java)!!
                    val description = ds.child("description").getValue(String::class.java)!!
                    val image = ds.child("image").getValue(String::class.java)!!
                    val option = ds.child("option").getValue(String::class.java)!!
                    val dimention = ds.child("dimention").getValue(String::class.java)!!

                    jObject.put("key",ds.key)
                    jObject.put("title",title)
                    jObject.put("description",description)
                    jObject.put("image",image)
                    jObject.put("option",option)
                    jObject.put("dimention",dimention)

                    jsonArray!!.put(jObject)

                }

                val adapter = ListAdapter(activity!!, jsonArray!!)

                listView.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        // Inflate the layout for this fragment
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
