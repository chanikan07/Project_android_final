package com.example.listview


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass.
 */
class detail_item: Fragment() {

    private var head : String = ""
    private var body : String = ""
    private var img : String = ""
    private var option : String = ""
    private var dimention : String = ""

    /*----Firebase authentication---*/
    // Firebase Auth Object.
    var user : FirebaseUser? = null

    fun newInstance(head: String,body: String, img : String, option: String , dimention: String): detail_item {

        val fragment = detail_item()
        val bundle = Bundle()
        bundle.putString("head", head)
        bundle.putString("body", body)
        bundle.putString("img", img)
        bundle.putString("option", option)
        bundle.putString("dimention", dimention)
        fragment.setArguments(bundle)

        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            head = bundle.getString("head").toString()
            body = bundle.getString("body").toString()
            img = bundle.getString("img").toString()
            option = bundle.getString("option").toString()
            dimention = bundle.getString("dimention").toString()

        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail_item, container, false)

//        //get element from layout
        val headTxt : TextView = view.findViewById(R.id.tv_name)
        val bodyTxt : TextView = view.findViewById(R.id.tv_description)
        val optTxt : TextView = view.findViewById(R.id.option)
        val dmtTxt : TextView = view.findViewById(R.id.dimention)

        //set value for element
        headTxt.setText(head)
        bodyTxt.setText(body)
        optTxt.setText(option)
        dmtTxt.setText(dimention)


        /*---------------------------------add item to realtime database-------------------------------*/
        //ประกาศตัวแปร DatabaseReference รับค่า Instance และอ้างถึง path ที่เราต้องการใน database
        val mRootRef = FirebaseDatabase.getInstance().getReference()
        //อ้างอิงไปที่ path ที่เราต้องการจะจัดการข้อมูล ตัวอย่างคือ users และ messages
        val mCartRef = mRootRef.child("cart")
        val button_add : TextView = view.findViewById(R.id.button_add)
        button_add.setOnClickListener {
            //setValue() เป็นการ write หรือ update ข้อมูล ไปยัง path ที่เราอ้างถึงได้ เช่น users/<user-id>/<username>

            //get user data
            user = FirebaseAuth.getInstance().currentUser
            if(user != null){
                Log.d("username", user!!.uid)
                //login with facebook
                var mUserIdRef = mCartRef.child(user!!.uid)//go to cart of current user
                var postValue: HashMap<String, Any> = HashMap() // Array for insert to database
                postValue["title"] = head
                postValue["image"] = img
                postValue["description"] = body
                postValue["option"] = option
                postValue["dimention"] = dimention
                mUserIdRef.push().setValue(postValue)
            }else{
                //login with local
                var mUserIdRef = mCartRef.child("s60160335")//go to cart of current user
                var postValue: HashMap<String, Any> = HashMap()
                postValue["title"] = head
                postValue["image"] = img
                postValue["description"] = body
                postValue["option"] = option
                postValue["dimention"] = dimention
                mUserIdRef.push().setValue(postValue)
            }

        }

        // Inflate the layout for this fragment
        return view
    }



}
