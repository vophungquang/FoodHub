package com.example.team10.activity.boarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.Navigation.findNavController
import com.example.team10.R
import kotlinx.android.synthetic.main.activity_onboarding_one.*
import kotlinx.android.synthetic.main.activity_onboarding_two.*
import kotlinx.android.synthetic.main.activity_onboarding_two.btn_next


class BoardingTwoActivity: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_onboarding_two, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_next.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<BoardingThreeActivity>(R.id.frag)
                addToBackStack(null)
            }
        }
    }
}

