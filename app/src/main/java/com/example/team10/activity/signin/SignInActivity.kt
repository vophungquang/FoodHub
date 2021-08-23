package com.example.team10.activity.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.team10.MainActivity
import com.example.team10.R
import com.example.team10.activity.signup.SignUpActivity
import com.example.team10.databinding.ActivitySignInBinding
import com.example.team10.restaurant.RestaurantActivity


class SignInActivity : Fragment() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: SignInViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        setupViewModel(inflater,container)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSignUp.setOnClickListener {
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<SignInActivity>(R.id.frag)
                    addToBackStack(null)
                }
            }
        }
        viewModel.isSignInSucceed.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                showToastMessage("Sign in Successful")
                val bundle = bundleOf("email" to user.email)
                val intent = Intent(activity,
                    RestaurantActivity::class.java)
                startActivity(intent)
            }

        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                showToastMessage(message)
            }
        })
    }

    override fun onStop() {
        super.onStop()
        viewModel.clear()
    }

    private fun setupViewModel(inflater: LayoutInflater,container: ViewGroup?){
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_sign_in, container, false)
        binding.lifecycleOwner = this
        binding.signInViewModel = viewModel
    }
    private fun showToastMessage(value: String) {
        Toast.makeText(activity, value, Toast.LENGTH_SHORT).show()
    }
}