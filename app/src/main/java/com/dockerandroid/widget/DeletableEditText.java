/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;


/**
 * Created by saymagic on 2015/12/20.
 */
public class DeletableEditText extends EditText{
	private Drawable mRightDrawable;
    private boolean isHasFocus;

    private static final int TIP_SHAKE_TIMES = 3;

    public DeletableEditText(Context context) {
        super(context);
        init();
    }
    public DeletableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
 
    public DeletableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
     
    private void init(){
        Drawable [] drawables=this.getCompoundDrawables();
        mRightDrawable=drawables[2];
        this.setOnFocusChangeListener(new FocusChangeListenerImpl());
        this.addTextChangedListener(new TextWatcherImpl());
        setClearDrawableVisible(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_UP:
             
            boolean isClean =(event.getX() > (getWidth() - getTotalPaddingRight()))&&
                             (event.getX() < (getWidth() - getPaddingRight()));
            if (isClean) {
                setText("");
            }
            break;
 
        default:
            break;
        }
        return super.onTouchEvent(event);
    }
     
    private class FocusChangeListenerImpl implements OnFocusChangeListener{
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
             isHasFocus=hasFocus;
             if (isHasFocus) {
                 boolean isVisible=getText().toString().length()>=1;
                 setClearDrawableVisible(isVisible);
            } else {
                 setClearDrawableVisible(false);
            }
        }
         
    }
     
    private class TextWatcherImpl implements TextWatcher{
        @Override
        public void afterTextChanged(Editable s) {
             boolean isVisible=getText().toString().length()>=1;
             setClearDrawableVisible(isVisible);
        }
 
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,int after) {
             
        }
 
        @Override
        public void onTextChanged(CharSequence s, int start, int before,int count) {
             
        }
         
    }   
     
    protected void setClearDrawableVisible(boolean isVisible) {
        Drawable rightDrawable;
        if (isVisible) {
            rightDrawable = mRightDrawable;
        } else {
            rightDrawable = null;
        }
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],
                             rightDrawable,getCompoundDrawables()[3]);
    } 
 
    public void setShakeAnimation() {
        this.startAnimation(shakeAnimation(TIP_SHAKE_TIMES));
        
    }
 
    public Animation shakeAnimation(int CycleTimes) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }
}
