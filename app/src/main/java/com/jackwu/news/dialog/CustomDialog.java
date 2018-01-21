package com.jackwu.news.dialog;


import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.jackwu.news.R;


public class CustomDialog extends Dialog {
    private final TextView textView;
    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.setContentView(R.layout.custom_dialog);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        textView = (TextView) this.findViewById(R.id.custom_dialog_tv_title);
    }

    public void setTitle(CharSequence text) {
        textView.setText(text);
    }
}
