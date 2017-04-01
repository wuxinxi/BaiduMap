package com.wxx.map.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * 作者：Tangren_ on 2016/12/17 20:55.
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */

public class ImageTextView extends TextView {

    private Drawable[] drawables;

    public ImageTextView(Context context) {
        super(context);
        init();
    }


    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        drawables = getCompoundDrawables();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        if (null != drawables) {
            Drawable drawableLeft = drawables[0];
            Drawable drawableTop = drawables[1];
            Drawable drawableRight = drawables[2];
            //文字宽度
            float textWidth = getPaint().measureText(getText().toString());
            if (null != drawableLeft) {
                setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                float contentWidth = textWidth + getCompoundDrawablePadding() + drawableLeft.getIntrinsicWidth();
                canvas.translate((getWidth() - contentWidth) / 2, 0);
            }
            if (null != drawableRight) {
                setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
                float contentWidth = textWidth + getCompoundDrawablePadding() + drawableRight.getIntrinsicWidth();
                canvas.translate(-(getWidth() - contentWidth) / 2, 0);
            }

            if (null != drawableTop) {
                Rect rect = new Rect();
                getPaint().getTextBounds(getText().toString(), 0, getText().toString().length(), rect);
                float textHeight = rect.height();
                int drawablePadding = getCompoundDrawablePadding();
                int drawableHeight = drawableTop.getIntrinsicHeight();
                float bodyHeight = textHeight + drawablePadding + drawableHeight;
                canvas.translate(0, (getHeight() - bodyHeight) / 2);
            }
        }

        super.onDraw(canvas);
    }
}
