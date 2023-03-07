package com.torrex.vcricket.fragmentsUI.adminAccount

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.torrex.vcricket.activities.adminAccount.adminContest.ContestListAdminActivity
import com.torrex.vcricket.databinding.FragmentAdminBinding

class AdminFragment : Fragment() {

    private lateinit var binding:FragmentAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding=FragmentAdminBinding.inflate(inflater,container,false)
        val root:View=binding.root
        binding.llAdminAddContest.setOnClickListener{

            val intent=Intent(requireActivity(), ContestListAdminActivity::class.java)
            startActivity(intent)
        }
        return root

    }

}