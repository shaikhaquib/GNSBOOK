package com.digital.gnsbook.Extra;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.httpgnsbook.gnsbook.R;

public class CenteredToolbar extends Toolbar {
    private TextView centeredTitleTextView;

    public CenteredToolbar(Context context) {
        super(context);
    }

    public CenteredToolbar(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CenteredToolbar(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setTitle(@StringRes int i) {
        setTitle(getResources().getString(i));
    }

    public void setTitle(CharSequence charSequence) {
        getCenteredTitleTextView().setText(charSequence);
    }

    public CharSequence getTitle() {
        return getCenteredTitleTextView().getText().toString();
    }

    public void setTypeface(Typeface typeface) {
        getCenteredTitleTextView().setTypeface(typeface);
    }

    private TextView getCenteredTitleTextView() {
        if (this.centeredTitleTextView == null) {
            this.centeredTitleTextView = new TextView(getContext());
            this.centeredTitleTextView.setSingleLine();
            this.centeredTitleTextView.setEllipsize(TruncateAt.END);
            this.centeredTitleTextView.setGravity(17);
          //  this.centeredTitleTextView.setTextAppearance(getContext(), R.style.TextAppearance.AppCompat.Widget.ActionBar.Title);
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.gravity = 17;
            this.centeredTitleTextView.setLayoutParams(layoutParams);
            addView(this.centeredTitleTextView);
        }
        return this.centeredTitleTextView;
    }
}
