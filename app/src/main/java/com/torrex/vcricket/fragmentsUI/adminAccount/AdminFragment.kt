package com.torrex.vcricket.fragmentsUI.adminAccount

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.get
import com.torrex.vcricket.R
import com.torrex.vcricket.activities.adminAccount.adminContest.ContestListAdminActivity
import com.torrex.vcricket.databinding.FragmentAdminBinding
import com.torrex.vcricket.fragmentsUI.home.HomeFragment

class AdminFragment : Fragment() {

    private lateinit var binding:FragmentAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding=FragmentAdminBinding.inflate(inflater,container,false)
        val root:View=binding.root

        /*Hiding the bottom navigation View
        val bottomNav:View=requireActivity().findViewById(R.id.nav_view_bottom)
        bottomNav.visibility=View.GONE
        */

        binding.llAdminAddContest.setOnClickListener{
            val intent=Intent(requireActivity(), ContestListAdminActivity::class.java)
            startActivity(intent)

        }
        return root
    }




}