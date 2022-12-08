package com.base.app.base;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.base.app.R;


public class ProgressDialog {

    public static ProgressDialog get(Activity activity) {
        return new ProgressDialog(activity);
    }

    private Dialog mDialog;

    private Activity mActivity;

    public ProgressDialog(Activity activity) {
        this.mActivity = activity;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.progress_hud, null);
        ImageView spinner = (ImageView) view.findViewById(R.id.spinner);
        AnimationDrawable drawable = (AnimationDrawable) spinner.getBackground();
        drawable.start();
        mDialog = new Dialog(mActivity, R.style.ProgressDialog);
        mDialog.setContentView(view);
    }


    /**
     * Must Call {@link #dismiss()}
     */
    public void show() {
        show(true);
    }

    public void show(boolean cancelable) {
        mDialog.setCancelable(cancelable);
        mDialog.show();
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}