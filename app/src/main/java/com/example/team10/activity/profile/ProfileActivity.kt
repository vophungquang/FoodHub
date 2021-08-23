package com.example.team10.activity.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.team10.EditDialog
import com.example.team10.R
import com.example.team10.User
import com.example.team10.activity.signin.SignInActivity
import com.example.team10.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setupViewModelBinding()

    }

    private fun setupViewModelBinding(){
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.lifecycleOwner = this
        binding.profileViewModel = viewModel

        binding.apply {
            val user = getUserFromBundle()
            user?.let {
                viewModel.setupUserProfile(user.email)

            }
    //set nhan chinh sua Dialog
            tvFullName.setOnClickListener {
                setupAlertDialog("Edit Full Name", "Enter your full name",tvFullName.text.toString(),object:EditDialog.EditDialogCallback {
                    override fun onConfirmClicked(data: String) {
                        viewModel.editFullNameUser(user!!.email,data)
                        showToastMessage(data)
                    }
                })
            }
            tvEmail.setOnClickListener {
                setupAlertDialog("Edit E-mail ", "Enter your e-mail",tvEmail.text.toString(),object :EditDialog.EditDialogCallback {
                    override fun onConfirmClicked(data: String) {
                        viewModel.editEmailUser(user!!.email,data)
                        showToastMessage(data)

                    }
                })
            }
            tvPhoneNumber.setOnClickListener {
                setupAlertDialog("Edit Phone Number ", "Enter your phone number",tvPhoneNumber.text.toString(),object :EditDialog.EditDialogCallback {
                    override fun onConfirmClicked(data: String) {
                        viewModel.editPhoneNumberUser(user!!.email,data)
                        showToastMessage(data)
                    }
                })
            }
        }
    }

    private fun getUserFromBundle() : User? {
        val bundle = intent.extras
        bundle?.let {
           // return bundle.getParcelable(SignInActivity)
        }
        return null
    }


    //tao setupAlert cho Dialog
    private fun setupAlertDialog(title: String, textHint: String,initData:String,editDialogCallback: EditDialog.EditDialogCallback) {
        val dialog = EditDialog(this,title,textHint,initData)
        dialog.setEditDialogCallback(editDialogCallback)
        dialog.show()
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
    }

    private fun showToastMessage(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}