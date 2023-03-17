package com.torrex.vcricket.fragmentsUI.myContest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.databinding.MyContestFragmentBinding
import com.torrex.vcricket.firebase.FireBaseContest
import com.torrex.vcricket.firebase.FireBaseJoinedContestDatabase
import com.torrex.vcricket.models.contests.MatchContest
import com.torrex.vcricket.models.contests.MyJoinedContest
import com.torrex.vcricket.sharedpreference.UserSharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class
MyContestFragment : Fragment() {

    private var _binding:MyContestFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var galleryViewModel:MyContestFragmentViewModel
    private var mUserId:String=""
    private var mJoinedContestList=ArrayList<MyJoinedContest>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MyContestFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        galleryViewModel = ViewModelProvider(this,MyContestViewModelFactory(requireActivity()))[MyContestFragmentViewModel::class.java]
        getMyJoinedContestList()

        galleryViewModel.contestList.observe(viewLifecycleOwner, Observer {
            setUpRecycleView(it)
        })
    }

    private fun getMyJoinedContestList() {
        CoroutineScope(Dispatchers.IO).launch{
            mUserId=UserSharedPreference(requireActivity()).getUserSharedPref().id
            FireBaseJoinedContestDatabase().getJoinedContest(this@MyContestFragment,mUserId)
        }
    }

    //Get Contest List Success
    fun getContestListSuccess(joinedContestList:ArrayList<MyJoinedContest>){
        galleryViewModel.contestList.value=joinedContestList
    }

    private fun setUpRecycleView(mJoinedContestList: ArrayList<MyJoinedContest>) {
        if (mJoinedContestList.size>0){
            binding.rvMyContest.visibility=View.VISIBLE
            binding.tvNoContestJoined.visibility=View.GONE
            val contestRecyclerView=binding.rvMyContest
            contestRecyclerView.layoutManager=LinearLayoutManager(requireContext())
            contestRecyclerView.setHasFixedSize(true)

            val adapter=MyContestListAdapter(requireActivity(),mJoinedContestList)
            contestRecyclerView.adapter=adapter

            adapter.setOnClickListener(object:MyContestListAdapter.OnItemClickListener{
                override fun onItemClicked(position: Int, contest: MyJoinedContest) {
                    Log.v("CONTEST_ITEM","Contest at${position} clicked")
                }
            })
        }
        else{
            binding.rvMyContest.visibility=View.GONE
            binding.tvNoContestJoined.visibility=View.VISIBLE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}