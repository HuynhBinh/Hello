package com.hnb.hello.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

/**
 * Created by USER on 7/28/2015.
 */
// Base activity, other activity will extend from this base activity
// so they can use the support function from this base activity
public class BaseActivity extends Activity
{
    private ProgressDialog progress_dialog;


    // show loading dialog when need
    public void showProgressDialog()
    {
        if (progress_dialog == null)
        {
            progress_dialog = new ProgressDialog(BaseActivity.this);
        }

        if (!progress_dialog.isShowing())
        {
            progress_dialog.setMessage("loading ... ");
            progress_dialog.setCancelable(true);
            progress_dialog.show();
        }
    }


    // hide loading dialog when finish long run task
    public void hideProgressDialog()
    {
        if (progress_dialog != null && progress_dialog.isShowing())
        {
            progress_dialog.dismiss();
        }
    }


    // show message to user
    public void showToast(String message)
    {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_LONG).show();
    }


}
