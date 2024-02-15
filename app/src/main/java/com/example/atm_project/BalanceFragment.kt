package com.example.atm_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.atm_project.UserSessionData.userBalance
import com.example.atm_project.databinding.ActivityMainBinding
import com.example.atm_project.databinding.FragmentBalanceBinding


class BalanceFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_balance, container, false)

        val tvUserBalance = view.findViewById<TextView>(R.id.tvUserBalance)

        tvUserBalance.text = userBalance.toString()


        return view
    }
}