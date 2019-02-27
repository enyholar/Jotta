package com.gideondev.jotta.utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by ${ENNY} on 11/24/2017.
 */

public class CircleCardView
    extends RelativeLayout {

    public CircleCardView(Context context) {
        super(context);
    }

    public CircleCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
