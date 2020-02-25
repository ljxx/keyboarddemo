package com.ylx.keyboarddemo.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;
import com.ylx.keyboarddemo.R;

import java.util.Iterator;

/**
 * createTime：2019-12-18  17:23
 * author：YLiXiang
 * description：
 */
public class CustomNumKeyboardView extends KeyboardView {
    private Context mContext;
    private Keyboard mKeyboard;
    private boolean mConFirmBtnEnabled = true;

    public CustomNumKeyboardView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        this.mContext = paramContext;
    }

    public CustomNumKeyboardView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        this.mContext = paramContext;
    }

    private void drawIcon(Canvas paramCanvas, Keyboard.Key paramKey) {
        if (paramKey.icon != null) {
            paramKey.icon.setBounds(
                    paramKey.x + (paramKey.width - paramKey.icon.getIntrinsicWidth()) / 2,
                    paramKey.y + (paramKey.height - paramKey.icon.getIntrinsicHeight()) / 2,
                    paramKey.x + (paramKey.width - paramKey.icon.getIntrinsicWidth()) / 2 + paramKey.icon.getIntrinsicWidth(),
                    paramKey.y + (paramKey.height - paramKey.icon.getIntrinsicHeight()) / 2 + paramKey.icon.getIntrinsicHeight());
            paramKey.icon.draw(paramCanvas);
        }
    }

    // 绘制背景
    private void drawKeyBackground(int paramInt, Canvas paramCanvas, Keyboard.Key paramKey) {
        Drawable localDrawable = this.mContext.getResources().getDrawable(paramInt);
        int[] arrayOfInt = paramKey.getCurrentDrawableState();
        if (paramKey.codes[0] != 0) {
            localDrawable.setState(arrayOfInt);
        }

        localDrawable.setBounds(paramKey.x, paramKey.y,
                paramKey.x + paramKey.width,
                paramKey.y + paramKey.height);
        localDrawable.draw(paramCanvas);
    }

    // 绘制文字
    private void drawText(Canvas paramCanvas, Keyboard.Key paramKey, float paramFloat, int paramInt, Typeface paramTypeface) {
        Rect localRect = new Rect();
        Paint localPaint = new Paint();
        localPaint.setTextAlign(Paint.Align.CENTER);
        localPaint.setAntiAlias(true);
        localPaint.setColor(ContextCompat.getColor(getContext(), paramInt));
        if (paramKey.label != null) {
            // 字体大小
            localPaint.setTextSize(paramFloat);
            localPaint.setTypeface(paramTypeface);
            localPaint.getTextBounds(paramKey.label.toString(), 0, paramKey.label.toString().length(), localRect);
            paramFloat = paramKey.x + paramKey.width / 2;
            paramInt = paramKey.y;
            int i = paramKey.height / 2;
            paramCanvas.drawText(paramKey.label.toString(), paramFloat, localRect.height() / 2 + (paramInt + i), localPaint);
        }
    }

    @Override
    public void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        this.mKeyboard = getKeyboard();
        if(this.mKeyboard == null || this.mKeyboard.getKeys() == null) return;
        Iterator localIterator = this.mKeyboard.getKeys().iterator();
        while (localIterator.hasNext()) {
            Keyboard.Key localKey = (Keyboard.Key) localIterator.next();
            if (localKey.codes[0] == -5) { //删除键
                // 绘制删除键
                drawKeyBackground(R.drawable.app_keyboard_btn_bg, paramCanvas, localKey);
                drawIcon(paramCanvas, localKey);
            } else if (localKey.codes[0] == -4) { //确定键
                // 绘制确认键
                if (this.mConFirmBtnEnabled) {
                    drawKeyBackground(R.color.flag_color_EF3A3A, paramCanvas, localKey);
                    drawText(paramCanvas, localKey, getResources().getDimension(R.dimen.qb_px_19), R.color.flag_color_FFFFFF, Typeface.DEFAULT);
                } else {
                    drawKeyBackground(R.color.flag_color_EF3A3A, paramCanvas, localKey);
                    drawText(paramCanvas, localKey, getResources().getDimension(R.dimen.qb_px_19), R.color.flag_color_FFFFFF, Typeface.DEFAULT);
                }
            } else if (localKey.codes[0] == -3) { //关闭软盘
                // 绘制键盘图标
                drawKeyBackground(R.drawable.app_keyboard_btn_bg, paramCanvas, localKey);
                drawIcon(paramCanvas, localKey);
            } else {
                // 绘制其他的数字 这里和 xml是对应的关系
                drawKeyBackground(R.drawable.app_keyboard_btn_bg, paramCanvas, localKey);
                drawText(paramCanvas, localKey, getResources().getDimension(R.dimen.qb_px_29), R.color.flag_color_333333, Typeface.DEFAULT);
            }
        }
    }

    public boolean isConfirmBtnEnabled() {
        return this.mConFirmBtnEnabled;
    }


    public void setConfirmBtnEnabled(boolean paramBoolean) {
        this.mConFirmBtnEnabled = paramBoolean;
        invalidate();
    }
}
