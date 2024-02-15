package com.example.atm_project

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.atm_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvMyBalance.append(" $${UserSessionData.userBalance}")

        binding.btnBalance.setOnClickListener {
            setUpBtnSeeMyBalance()
        }

        binding.btnWithdrawal.setOnClickListener {
            setUpBtnWithdrawal()
        }

        binding.btnDeposit.setOnClickListener {
            setUpDeposit()
        }
    }

    private fun setUpBtnWithdrawal() {
        binding.etUserInput.requestFocus()
        makeUserInputAndAmountVisible()

        if(binding.tvMyBalance.visibility == View.VISIBLE) {
            binding.tvMyBalance.visibility = View.INVISIBLE
        }

        binding.btnSubmit.setOnClickListener {
            val amount = binding.etUserInput.text.toString()
            val amountAsInt = amount.toIntOrNull() ?: 0

            if(amountAsInt > UserSessionData.userBalance) {
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.alert))
                    //add dollar sign
                    .setMessage(getString(R.string.unsuccessfulWithdrawal) + " $${UserSessionData.userBalance}")
                    .create()
                alertDialog.show()
            } else {
                submitTransaction(amountAsInt, TransactionOptions.WITHDRAWAL)
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun submitTransaction(transactionAmount: Int, transactionOption: TransactionOptions) {
        when(transactionOption) {
            TransactionOptions.DEPOSIT -> {
                UserSessionData.userBalance += transactionAmount
                binding.tvMyBalance.text = getString(R.string.my_balance) + " $${UserSessionData.userBalance}"
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.alert))
                    .setMessage(getString(R.string.successfulDeposit) + "$transactionAmount")
                    .create()
                alertDialog.show()
                makeUserInputAndAmountVisible()
            }
            TransactionOptions.WITHDRAWAL -> {
                UserSessionData.userBalance -= transactionAmount
                binding.tvMyBalance.text = getString(R.string.my_balance) + " $${UserSessionData.userBalance}"
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.alert))
                    .setMessage(getString(R.string.successfulWithdrawal) + "$transactionAmount")
                    .create()
                alertDialog.show()
                makeUserInputAndAmountVisible()
            }
        }
    }

    private fun setUpDeposit() {
        binding.etUserInput.requestFocus()
        makeUserInputAndAmountVisible()

        binding.tvMyBalance.visibility = View.INVISIBLE

        binding.btnSubmit.setOnClickListener {
            val amount = binding.etUserInput.text.toString()
            val amountAsInt = amount.toIntOrNull() ?: 0

            submitTransaction(amountAsInt, TransactionOptions.DEPOSIT)
        }
    }

    private fun makeUserInputAndAmountVisible() {
        binding.tvChooseAmount.visibility = View.VISIBLE
        binding.etUserInput.visibility = View.VISIBLE
        binding.btnSubmit.visibility = View.VISIBLE

        binding.etUserInput.text.clear()
    }

    private fun makeBalanceVisible() {
        binding.tvMyBalance.visibility = View.VISIBLE
    }

    private fun setUpBtnSeeMyBalance() {
        binding.tvChooseAmount.visibility = View.INVISIBLE
        binding.etUserInput.visibility = View.INVISIBLE
        binding.btnSubmit.visibility = View.INVISIBLE

        makeBalanceVisible()
    }


}