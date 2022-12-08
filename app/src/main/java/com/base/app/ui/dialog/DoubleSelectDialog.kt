package com.base.app.ui.dialog


import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.base.app.R
import com.base.app.utils.DisplayUtils
import kotlinx.android.synthetic.main.dialog_double_select.*


class DoubleSelectDialog(context: Context) : Dialog(context) {
    fun showDialog(title: String, buttonText1: String,buttonText2: String,callback1:Int,callback2:Int, noticeSuccess: IDialogInterface.NoticeSuccess) {
        val layout = View.inflate(context, R.layout.dialog_double_select, null)
        window!!.setBackgroundDrawableResource(R.color.transparent)
        window!!.setGravity(Gravity.BOTTOM)
        /*setCanceledOnTouchOutside(true);
        setCancelable(false);*/
        val width: Int = DisplayUtils.getSystemWidth(context)
        val height: Int = DisplayUtils.getSystemHeight(context)
        layout.minimumWidth = width
        layout.minimumHeight = height
        setContentView(layout)
        tv_title.visibility=if(title=="")View.GONE else View.VISIBLE
        tv_title.text = title
        tv_one.text=buttonText1
        tv_two.text=buttonText2
        text_cancel.setOnClickListener() {
            dismiss()
        }
        tv_one.setOnClickListener() {
            noticeSuccess.dialogSuccess("",callback1)
            dismiss()
        }
        tv_two.setOnClickListener() {
            noticeSuccess.dialogSuccess("",callback2)
            dismiss()
        }
        if (!isShowing) {
            show()
            val attrs = window!!.attributes
            attrs.setTitle("Title");
            attrs.width = WindowManager.LayoutParams.MATCH_PARENT // attrs.width =580;
          //  attrs.height = WindowManager.LayoutParams.MATCH_PARENT // attrs.height = 600;
            window!!.attributes = attrs
        }
    }
}