package com.digital.gnsbook;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import com.httpgnsbook.gnsbook.R;

public class ViewDialog {
    Context activity;
    Dialog dialog;

    public ViewDialog(Context context) {
        this.activity = context;
    }

    public void show() {
        this.dialog = new Dialog(this.activity);
        this.dialog.requestWindowFeature(1);
        this.dialog.setCancelable(false);
        this.dialog.setContentView(R.layout.custom_loading_layout);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialog.show();
    }

    public void dismiss() {
        this.dialog.dismiss();
    }
}
