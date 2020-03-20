package com.example.listview


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_cart_detail_item.*

/**
 * A simple [Fragment] subclass.
 */
class cart_detail_item: Fragment() {

    private var key : String = ""
    private var head : String = ""
    private var body : String = ""
    private var option : String = ""
    private var dimention : String = ""

    /*----Firebase authentication---*/
    // Firebase Auth Object.
    var user : FirebaseUser? = null

    fun newInstance(key: String, head: String,body: String, option: String , dimention: String): cart_detail_item {

        val fragment = cart_detail_item()
        val bundle = Bundle()
        bundle.putString("key", key)
        bundle.putString("head", head)
        bundle.putString("body", body)
        bundle.putString("option", option)
        bundle.putString("dimention", dimention)
        fragment.setArguments(bundle)

        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            key = bundle.getString("key").toString()
            head = bundle.getString("head").toString()
            body = bundle.getString("body").toString()
            option = bundle.getString("option").toString()
            dimention = bundle.getString("dimention").toString()

        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart_detail_item, container, false)

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
        val mCartRef = mRootRef.child("cart")
        val button_delete : TextView = view.findViewById(R.id.button_delete)
        button_delete.setOnClickListener {

            val builder = AlertDialog.Builder(this.context)
            builder.setMessage("Please confirm to delete")

            builder.setPositiveButton("Yes") { dialog, id ->
                //get user data
                user = FirebaseAuth.getInstance().currentUser
                if(user != null){
                    Log.d("username", user!!.uid)
                    Log.d("key", key)
                    //login with facebook
                    var mUserIdRef = mCartRef.child(user!!.uid).child(key)//go to cart of current user _
                    mUserIdRef.setValue(null)
                    val fm: FragmentManager = activity!!.getSupportFragmentManager()
                    fm.popBackStack("fragment_cart_item_selected", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show()
                }else{
                    //login with local
                    var mUserIdRef = mCartRef.child("s60160335").child(key)//go to cart of current user
                    mUserIdRef.setValue(null)
                    val fm: FragmentManager = activity!!.getSupportFragmentManager()
                    fm.popBackStack("fragment_cart_item_selected", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("No") { dialog, which ->
                //dialog.dismiss();
            }
            builder.show()

        }

        // Inflate the layout for this fragment
        return view
    }



}
