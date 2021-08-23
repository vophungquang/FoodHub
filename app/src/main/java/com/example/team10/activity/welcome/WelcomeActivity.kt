package com.example.team10.activity.welcome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.team10.R
import com.example.team10.activity.boarding.BoardingTwoActivity
import com.example.team10.activity.signin.SignInActivity
import com.example.team10.activity.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_onboarding_one.*
import kotlinx.android.synthetic.main.activity_welcome.*


class WelcomeActivity : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_welcome,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_start.setOnClickListener {
                    //findNavController().navigate(R.id.action_boardingOneFragment_to_boardingTwoFragment)
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<SignUpActivity>(R.id.frag)
                        addToBackStack(null)
                    }
        }

        btn_sign_in.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SignInActivity>(R.id.frag)
                addToBackStack(null)
            }
        }
    }
}