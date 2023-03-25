package com.torrex.vcricket.fragmentsUI.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.torrex.vcricket.R
import com.torrex.vcricket.activities.matches.MatchContestActivity
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.databinding.FragmentHomeBinding
import com.torrex.vcricket.models.currentMatch.CurrentMatchModel
import com.torrex.vcricket.models.currentMatch.Data
import com.torrex.vcricket.modules.BaseFragment
import com.torrex.vcricket.sharedpreference.MatchSharedPreference

class HomeFragment : BaseFragment() {

    private var _binding:FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var currentMatchList:CurrentMatchModel?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val homeViewModel = ViewModelProvider(this,HomeViewModelFactory(requireActivity()))[HomeViewModel::class.java]

        showProgressDialog("Loading")
        homeViewModel.getCurrentMatchListFromAPI()

        homeViewModel.currentMatchModel.observe(viewLifecycleOwner, Observer {
            currentMatchList= it
            setUpRecycleView(currentMatchList!!)
        })
    }

    private fun setUpRecycleView(matchList:CurrentMatchModel){
        if (matchList.data.isNotEmpty()){
            binding.rvHome.visibility=View.VISIBLE
            binding.tvNoMatch.visibility=View.GONE
            val matchHomeRecyclerView= binding.rvHome
            matchHomeRecyclerView.layoutManager=LinearLayoutManager(this.context)
            matchHomeRecyclerView.setHasFixedSize(true)

            val adapter=HomeMatchAdapter(requireContext(),matchList)
            matchHomeRecyclerView.adapter=adapter

            adapter.setOnClickListener(object :HomeMatchAdapter.ItemClickListener{
                override fun onItemClicked(position: Int,currentMatchModel:Data) {
                    MatchSharedPreference(requireActivity()).saveCurrentMatchOffLine(currentMatchModel)
                    val intent=Intent(requireActivity(),MatchContestActivity::class.java)
                    startActivity(intent)
                }
            })
        }

        else{
            binding.rvHome.visibility=View.GONE
            binding.tvNoMatch.visibility=View.VISIBLE
        }

        hideProgressDialog()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        if (currentMatchList!=null){
            setUpRecycleView(currentMatchList!!)
        }
    }
}