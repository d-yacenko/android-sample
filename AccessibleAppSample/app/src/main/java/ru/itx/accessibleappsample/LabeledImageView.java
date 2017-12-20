package ru.itx.accessibleappsample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by d.yacenko on 15.12.17.
 */

// very-very ugly code, but hurry-scurry work on android emulator :)
public class LabeledImageView extends android.support.v7.widget.AppCompatImageView {

    private boolean selected;
    private String text;
    private Paint mTextPaint;
    private int mActiveSelection=0;
    final public static int TEXTSIZE=48;
    private static int SELECTION_COUNT = 2;
    private int w,h;


    public LabeledImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTextPaint = new Paint();
        mTextPaint.setTextSize(TEXTSIZE);
		mTextPaint.setColor(Color.RED);
		setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                 Log.d("LabeledImageView","onClick "+view.getId());
                if((mActiveSelection + 1) % SELECTION_COUNT==0) {showKeyboard();selected=true;}
                else {dismissKeyboard();selected=false;fireMessage();}
                mActiveSelection++;
            }
        });
    }

    public void showKeyboard(){
        requestFocus();
        postDelayed(new Runnable(){
                        @Override public void run(){
                            InputMethodManager keyboard=(InputMethodManager)LabeledImageView.this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            keyboard.showSoftInput(LabeledImageView.this,0);
                        }
                    }
                ,200);
    }
    public void dismissKeyboard(){
        requestFocus();
        postDelayed(new Runnable(){
                        @Override public void run(){
                            InputMethodManager keyboard=(InputMethodManager)LabeledImageView.this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            keyboard.toggleSoftInput(0,0);
                        }
                    }
                ,200);
    }
    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.w=w;
        this.h=h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (text != null) {
            canvas.drawText(text, 0, 64, mTextPaint);
        }
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d("LabeledImageView","onKeyUp "+(char)event.getUnicodeChar());
        return super.onKeyUp(keyCode, event);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(selected) {
            if(keyCode==67){
                if(text.length()>0)text=text.substring(0,text.length()-1);
            }else {
                text += (char) event.getUnicodeChar();
            }

        }
        Log.d("LabeledImageView","onKeyDown "+(char)event.getUnicodeChar());
        invalidate();
        return super.onKeyUp(keyCode, event);
    }

    private void fireMessage(){
        final int eventType;
        if (Build.VERSION.SDK_INT < 16) {
            eventType = AccessibilityEvent.TYPE_VIEW_FOCUSED;
        } else {
            eventType = AccessibilityEventCompat.TYPE_ANNOUNCEMENT;
        }
        AccessibilityManager manager = (AccessibilityManager) getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
        final AccessibilityEvent ev = AccessibilityEvent.obtain(eventType);
        ev.getText().add(text);
        ev.setEnabled(isEnabled());
        ev.setClassName(getClass().getName());
        ev.setPackageName(getContext().getPackageName());
        final AccessibilityRecordCompat record = new AccessibilityRecordCompat(ev);
        record.setSource(this);
        manager.sendAccessibilityEvent(ev);

    }

//    @Override
//    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
//        Log.d("LabeledImageView","requestFocus "+direction);
//        return super.requestFocus(direction, previouslyFocusedRect);
//    }

//    @Override
//    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
//        Log.d("LabeledImageView","onFocusChanged "+direction+" "+gainFocus );
//        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
//    }



    public void setText(String text) {
        this.text = text;
    }
}
