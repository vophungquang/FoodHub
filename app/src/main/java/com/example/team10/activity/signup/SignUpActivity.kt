package com.example.team10.activity.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.team10.R
import com.example.team10.activity.signin.SignInActivity
import com.example.team10.databinding.ActivitySignUpBinding

class SignUpActivity : Fragment(){
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel

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
            btnSignIn.setOnClickListener {
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<SignUpActivity>(R.id.frag)
                    addToBackStack(null)
                }
            }
        }

        viewModel.isSignUpSucceed.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    showToastMessage("Sign Up Successful")
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<SignInActivity>(R.id.frag)
                        addToBackStack(null)
                    }
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                showToastMessage(message)
            }
        })
    }

    private fun setupViewModel(inflater: LayoutInflater,container: ViewGroup?){
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.activity_sign_up,container,false)
        binding.lifecycleOwner = this
        binding.signUpViewModel = viewModel
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}