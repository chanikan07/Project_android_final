package com.example.listview

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import android.R.attr.key
import android.util.Log


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Menu_shop.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Menu_shop.newInstance] factory method to
 * create an instance of this fragment.
 */
class Menu_shop : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_menu_shop, container, false)
        val table_button : Button = view.findViewById(R.id.Tables_group)
        val chair_button : Button = view.findViewById(R.id.Chairs_group)
        val cart_button : Button = view.findViewById(R.id.Cart_group)

        //show fragment table menu
        table_button.setOnClickListener{

            //replace other fragement in
            val fragment_table_list = Recycler_view().newInstance("table")
            val fm = fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.Layout, fragment_table_list,"fragment_fragment_list_view")
            transaction.addToBackStack("fragment_second")
            transaction.commit()
        }

        chair_button.setOnClickListener{

            //replace other fragement in
            val fragment_table_list = Recycler_view().newInstance("chair")
            val fm = fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.Layout, fragment_table_list,"fragment_fragment_list_view")
            transaction.addToBackStack("fragment_second")
            transaction.commit()
        }

        cart_button.setOnClickListener{

            //replace other fragement in
            val fragment_table_list = Cart_list()
            val fm = fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.Layout, fragment_table_list,"fragment_cart_list")
            transaction.addToBackStack("fragment_second")
            transaction.commit()
        }

        return view
    }

}
