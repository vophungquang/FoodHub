package com.example.team10

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.example.team10.databinding.DialogEditInfoBinding

//tao doi so cho Dialog
class EditDialog(context: Context, private val title: String, private val hint: String, private val initData:String) :
    AlertDialog(context) {
    private lateinit var binding: DialogEditInfoBinding
    private lateinit var editDialogCallback: EditDialogCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_edit_info,
            null,
            false
        )
        setContentView(binding.root)


        binding.apply {
            tvDialogTitle.text = title
            edtBase.setText(initData)
            edtBase.hint = hint
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnConfirm.setOnClickListener {
                editDialogCallback.onConfirmClicked(edtBase.text.toString())
                dismiss()
            }
        }
    }

    fun setEditDialogCallback(editDialogCallback: EditDialogCallback) {
        this.editDialogCallback = editDialogCallback
    }

    interface EditDialogCallback {
        fun onConfirmClicked(data: String)
    }
}