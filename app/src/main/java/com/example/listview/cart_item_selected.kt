package com.example.listview


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.listview.R
import com.facebook.CallbackManager
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass.
 */
class cart_item_selected: Fragment() {

    private var key : String = ""
    private var head : String = ""
    private var body : String = ""
    private var img : String = ""
    private var option : String = ""
    private var dimention : String = ""

    fun newInstance(key : String, head: String,body: String,img: String, option: String, dimention: String): cart_item_selected {

        val fragment = cart_item_selected()
        val bundle = Bundle()
        bundle.putString("key", key)
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
            key = bundle.getString("key").toString()
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
        val view = inflater.inflate(R.layout.fragment_cart_item_selected, container, false)
        //get element image
        val imgVi : ImageView = view.findViewById(R.id.imgV)
        //set image
        Glide.with(activity!!.baseContext)
            .load(img)
            .into(imgVi)


        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*---------------------------------set fragement-------------------------------*/
        //replace other fragement in
        val fragment_cart_detail_item = cart_detail_item().newInstance(key, head, body, option, dimention)
        val fm = childFragmentManager
        val transaction : FragmentTransaction = fm!!.beginTransaction()
        transaction.replace(R.id.detail_item, fragment_cart_detail_item,"fragment_cart_detail_item")
        transaction.addToBackStack("fragment_cart_detail_item")
        transaction.commit()
    }




}
