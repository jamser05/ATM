package com.example.atm_project

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.atm_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var accountBalance = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvMyBalance.append(" $$accountBalance")

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

            if(amountAsInt > accountBalance) {
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.alert))
                    //add dollar sign
                    .setMessage(getString(R.string.unsuccessfulWithdrawal) + " $$accountBalance")
                    .create()
                alertDialog.show()
            } else {
                submitTransaction(amountAsInt, TransactionOptions.WITHDRAWAL)
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun submitTransaction(amountAsInt: Int, transactionOption: TransactionOptions) {
        when(transactionOption) {
            TransactionOptions.DEPOSIT -> {
                accountBalance += amountAsInt
                binding.tvMyBalance.text = getString(R.string.my_balance) + " $$accountBalance"
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.alert))
                    .setMessage(getString(R.string.successfulDeposit) + "$amountAsInt")
                    .create()
                alertDialog.show()
                makeUserInputAndAmountVisible()
            }
            TransactionOptions.WITHDRAWAL -> {
                accountBalance -= amountAsInt
                binding.tvMyBalance.text = getString(R.string.my_balance) + " $$accountBalance"
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.alert))
                    .setMessage(getString(R.string.successfulWithdrawal) + "$amountAsInt")
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