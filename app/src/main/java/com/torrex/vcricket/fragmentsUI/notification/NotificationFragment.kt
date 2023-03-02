package com.torrex.vcricket.fragmentsUI.notification

import android.app.PendingIntent
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.torrex.vcricket.activities.mainUi.MainActivity
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.databinding.FragmentNotificationBinding
import com.torrex.vcricket.fragmentsUI.home.HomeFragment
import com.torrex.vcricket.modules.BaseFragment

class NotificationFragment : BaseFragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var viewModel: NotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[NotificationViewModel::class.java]

        binding.btnSendNtoifiaction.setOnClickListener {
            val intent=Intent(requireActivity(),MainActivity::class.java)
            val pendingIntent= PendingIntent.getActivity(requireActivity(),GlobalConstant.NOTIFICATION_PENDING_INTENT,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            sendNotification(requireActivity(),"VCricket Notification Send",pendingIntent)
            Log.v("NOTIFICATION_1","Notification Send")
        }
    }

}